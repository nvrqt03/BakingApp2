package ajmitchell.android.bakingapp2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ajmitchell.android.bakingapp2.adapters.RecipeAdapter;
import ajmitchell.android.bakingapp2.database.RecipeRepository;
import ajmitchell.android.bakingapp2.models.Recipe;
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

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity.class";
    private List<Recipe> recipeList;

    public RecipeRepository mRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Bundle bundle = new Bundle();
//        bundle.putParcelable("recipes", (Parcelable) mRepository.getRecipeFromApi());

        RecipeFragment recipeFragment = new RecipeFragment();
//        recipeFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_layout, recipeFragment)
                .commit();

    }
}

//    moved to repository
//    public void getRecipes() {
//        Retrofit retrofit = RetrofitClient.getInstance();
//        BakingApi bakingApi = retrofit.create(BakingApi.class);
//        Call<List<Recipe>> call = bakingApi.getRecipes();
//        call.enqueue(new Callback<List<Recipe>>() {
//            @Override
//            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
//                recipeList = response.body();
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("recipe", (ArrayList<? extends Parcelable>) recipeList);
//
//
//                Log.d(TAG, "onResponse: " + recipeList.get(0).getName());
//            }
//
//            @Override
//            public void onFailure(Call<List<Recipe>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }

