package ajmitchell.android.bakingapp2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ajmitchell.android.bakingapp2.database.RecipeRepository;
import ajmitchell.android.bakingapp2.models.Recipe;

public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepository mRepository;
    private final LiveData<List<Recipe>> mAllRecipes;


    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RecipeRepository(application);
        mAllRecipes = mRepository.getAllRecipes();

    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return mAllRecipes;
    }

    public void getRecipesFromApi() {
        mRepository.getRecipeFromApi();
    }

    public Recipe getRecipeById(int id) {
        return mRepository.getRecipeById(id);
    }



}
