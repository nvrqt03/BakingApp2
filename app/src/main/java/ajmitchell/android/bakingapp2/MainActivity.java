package ajmitchell.android.bakingapp2;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.Parcelable;


import java.util.List;

import ajmitchell.android.bakingapp2.database.RecipeRepository;
import ajmitchell.android.bakingapp2.models.Recipe;


public class MainActivity extends AppCompatActivity  { //implements RecipeAdapter.OnRecipeItemClickListener, RecipeDetailAdapter.OnStepClickListener

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

//        if (savedInstanceState == null) {
//            RecipeFragment recipeFragment = new RecipeFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .add(R.id.nav_host_fragment, recipeFragment)
//                    .commit();
//
//        }
    }

//    @Override
//    public void onRecipeItemClick(Recipe recipeItem) {
//        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipeItem);
//        FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction()
//                .replace(R.id.nav_host_fragment, recipeDetailFragment)
//                .addToBackStack("recipe")
//                .commit();
//
//    }
//
//    @Override
//    public void onStepItemClick(Step step, List<Step> steps) {
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) steps);
//
//        StepDetailFragment stepDetailFragment = StepDetailFragment.newInstance(step, (ArrayList<Step>) steps);
//        FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction()
//                .replace(R.id.nav_host_fragment, stepDetailFragment)
//                .addToBackStack("step")
//                .commit();
//    }
}


