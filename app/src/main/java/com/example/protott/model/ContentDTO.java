package com.example.protott.model;

import java.util.Map;
import java.util.Objects;

 public class ContentDTO{

    private String explain;

    private String imageUrl;

    private String uid;



     private String userid;

    private Long timestamp;

    private int favoriteCount;

    private Map favorites;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentDTO that = (ContentDTO) o;
        return favoriteCount == that.favoriteCount &&
                explain.equals(that.explain) &&
                imageUrl.equals(that.imageUrl) &&
                uid.equals(that.uid) &&
                userid.equals(that.userid) &&
                timestamp.equals(that.timestamp) &&
                Objects.equals(favorites, that.favorites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(explain, imageUrl, uid, userid, timestamp, favoriteCount, favorites);
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Map getFavorites() {
        return favorites;
    }

    public void setFavorites(Map favorites) {
        this.favorites = favorites;
    }
}
