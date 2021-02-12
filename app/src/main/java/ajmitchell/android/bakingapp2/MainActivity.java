package ajmitchell.android.bakingapp2;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;



public class MainActivity extends AppCompatActivity  { //implements RecipeAdapter.OnRecipeItemClickListener, RecipeDetailAdapter.OnStepClickListener

    public static final String TAG = "MainActivity.class";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}


