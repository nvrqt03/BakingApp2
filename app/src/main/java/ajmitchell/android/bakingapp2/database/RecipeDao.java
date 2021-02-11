package ajmitchell.android.bakingapp2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ajmitchell.android.bakingapp2.models.Ingredient;
import ajmitchell.android.bakingapp2.models.Recipe;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipe_table ORDER BY name")
    LiveData<List<Recipe>> getAllRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe... recipe);

    @Query("SELECT id FROM recipe_table WHERE id = :id")
    Recipe getRecipeById(int id);

//    @Insert(onConflict = OnConflictStrategy.REPLACE) // ** GOT ERRORS HERE - SAID INT CANNOT BE CONVERTED TO ELEMENT
//    void insert(int recipeId);

//    @Query("INSERT INTO ingredient_table WITH recipeId")  // ** TRYING TO INSERT THE RECIPEID TO THE TABLE, EXPECTS <SELECTED STATEMENT>
//    void insert (int recipeId);

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    void insertIngredients(Ingredient... ingredients);

    @Query("SELECT * FROM ingredient_table WHERE recipeId = :id")
    List<Ingredient> getIngredients(int id);
}
