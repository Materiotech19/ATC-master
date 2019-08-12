package com.materiotech.atc.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.materiotech.atc.PaginationScrollListener;
import com.materiotech.atc.R;
import com.materiotech.atc.VideoRecyclerAdapter;
import com.materiotech.atc.YouTubeModel;
import com.materiotech.atc.api.ApiClient;
import com.materiotech.atc.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YouTubeVideoFragment extends Fragment {
    String TAG = YouTubeVideoFragment.class.getSimpleName();
    public View view;
    String YouTubeAPIChannelId = "UCQ6ByfFiVSGQORtQ1py2wAg";
    String YouTubeAPIKey = "AIzaSyBLejPbqwdVZVMG_apvA4rTi_mLCTCqtyk";
    String YouTubeAPIUrl = "https://www.googleapis.com/youtube/v3/search?" +
            "part=snippet&" +
            "part=statistics&" +
            "channelId=" + YouTubeAPIChannelId + "&" +
            "&maxResults=25&" +
            "order=date&" +
            "key=" + YouTubeAPIKey;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int RECOVERY_REQUEST = 1;
    private int selected = 0;

    TextView txtTotalVideo;
    ProgressBar progressBar;
    RecyclerView mRecyclerView;
    VideoRecyclerAdapter adapter;
    YouTubeModel model;
    YouTubePlayer youTubePlayer;
    YouTubePlayerSupportFragment youtubePlayerFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main, container, false);
        getWidgets();
        return view;
    }

    public void getWidgets() {
        try {
//            context = getActivity();
            mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
            txtTotalVideo = (TextView) view.findViewById(R.id.txt_total_video);

            adapter = new VideoRecyclerAdapter(getActivity());
            youtubePlayerFragment = new YouTubePlayerSupportFragment();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_fragment, youtubePlayerFragment);
            transaction.commit();
//            youtubePlayerFragment = (YouTubePlayerSupportFragment) getActivity().getSupportFragmentManager()
//                    .findFragmentById(R.id.frame_fragment);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
                @Override
                protected void loadMoreItems() {
                    isLoading = true;
                    if (!isLastPage) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadVideos(YouTubeAPIUrl + "&pageToken=" + model.getNextPageToken());
                            }
                        }, 200);
                    }
                }

                @Override
                public boolean isLastPage() {
                    return isLastPage;
                }

                @Override
                public boolean isLoading() {
                    return isLoading;
                }
            });

            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemClicklListener(new VideoRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    selected = position;
                    if (adapter.getItems().size() > 0 && adapter.getItems().get(selected) != null && adapter.getItems().get(selected).getVideoId() != null) {
                        youTubePlayer.cueVideo(adapter.getItems().get(selected).getVideoId());
                        youTubePlayer.play();
                    }
                }
            });

            loadVideos(YouTubeAPIUrl);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    private void loadVideos(String url) {
        ApiInterface apiService = ApiClient.createService(ApiInterface.class);
        if (apiService == null) return;

        showProgressBar();
        Call<YouTubeModel> call = apiService.getVideos(url);
        call.enqueue(new Callback<YouTubeModel>() {
            @Override
            public void onResponse(Call<YouTubeModel> call, Response<YouTubeModel> response) {
                YouTubeModel serverResponse = response.body();
                hideProgressBar();
                loadDataAction(serverResponse);
            }

            @Override
            public void onFailure(Call<YouTubeModel> call, Throwable t) {
                Log.e(TAG, t.toString());
                hideProgressBar();
            }
        });
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void loadDataAction(YouTubeModel item) {
        isLoading = false;

        if (item != null && item.getVideoModels() != null) {
            model = item;
            txtTotalVideo.setText("Total Videos: " + item.getTotalViedeos());

            adapter.addAll(model.getVideoModels());
            adapter.notifyDataSetChanged();

            youtubePlayerFragment.initialize(YouTubeAPIKey, onInitializedListener);

            if (model.getNextPageToken() == null) {
                isLastPage = true;
            }
        }
    }


    public YouTubePlayer.OnInitializedListener onInitializedListener = new YouTubePlayer.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
            if (!b) {
                youTubePlayer = player;
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                if (adapter != null && adapter.getItems().size() > 0) {
                    if (adapter.getItems().get(selected) != null && adapter.getItems().get(selected).getVideoId() != null) {
                        youTubePlayer.cueVideo(adapter.getItems().get(selected).getVideoId());
                        youTubePlayer.loadVideo(adapter.getItems().get(selected).getVideoId());
                        youTubePlayer.play();
                    }
                }

                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {

                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {
                        Log.e(TAG, "Video Started");
                    }

                    @Override
                    public void onVideoEnded() {
                        Log.e(TAG, "Video End");
                        if (selected >= adapter.getItems().size()) {
                            selected = 0;
                        } else {
                            selected++;
                        }

                        youTubePlayer.cueVideo(adapter.getItems().get(selected).getVideoId());
                        youTubePlayer.play();
                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });
            }
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            if (youTubeInitializationResult.isUserRecoverableError()) {
                youTubeInitializationResult.getErrorDialog( getActivity(), RECOVERY_REQUEST).show();
            } else {
                Toast.makeText(getActivity(), "Error Intializing Youtube Player", Toast.LENGTH_LONG).show();
            }
        }
    };
}