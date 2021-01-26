package ajmitchell.android.bakingapp2.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import ajmitchell.android.bakingapp2.models.Ingredient;
import ajmitchell.android.bakingapp2.models.Recipe;
import ajmitchell.android.bakingapp2.models.Step;

public class RecipeTypeConverter {
    @TypeConverter
    public static List<Recipe> stringToRecipeList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Recipe>>() {}.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String fromRecipeList(List<Recipe> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<Ingredient> stringToIngredientList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Ingredient>>() {}.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String fromIngredientList(List<Ingredient> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<Step> stringToStepList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Step>>() {}.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String fromStepList(List<Step> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
