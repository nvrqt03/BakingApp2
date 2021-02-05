package ajmitchell.android.bakingapp2;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ajmitchell.android.bakingapp2.adapters.RecipeDetailAdapter;
import ajmitchell.android.bakingapp2.models.Recipe;
import ajmitchell.android.bakingapp2.models.Step;

public class StepDetailFragment extends Fragment {
    private Step mStep;
    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;
    private Context context;
    private Uri videoUri;
    private Button next;
    private Button previous;
    private int currentStep;
    private TextView shortDescription;
    private TextView longDescription;
    private Application application;
    private RecipeViewModel mViewModel;

    private Recipe recipe;
    private List<Step> videoList;
    private ArrayList<Step> recipeSteps;

    private long playback = 0;
    private Boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;


    public StepDetailFragment() {

    }

    private RecipeDetailAdapter.OnStepClickListener stepClickListener;

    public interface ItemClickListener {
        void onItemClick(List<Step> steps, int index);
    }

    public static StepDetailFragment newInstance(Step step, ArrayList<Step> steps) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("step", step);
        args.putParcelableArrayList("steps", steps);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("step")) {
            mStep = getArguments().getParcelable("step");
            recipeSteps = getArguments().getParcelableArrayList("steps");
            Log.d("steps", "onCreate: " + recipeSteps.toString());

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        playerView = rootView.findViewById(R.id.composerView);
        shortDescription = rootView.findViewById(R.id.step_short_description);
        longDescription = rootView.findViewById(R.id.step_long_description);
        previous = rootView.findViewById(R.id.btn_previous_step);
        next = rootView.findViewById(R.id.btn_next_step);

        if (mStep != null) {
            shortDescription.setText(mStep.getShortDescription());
            longDescription.setText(mStep.getDescription());
            videoUri = Uri.parse(mStep.getVideoURL());
            currentStep = mStep.getId();
        }

        previous = rootView.findViewById(R.id.btn_previous_step);
        next = rootView.findViewById(R.id.btn_next_step);

        // need to get list of videos, and on click go to next video. maybe save the videos from the
        // recipe, and have a position that increments or decrements on click.



        if (mStep != null) {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (currentStep < (recipeSteps.size() - 1)) {
                        if (simpleExoPlayer != null) {
                            simpleExoPlayer.stop();
                        }

                        currentStep++;

                        videoUri = Uri.parse(recipeSteps.get(currentStep).getVideoURL());
                        shortDescription.setText(recipeSteps.get(currentStep).getShortDescription());
                        longDescription.setText(recipeSteps.get(currentStep).getDescription());
                        initializePlayer();
                    } else {
                        Toast.makeText(getActivity(), "End of instructions", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (mStep != null) {
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentStep > 0) {
                        if (simpleExoPlayer != null) {
                            simpleExoPlayer.stop();
                        }
                        currentStep--;
                        videoUri = Uri.parse(recipeSteps.get(currentStep).getVideoURL());
                        shortDescription.setText(recipeSteps.get(currentStep).getShortDescription());
                        longDescription.setText(recipeSteps.get(currentStep).getDescription());
                        initializePlayer();
                    } else {
                        Toast.makeText(getActivity(), "You're already at the beginning", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return rootView;
    }

    private void initializePlayer() {
        if (mStep != null) {
            simpleExoPlayer = new SimpleExoPlayer.Builder(getActivity()).build();
            playerView.setPlayer(simpleExoPlayer);
            MediaItem mediaItem = MediaItem.fromUri(videoUri);
            simpleExoPlayer.setMediaItem(mediaItem);
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            simpleExoPlayer.seekTo(currentWindow, playbackPosition);
            simpleExoPlayer.prepare();
        }
    }

    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
//            playbackPosition = simpleExoPlayer.getCurrentWindowIndex();
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT < 24 || simpleExoPlayer == null)) {
            if (playback != 0 && simpleExoPlayer != null) {
                simpleExoPlayer.seekTo(playback);
            }
            initializePlayer();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            if (simpleExoPlayer != null) {
                simpleExoPlayer.stop();
                playback = simpleExoPlayer.getCurrentPosition();
//            playback = simpleExoPlayer.getCurrentPosition();
                releasePlayer();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
}
