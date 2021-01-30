package ajmitchell.android.bakingapp2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ajmitchell.android.bakingapp2.adapters.RecipeAdapter;
import ajmitchell.android.bakingapp2.adapters.RecipeDetailAdapter;
import ajmitchell.android.bakingapp2.database.RecipeRepository;
import ajmitchell.android.bakingapp2.models.Recipe;
import ajmitchell.android.bakingapp2.models.Step;
import ajmitchell.android.bakingapp2.network.BakingApi;
import ajmitchell.android.bakingapp2.network.RetrofitClient;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeItemClickListener, RecipeDetailAdapter.OnStepClickListener {

    public static final String TAG = "MainActivity.class";
    private List<Recipe> recipeList;
    private boolean twoPane;

    public RecipeRepository mRepository;
    private RecipeViewModel mRecipeViewModel;
    private Parcelable mLayoutManagerSavedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            RecipeFragment recipeFragment = new RecipeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout, recipeFragment)
                    .commit();

            if (twoPane) {
                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.frame_layout2, stepDetailFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onRecipeItemClick(Recipe recipeItem) {
        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipeItem);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.frame_layout, recipeDetailFragment)
                .addToBackStack("recipe")
                .commit();

    }

    @Override
    public void onStepItemClick(Step step) {
        StepDetailFragment stepDetailFragment = StepDetailFragment.newInstance(step);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.frame_layout, stepDetailFragment)
                .addToBackStack("step")
                .commit();

        if (twoPane) {
            manager.beginTransaction()
                    .add(R.id.frame_layout2, stepDetailFragment)
                    .addToBackStack("step")
                    .commit();
        }
    }
}


