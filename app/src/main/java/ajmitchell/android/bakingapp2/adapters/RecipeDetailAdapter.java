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
import ajmitchell.android.bakingapp2.models.Recipe;
import ajmitchell.android.bakingapp2.models.Step;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.StepViewHolder> {

    private List<Step> recipeSteps;
    Step step;
    private boolean mTwoPane = false;
    private Context mContext;
    OnStepClickListener mListener;

    public RecipeDetailAdapter(List<Step> recipeSteps, Context context, OnStepClickListener onStepClickListener) {
        this.recipeSteps = recipeSteps;
        this.mContext = context;
        this.mListener = onStepClickListener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.recipe_step_item, parent, false);
        StepViewHolder holder = new StepViewHolder(itemView, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Step items = holder.step = recipeSteps.get(position);
        holder.stepItem.setText(items.getShortDescription());

    }

    @Override
    public int getItemCount() {
        if (recipeSteps == null) {
            return 0;
        } else {
            return recipeSteps.size();
        }
    }


    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View view;
        TextView stepItem;
        Step step;
        OnStepClickListener listener;

        public StepViewHolder(@NonNull View itemView, OnStepClickListener onStepClickListener) {
            super(itemView);
            view = itemView;
            this.listener = onStepClickListener;
            stepItem = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onStepItemClick(recipeSteps.get(getAdapterPosition()));
        }
    }

    public interface OnStepClickListener {
        void onStepItemClick(Step step);
    }
}
//holder.view.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        //if (mTwoPane) {
//        Step recipeStep = holder.step;
//        StepDetailFragment fragment = StepDetailFragment.newInstance(recipeStep);
//        ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
//        .replace(R.id.frame_layout, fragment)
//        .addToBackStack(null)
//        .commit();