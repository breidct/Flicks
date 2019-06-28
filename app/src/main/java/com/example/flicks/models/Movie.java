package com.example.flicks.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel //annotation indicates class is parcelable
public class Movie {

    //values from API
    String title;
    String overview;
    String posterPath; //only the url
    String backDropPath;
    Double voteAverage;
    Integer id;

    public Movie () {}

    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backDropPath = object.getString("backdrop_path");
        voteAverage = object.getDouble("vote_average");
        id = object.getInt("id");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Integer getId() {
        return id;
    }
}
