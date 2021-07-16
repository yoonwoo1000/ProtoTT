package com.example.protott.model;

import java.util.HashMap;
import java.util.Objects;

public class FollowDTO {
    private int followerCount = 0;
    private HashMap<String, Boolean> followers;

    private int followingCount = 0;

    @Override
    public String toString() {
        return "FollowDTO{" +
                "followerCount=" + followerCount +
                ", followers=" + followers +
                ", followingCount=" + followingCount +
                ", followings=" + followings +
                '}';
    }

    private HashMap<String, Boolean> followings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowDTO followDTO = (FollowDTO) o;
        return followerCount == followDTO.followerCount &&
                followingCount == followDTO.followingCount &&
                Objects.equals(followers, followDTO.followers) &&
                Objects.equals(followings, followDTO.followings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerCount, followers, followingCount, followings);
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public HashMap<String, Boolean> getFollowers() {
        return followers;
    }

    public void setFollowers(HashMap<String, Boolean> followers) {
        this.followers = followers;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public HashMap<String, Boolean> getFollowings() {
        return followings;
    }

    public void setFollowings(HashMap<String, Boolean> followings) {
        this.followings = followings;
    }
}
