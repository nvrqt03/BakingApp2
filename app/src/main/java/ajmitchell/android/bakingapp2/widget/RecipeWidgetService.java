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

//    public static void startActionGetIngredients(Context context, int recipeId) {
//        Intent intent = new Intent(context, RecipeWidgetService.class);
//        intent.setAction(ACTION_GET_INGREDIENTS);
//        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
//        context.startService(intent);
//    }

//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        if (intent != null) {
//            final String action = intent.getAction();
//            if (ACTION_GET_INGREDIENTS.equals(action)) {
//                final int recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, 0); //unsure what to put here
//                handleActionGetIngredients(recipeId);
//            }
//        }
//    }
//
//    private void handleActionGetIngredients(int recipeId) {
//        RecipeRepository repository = new RecipeRepository(getApplication());
//        Recipe recipe = repository.getRecipeById(recipeId);
//        ingredientsList = recipe.getIngredients();
//        startActionGetIngredients(this, recipeId);
//
//    }

