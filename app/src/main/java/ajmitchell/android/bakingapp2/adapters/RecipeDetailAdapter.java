package ajmitchell.android.bakingapp2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ajmitchell.android.bakingapp2.R;
import ajmitchell.android.bakingapp2.StepDetailFragment;
import ajmitchell.android.bakingapp2.models.Step;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.StepViewHolder> {

    private List<Step> recipeSteps;
    Step step;
    private boolean mTwoPane = false;
    private Context mContext;

    public RecipeDetailAdapter(List<Step> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_step_item, parent, false);
        return new RecipeDetailAdapter.StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Step items = holder.step = recipeSteps.get(position);
        holder.stepItem.setText(items.getShortDescription());
        // going to redo this - not best practice, should be in interface
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (mTwoPane) {
                Step recipeStep = holder.step;
                StepDetailFragment fragment = StepDetailFragment.newInstance(recipeStep);
                ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (recipeSteps == null) {
            return 0;
        } else {
            return recipeSteps.size();
        }
    }


    public class StepViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView stepItem;
        Step step;
        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            stepItem = itemView.findViewById(R.id.description);
        }
    }
}
