package com.example.aliyyyyreza.retrofit.model.rvmodel;

import android.os.Parcel;
import android.os.Parcelable;

public class RvModel implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RvModel> CREATOR = new Parcelable.Creator<RvModel>() {
        @Override
        public RvModel createFromParcel(Parcel in) {
            return new RvModel(in);
        }

        @Override
        public RvModel[] newArray(int size) {
            return new RvModel[size];
        }
    };
    private String title;
    private String imageUrl;
    private String content;

    public RvModel() {
    }

    public RvModel(String title, String imageUrl, String content) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.content = content;
    }

    protected RvModel(Parcel in) {
        title = in.readString();
        imageUrl = in.readString();
        content = in.readString();
    }

    public static Creator<RvModel> getCREATOR() {
        return CREATOR;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(content);
    }
}