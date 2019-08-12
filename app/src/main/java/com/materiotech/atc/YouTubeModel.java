package com.materiotech.atc;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class YouTubeModel implements Serializable {
    @SerializedName("nextPageToken")
     String NextPageToken;

    @SerializedName("pageInfo")
     PageInfo PageInfo;

    @SerializedName("items")
     List<VideoModel> VideoModels;

    class PageInfo {
        @SerializedName("totalResults")
        int TotalResult;
    }

    public int getTotalViedeos() {
        return PageInfo.TotalResult;
    }

    public String getNextPageToken() {
        return NextPageToken;
    }

    public List<VideoModel> getVideoModels() {
        return VideoModels;
    }
}