package ajmitchell.android.bakingapp2.network;

import java.util.List;

import ajmitchell.android.bakingapp2.models.Ingredient;
import ajmitchell.android.bakingapp2.models.Recipe;
import ajmitchell.android.bakingapp2.models.Step;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingApi {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Ingredient>> getIngredients();

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Step>> getSteps();
}
