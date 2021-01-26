package ajmitchell.android.bakingapp2;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ajmitchell.android.bakingapp2.adapters.RecipeAdapter;
import ajmitchell.android.bakingapp2.database.RecipeRepository;
import ajmitchell.android.bakingapp2.models.Recipe;
import ajmitchell.android.bakingapp2.network.BakingApi;
import ajmitchell.android.bakingapp2.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RecipeFragment extends Fragment implements RecipeAdapter.OnRecipeItemClickListener {

    private List<Recipe> recipeList;
    private RecipeAdapter adapter;
    public RecipeViewModel mViewModel;
    private RecyclerView mRecyclerView;
    RecipeAdapter.OnRecipeItemClickListener mCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mCallback = (RecipeAdapter.OnRecipeItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
            + " must implement OnItemClickListener");
        }
    }

    public RecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mViewModel.getRecipesFromApi();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = rootView.findViewById(R.id.recipe_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new RecipeAdapter(recipeList, getContext(), mCallback);

        mViewModel.getAllRecipes().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null) {
                    adapter.setRecipe(recipes);
                    mRecyclerView.setAdapter(adapter);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onRecipeItemClick(Recipe recipeItem) {
        mCallback.onRecipeItemClick(recipeItem);
    }
}
