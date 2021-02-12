package ajmitchell.android.bakingapp2.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import java.util.List;

import ajmitchell.android.bakingapp2.models.Ingredient;

public class RecipeWidgetService extends RemoteViewsService {

    public List<Ingredient> ingredientsList;
    int recipeId;

    public static final String ACTION_GET_INGREDIENTS = "ajmitchell.android.bakingapp2.widget";
    public static final String EXTRA_RECIPE_ID = "ajmitchell.android.bakingapp2.extra.RECIPE_ID";



    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetDataFactory(this, intent);
    }
}



