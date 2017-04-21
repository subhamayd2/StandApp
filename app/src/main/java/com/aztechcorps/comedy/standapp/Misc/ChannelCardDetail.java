package com.aztechcorps.comedy.standapp.Misc;

public class ChannelCardDetail {
    String mTitle;
    String mCountry;
    String mThumbnail;

    public ChannelCardDetail(String mTitle, String mCountry, String mThumbnail) {
        this.mTitle = mTitle;
        this.mCountry = mCountry;
        this.mThumbnail = mThumbnail;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        mTitle = mTitle;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String mCountry) {
        mCountry = mCountry;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }
}