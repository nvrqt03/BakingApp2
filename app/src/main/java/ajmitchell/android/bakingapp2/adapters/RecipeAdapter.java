package ajmitchell.android.bakingapp2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ajmitchell.android.bakingapp2.R;
import ajmitchell.android.bakingapp2.RecipeDetailFragment;
import ajmitchell.android.bakingapp2.RecipeFragment;
import ajmitchell.android.bakingapp2.models.Recipe;
import ajmitchell.android.bakingapp2.models.Step;
import ajmitchell.android.bakingapp2.utils.Constants;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList = new ArrayList<>();
    private boolean mTwoPane = false;
    private Context mContext;
    private Recipe items;
    private Step step;
    public OnRecipeItemClickListener mListener;

    public RecipeAdapter(List<Recipe> recipeList, Context context, OnRecipeItemClickListener listener) {
        this.recipeList = recipeList;
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.recipe_item, parent, false);
        RecipeViewHolder holder = new RecipeViewHolder(itemView, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe items = holder.recipe = recipeList.get(position);
        holder.recipeName.setText(items.getName());
        holder.servings.setText(Constants.SERVING + String.valueOf(items.getServings()));
    }

    @Override
    public int getItemCount() {
        if (recipeList == null) {
            return 0;
        }
        return recipeList.size();
    }

    public void setRecipe(List<Recipe> recipes) {
        this.recipeList = recipes;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View view;
        TextView recipeName;
        TextView servings;
        Recipe recipe;
        OnRecipeItemClickListener listener;

        public RecipeViewHolder(@NonNull View itemView, OnRecipeItemClickListener onRecipeItemClickListener) {
            super(itemView);
            view = itemView;
            this.listener = onRecipeItemClickListener;
            recipeName = itemView.findViewById(R.id.recipe_name);
            servings = itemView.findViewById(R.id.servings);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onRecipeItemClick(recipeList.get(getAdapterPosition()));
//            boolean isTablet = mContext.getResources().getBoolean(R.bool.isTablet);
//            if (isTablet) {
//                Recipe currentRecipe = recipeList.get(getAdapterPosition());
//                RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(currentRecipe);
//                ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_recipe_detail_land, fragment)
//                        .addToBackStack(null)
//                        .commit();
//            }
        }
    }

        public interface OnRecipeItemClickListener {
            void onRecipeItemClick(Recipe recipeItem);
        }
    }
//    private void displaySingleLayout(View view) {
//        view.findViewById(R.id.recipe_fragment).setOnClickListener(
//                Navigation.createNavigateOnClickListener(R.id.action_recipeFragment_to_recipeDetailFragment)
//        );

//        view.findViewById(R.id.fragment_recipe_detail).setOnClickListener(
//                Navigation.createNavigateOnClickListener(R.id.action_recipeDetailFragment_to_stepDetailFragment2)
//        );
//    }
// if tablet, should already be in landscape mode... ?  Would there still be a need to navigate to something else since all screens
// would be present?

//    private void displayMasterDetailLayout(View view) {
///NavHostFragment navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.detail_nav_container);
//NavController navController = Navigation.findNavController(view);
//        view.findViewById(R.id.fragment_recipe_detail_land).setOnClickListener(
//                Navigation.createNavigateOnClickListener(R.id.action_recipeDetailFragment_to_stepDetailFragment2)
//        );
//    }

//holder.view.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        if (mTwoPane) {
//        C
//        } else {
////                    Bundle bundle = new Bundle();
////                    bundle.putParcelable("arrayList", recipe);
//
//        Context context = view.getContext();
//        Intent intent = new Intent(context, RecipeDetailActivity.class);
//        intent.putExtra("recipes", holder.recipe);
//        intent.putExtra("steps", holder.steps);
//        context.startActivity(intent);
//        }
//        }
//        });