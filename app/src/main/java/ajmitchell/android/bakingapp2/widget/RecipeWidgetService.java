package ajmitchell.android.bakingapp2.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.Nullable;

import java.util.List;

import ajmitchell.android.bakingapp2.R;
import ajmitchell.android.bakingapp2.database.RecipeRepository;
import ajmitchell.android.bakingapp2.models.Ingredient;
import ajmitchell.android.bakingapp2.models.Recipe;

public class RecipeWidgetService extends IntentService {

    public List<Ingredient> ingredientsList;
    int recipeId;

    public static final String ACTION_GET_INGREDIENTS = "ajmitchell.android.bakingapp2.widget";
    public static final String EXTRA_RECIPE_ID = "ajmitchell.android.bakingapp2.extra.RECIPE_ID";

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }


//    @Override
//    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
//        return new RecipeRemoteViewsFactory(this.getApplicationContext(), intent);
//    }


    public static void startActionGetIngredients(Context context, int recipeId) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_GET_INGREDIENTS);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_INGREDIENTS.equals(action)) {
                final int recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, 0); //unsure what to put here
                handleActionGetIngredients(recipeId);
            }
        }
    }

    private void handleActionGetIngredients(int recipeId) {
        RecipeRepository repository = new RecipeRepository(getApplication());
        Recipe recipe = repository.getRecipeById(recipeId);
        ingredientsList = recipe.getIngredients();
        startActionGetIngredients(this, recipeId);

    }

    class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;
        RecipeWidgetService service;

        public RecipeRemoteViewsFactory(Context applicationContext, Intent intent) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            ingredientsList = service.ingredientsList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
            views.setTextViewText(R.id.widget_item, (CharSequence) ingredientsList.get(i));
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
