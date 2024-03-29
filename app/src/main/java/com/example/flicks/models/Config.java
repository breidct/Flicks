package com.example.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {

    String imageBaseUrl;
    String posterSize;
    //thed backdrop size to use when fetching images
    String backdropSize;

    public Config(JSONObject object) throws JSONException {
        JSONObject images = object.getJSONObject("images");
        //Get the image base url
        imageBaseUrl = images.getString("secure_base_url");
        //get poster size
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        //use the option at index 3
        posterSize = posterSizeOptions.optString(3,"w342");
        //parse the backdrop
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        backdropSize = backdropSizeOptions.optString(1,"w780");
    }

    //helper method to construct URL
    public String getImageUrl(String size, String path){
        return String.format("%s%s%s", imageBaseUrl, size,path); //concatenate all three
    }

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }
}
