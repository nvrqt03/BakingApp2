package ajmitchell.android.bakingapp2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ajmitchell.android.bakingapp2.models.Ingredient;
import ajmitchell.android.bakingapp2.models.Recipe;
import ajmitchell.android.bakingapp2.models.Step;
import ajmitchell.android.bakingapp2.utils.RecipeTypeConverter;

@Database(entities = {Recipe.class, Ingredient.class, Step.class}, version = 1, exportSchema = false)
@TypeConverters(RecipeTypeConverter.class)
public abstract class RecipeRoomDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();

    private static volatile RecipeRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RecipeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecipeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeRoomDatabase.class,
                            "recipe_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
