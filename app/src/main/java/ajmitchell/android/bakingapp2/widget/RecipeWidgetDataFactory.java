package ajmitchell.android.bakingapp2.widget;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import ajmitchell.android.bakingapp2.R;
import ajmitchell.android.bakingapp2.database.RecipeRepository;
import ajmitchell.android.bakingapp2.models.Ingredient;
import ajmitchell.android.bakingapp2.models.Recipe;

public class RecipeWidgetDataFactory implements RemoteViewsService.RemoteViewsFactory {

    List<Ingredient> collection = new ArrayList<>();
    Context context;
    Application application;
    Intent intent;

    int recipeId = 0;

    private void initData() {
        collection.clear();
        //String PACKAGE_NAME = context.getPackageName();

        SharedPreferences sharedPreferences = context.getSharedPreferences("com.ajmitchell.bakingapp2", Context.MODE_PRIVATE);
        recipeId = sharedPreferences.getInt("recipeId", 0);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_items);

        RecipeRepository recipeRepository = new RecipeRepository(context);

        List<Ingredient> ingredientList = recipeRepository.getIngredientsById(recipeId);
        if (ingredientList != null) {
            collection = ingredientList;
        }


//        for (int i = 0; i <= collection.size(); i++) {
//            collection.add(collection.get(i));
//        }
    }

    public RecipeWidgetDataFactory(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return collection.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                R.layout.recipe_item); //simple_list_item_1
        remoteView.setTextViewText(R.id.widget_list_items, (CharSequence) collection.get(i).getIngredient()); //android.R.id.text1,

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

