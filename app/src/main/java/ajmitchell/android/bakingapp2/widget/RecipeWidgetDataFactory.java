package ajmitchell.android.bakingapp2.widget;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import ajmitchell.android.bakingapp2.database.RecipeRepository;
import ajmitchell.android.bakingapp2.models.Ingredient;
import ajmitchell.android.bakingapp2.models.Recipe;

public class RecipeWidgetDataFactory implements RemoteViewsService.RemoteViewsFactory {

    List<Ingredient> collection = new ArrayList<>();
    Context context;
    Application application;
    Intent intent;
    public static String PACKAGE_NAME;


    private void initData() {
        int recipeId = 0;
        PACKAGE_NAME = context.getPackageName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
        recipeId = sharedPreferences.getInt("recipeId", 0);

        RecipeRepository recipeRepository = new RecipeRepository(application);
        Recipe recipe = recipeRepository.getRecipeById(recipeId);
        collection = recipe.getIngredients();
        collection.clear();
        for (int i = 0; i <= collection.size(); i++) {
            collection.add(collection.get(i));
        }
    }

    public RecipeWidgetDataFactory(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {
        initData();
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
                android.R.layout.simple_list_item_1);
        remoteView.setTextViewText(android.R.id.text1, (CharSequence) collection.get(i));
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
        return false;
    }
}
