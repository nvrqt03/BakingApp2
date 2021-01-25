package ajmitchell.android.bakingapp2.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

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
    private Recipe recipe;

    public RecipeRepository(Application application) {
        RecipeRoomDatabase db = RecipeRoomDatabase.getDatabase(application);
        recipeDao = db.recipeDao();
        mAllRecipes = recipeDao.getAllRecipes();
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return mAllRecipes;
    }

    public LiveData<Recipe> getRecipeById(int id) {
        return recipeDao.getRecipeById(id);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Recipe recipe) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            recipeDao.insert(recipe);
        });
    }

    public Recipe getRecipeFromApi() {
        Retrofit retrofit = RetrofitClient.getInstance();
        BakingApi bakingApi = retrofit.create(BakingApi.class);
        Call<Recipe> call = bakingApi.getRecipes();
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                Recipe recipe = response.body();
                insert(recipe);
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {

            }
        });
        return recipe;
    }

}
