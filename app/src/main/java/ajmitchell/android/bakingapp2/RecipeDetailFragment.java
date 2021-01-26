package ajmitchell.android.bakingapp2;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ajmitchell.android.bakingapp2.adapters.RecipeDetailAdapter;
import ajmitchell.android.bakingapp2.models.Ingredient;
import ajmitchell.android.bakingapp2.models.Recipe;
import ajmitchell.android.bakingapp2.models.Step;

public class RecipeDetailFragment extends Fragment {
    public Recipe mRecipe;
    private final static String TAG = "RecipeDetailFragment";
    private String recipeName;
    private List<Ingredient> ingredientList;
    public List<Step> steps;
    private RecyclerView stepRecyclerView;

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
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.ingredients);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ingredientList.forEach((item) ->
            {
                textView.append("\u2022 "+ item.getIngredient()+"\n");
                textView.append("\t\t\t Quantity: "+item.getQuantity().toString()+"\n");
                textView.append("\t\t\t Measure: "+item.getMeasure()+"\n\n");
            });
        }

        if (mRecipe != null) {
            ((TextView) rootView.findViewById(R.id.recipe_detail_name)).setText(recipeName);
            ((TextView) rootView.findViewById(R.id.ingredients)).setText(textView.getText());

        } else {
            Log.e(TAG, "onCreateView: mRecipe is null", null);
        }

        stepRecyclerView = rootView.findViewById(R.id.stepRv);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        stepRecyclerView.setLayoutManager(manager);
        RecipeDetailAdapter adapter = new RecipeDetailAdapter(steps);
        stepRecyclerView.setAdapter(adapter);

        return rootView;
    }

}
