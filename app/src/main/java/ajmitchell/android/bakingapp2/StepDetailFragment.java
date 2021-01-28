package ajmitchell.android.bakingapp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Util;

import ajmitchell.android.bakingapp2.models.Step;

public class StepDetailFragment extends Fragment {
    private Step mStep;
    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;
    private Context context;
    private Uri videoUri;
    private Boolean play = false;

    private int current = 0;
    private long playback = 0;


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
        //initializePlayer();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        if (mStep != null) {
            playerView = rootView.findViewById(R.id.composerView);

            ((TextView) rootView.findViewById(R.id.step_short_description)).setText(mStep.getShortDescription());
            ((TextView) rootView.findViewById(R.id.step_long_description)).setText(mStep.getDescription());

            rootView.findViewById(R.id.btn_previous_step);
            rootView.findViewById(R.id.btn_next_step);

            videoUri = Uri.parse(mStep.getVideoURL());
        }

        return rootView;
    }

    private void initializePlayer() {
        simpleExoPlayer = new SimpleExoPlayer.Builder(getActivity()).build();
        playerView.setPlayer(simpleExoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        simpleExoPlayer.setMediaItem(mediaItem);
        simpleExoPlayer.setPlayWhenReady(play);
        simpleExoPlayer.seekTo(current, playback);
        simpleExoPlayer.prepare();
    }

    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            play = simpleExoPlayer.getPlayWhenReady();
            playback = simpleExoPlayer.getCurrentWindowIndex();
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
            if(playback!=0 && simpleExoPlayer !=null){
                simpleExoPlayer.seekTo(playback);}
            initializePlayer();
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            playback = simpleExoPlayer.getCurrentPosition();
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT < 24) {
            initializePlayer();
            if(playback !=0){
                simpleExoPlayer.seekTo(playback);
            }
        }
    }

}
