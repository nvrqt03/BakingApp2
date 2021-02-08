package ajmitchell.android.bakingapp2.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import ajmitchell.android.bakingapp2.models.Recipe;
import ajmitchell.android.bakingapp2.network.BakingApi;
import ajmitchell.android.bakingapp2.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipeRepository {
    private RecipeDao recipeDao;
    private LiveData<List<Recipe>> mAllRecipes;
    private List<Recipe> mRecipe;

    public RecipeRepository(Context context) {
        RecipeRoomDatabase db = RecipeRoomDatabase.getDatabase(context);
        recipeDao = db.recipeDao();
        mAllRecipes = recipeDao.getAllRecipes();
        mRecipe = new ArrayList<>();
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return mAllRecipes;
    }

    public Recipe getRecipeById(int id) {
        return recipeDao.getRecipeById(id);
    }


    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Recipe recipe) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            recipeDao.insert(recipe);
        });
    }

    public void getRecipeFromApi() {
        Retrofit retrofit = RetrofitClient.getInstance();
        BakingApi bakingApi = retrofit.create(BakingApi.class);

        Call<List<Recipe>> call = bakingApi.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                //now set up for loop and loop through each item, inserting each into the database.
                // then initiate this from the fragment, not main.
                mRecipe = response.body();
                for (int i = 0; i < mRecipe.size(); i++) {
                    Recipe recipe = mRecipe.get(i);
                    insert(recipe);
                }
                Log.d("Baking app", "onResponse: " + mRecipe.get(2).getName());
                Log.d("Baking App", "onResponse: " + mRecipe.get(2).getIngredients().toString());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
