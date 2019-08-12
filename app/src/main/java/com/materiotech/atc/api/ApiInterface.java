package com.materiotech.atc.api;

import com.materiotech.atc.YouTubeModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {
    @GET
    Call<YouTubeModel> getVideos(@Url String url);
}