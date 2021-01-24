package ajmitchell.android.bakingapp2.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ajmitchell.android.bakingapp2.models.Recipe;

public class RecipeRepository {
    private RecipeDao recipeDao;
    private LiveData<List<Recipe>> mAllRecipes;

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
}
