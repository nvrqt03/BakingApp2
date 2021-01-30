package ajmitchell.android.bakingapp2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import ajmitchell.android.bakingapp2.database.RecipeRepository;
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
    private Recipe recipes;
    private int currentId;
    private  TextView shortDescription;
    private TextView longDescription;

    RecipeRepository mRepository;

    private Boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private int arrayPosition;


    public StepDetailFragment() {

    }

    public static StepDetailFragment newInstance(Step step) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("step", step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("step")) {
            mStep = getArguments().getParcelable("step");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        if (mStep != null) {
            playerView = rootView.findViewById(R.id.composerView);
            shortDescription = rootView.findViewById(R.id.step_short_description);
            shortDescription.setText(mStep.getShortDescription());
            longDescription = rootView.findViewById(R.id.step_long_description);
            longDescription.setText(mStep.getDescription());
            //((TextView) rootView.findViewById(R.id.step_short_description)).setText(mStep.getShortDescription());
            //((TextView) rootView.findViewById(R.id.step_long_description)).setText(mStep.getDescription());

            previous = rootView.findViewById(R.id.btn_previous_step);
            next = rootView.findViewById(R.id.btn_next_step);

            int id = mStep.getId();

            videoUri = Uri.parse(mStep.getVideoURL());
            //Recipe videoList = mRepository.getRecipeById(id);
            // need to get list of videos, and on click go to next video. maybe save the videos from the
            // recipe, and have a position that increments or decrements on click.


            // Also - the codelab may actually be helpful

        }
        if (mStep != null) {
//            currentId = mStep.getId();
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (simpleExoPlayer != null) {
                        simpleExoPlayer.stop();
                    }
//                    currentId++;
//                    if (currentId == mStep.getId()) {
//                        shortDescription.setText(mStep.getShortDescription());
//                        longDescription.setText(mStep.getDescription());
//                        initializePlayer();
//                    }
                }
            });
        }

        return rootView;
    }

    private void initializePlayer() {
        simpleExoPlayer = new SimpleExoPlayer.Builder(getActivity()).build();
        playerView.setPlayer(simpleExoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        simpleExoPlayer.setMediaItem(mediaItem);
        simpleExoPlayer.setPlayWhenReady(playWhenReady);
        simpleExoPlayer.seekTo(currentWindow, playbackPosition);
        simpleExoPlayer.prepare();
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
//            if(playback!=0 && simpleExoPlayer !=null){
//                simpleExoPlayer.seekTo(playback);}
            initializePlayer();
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
//        if (simpleExoPlayer != null) {
//            simpleExoPlayer.stop();
//            playback = simpleExoPlayer.getCurrentPosition();
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT < 24) {
//            initializePlayer();
//            if(playback !=0){
//                simpleExoPlayer.seekTo(playback);
            releasePlayer();
            }
        }

}
