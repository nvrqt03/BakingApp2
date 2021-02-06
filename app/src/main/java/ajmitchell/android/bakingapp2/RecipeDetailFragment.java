package ajmitchell.android.bakingapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ajmitchell.android.bakingapp2.adapters.RecipeAdapter;
import ajmitchell.android.bakingapp2.adapters.RecipeDetailAdapter;
import ajmitchell.android.bakingapp2.models.Ingredient;
import ajmitchell.android.bakingapp2.models.Recipe;
import ajmitchell.android.bakingapp2.models.Step;

public class RecipeDetailFragment extends Fragment implements RecipeDetailAdapter.OnStepClickListener {
    public Recipe mRecipe;
    private final static String TAG = "RecipeDetailFragment";
    private String recipeName;
    private List<Ingredient> ingredientList;
    public List<Step> steps;
    private RecyclerView stepRecyclerView;
    boolean isTablet;
    NavController navController;
    public SharedPreferences preferences;

    public RecipeDetailFragment() {

    }

//    public static RecipeDetailFragment newInstance(Recipe selectedRecipe) {
//        RecipeDetailFragment fragment = new RecipeDetailFragment();
//        Bundle args = new Bundle();
//        args.putParcelable("recipe_details", selectedRecipe);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        if (getArguments() != null && getArguments().containsKey("recipe_details")) {
            mRecipe = getArguments().getParcelable("recipe_details");
            recipeName = mRecipe.getName();
            ingredientList = mRecipe.getIngredients();
            steps = mRecipe.getSteps();

            editor.putInt("recipeId", mRecipe.getId());
            editor.apply();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isTablet = getResources().getBoolean(R.bool.isTablet);
        View rootView;
        if (isTablet) {
            rootView = inflater.inflate(R.layout.fragment_recipe_detail_land, container, false);
        } else {
            rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        }

        TextView textView = rootView.findViewById(R.id.ingredients);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ingredientList.forEach((item) ->
            {
                textView.append("\u2022 " + item.getIngredient() + "\n");
                textView.append("\t\t\t Quantity: " + item.getQuantity().toString() + "\n");
                textView.append("\t\t\t Measure: " + item.getMeasure() + "\n\n");
            });


            if (mRecipe != null) {
                ((TextView) rootView.findViewById(R.id.recipe_detail_name)).setText(recipeName);
                ((TextView) rootView.findViewById(R.id.ingredients)).setText(textView.getText());

            } else {
                Log.e(TAG, "onCreateView: mRecipe is null", null);
            }

            stepRecyclerView = rootView.findViewById(R.id.stepRv);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            stepRecyclerView.setLayoutManager(manager);
            RecipeDetailAdapter adapter = new RecipeDetailAdapter(steps, getContext(), this);
            stepRecyclerView.setAdapter(adapter);
        }

        return rootView;
    }

    @Override
    public void onStepItemClick(Step step, List<Step> steps) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) steps);
        bundle.putParcelable("step", step);

        if (isTablet) {
            NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.detail_nav_container);
            navController = navHostFragment.getNavController();
            navController.navigate(R.id.stepDetailFragment, bundle);

        } else {
            NavHostFragment navHostFragment =
                    (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.stepDetailFragment2, bundle);
        }
    }
}
//    private void displaySingleLayout(View view) {
//        view.findViewById(R.id.fragment_recipe_detail).setOnClickListener(
//                Navigation.createNavigateOnClickListener(R.id.action_recipeDetailFragment_to_stepDetailFragment2)
//        );
//    }
    // if tablet, should already be in landscape mode... ?  Would there still be a need to navigate to something else since all screens
    // would be present?

//    private void displayMasterDetailLayout(View view) {
//        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.detail_nav_container);
//        NavController navController = navHostFragment.getNavController();

//        view.findViewById(R.id.fragment_recipe_detail).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                navController.navigate(R.id.fragment_recipe_detail);
//            }
//        });

//        view.findViewById(R.id.stepRv).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                navController.navigate(R.id.stepDetailFragment);
//            }
//        });




