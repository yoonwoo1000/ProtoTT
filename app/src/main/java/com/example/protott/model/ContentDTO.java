package com.example.protott.model;

import java.util.Map;
import java.util.Objects;

public class ContentDTO {

    private String explain;

    private String imageUrl;

    private String uid;

    private String userid;

    private Long timestamp;

    private String latitude;

    private String longitude;

    private String takenDate;

    @Override
    public String toString() {
        return "ContentDTO{" +
                "explain='" + explain + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", uid='" + uid + '\'' +
                ", userid='" + userid + '\'' +
                ", timestamp=" + timestamp +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", takenDate='" + takenDate + '\'' +
                ", favoriteCount=" + favoriteCount +
                ", favorites=" + favorites +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentDTO that = (ContentDTO) o;
        return favoriteCount == that.favoriteCount &&
                Objects.equals(explain, that.explain) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(uid, that.uid) &&
                Objects.equals(userid, that.userid) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(takenDate, that.takenDate) &&
                Objects.equals(favorites, that.favorites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(explain, imageUrl, uid, userid, timestamp, latitude, longitude, takenDate, favoriteCount, favorites);
    }

    public String getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(String takenDate) {
        this.takenDate = takenDate;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    private int favoriteCount;

    private Map favorites;


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
