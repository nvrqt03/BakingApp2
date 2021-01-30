package ajmitchell.android.bakingapp2.widget;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class RecipeWidgetService extends IntentService {

    public static final String UPDATE_INGREDIENTS = "update ingredients";


    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
