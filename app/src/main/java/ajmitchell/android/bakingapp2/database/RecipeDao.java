package ajmitchell.android.bakingapp2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ajmitchell.android.bakingapp2.models.Recipe;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipe_table ORDER BY name")
    LiveData<List<Recipe>> getAllRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe... recipe);

    @Query("SELECT id FROM recipe_table WHERE id = :id")
    LiveData<Recipe> getRecipeById(int id);
}
