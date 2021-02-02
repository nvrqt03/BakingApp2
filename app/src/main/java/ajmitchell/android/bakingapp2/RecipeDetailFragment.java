package ajmitchell.android.bakingapp2;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
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
    private RecipeDetailAdapter.OnStepClickListener mCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mCallback = (RecipeDetailAdapter.OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnItemClickListener");
        }
    }

    public RecipeDetailFragment() {

    }

    public static RecipeDetailFragment newInstance(Recipe selectedRecipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("recipe_details", selectedRecipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey("recipe_details")) {
            mRecipe = getArguments().getParcelable("recipe_details");
            recipeName = mRecipe.getName();
            ingredientList = mRecipe.getIngredients();
            steps = mRecipe.getSteps();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        boolean isTablet = getContext().getResources().getBoolean(R.bool.isTablet);
        View rootView;
        if (isTablet) {
            rootView = inflater.inflate(R.layout.fragment_recipe_detail_land, container, false);
            displayMasterDetailLayout(rootView);
        } else {
            rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
            displaySingleLayout(rootView);
        }

        TextView textView = (TextView) rootView.findViewById(R.id.ingredients);

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
            RecipeDetailAdapter adapter = new RecipeDetailAdapter(steps, getContext(), mCallback);
            stepRecyclerView.setAdapter(adapter);
        }

        return rootView;
    }

    @Override
    public void onStepItemClick(Step step, List<Step> steps) {
        mCallback.onStepItemClick(step, steps);
    }

    private void displaySingleLayout(View view) {
        view.findViewById(R.id.recipe_list).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_recipeFragment_to_recipeDetailFragment)
        );
        view.findViewById(R.id.stepRv).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_recipeDetailFragment_to_stepDetailFragment2)
        );
    }

    private void displayMasterDetailLayout(View view) {
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.detail_nav_container);
        NavController navController = navHostFragment.getNavController();

        view.findViewById(R.id.recipe_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.fragment_recipe_detail);
            }
        });

        view.findViewById(R.id.stepRv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.stepDetailFragment);
            }
        });

    }
}

