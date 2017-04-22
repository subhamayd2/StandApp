package com.aztechcorps.comedy.standapp.Misc;


public class ApiUrl {
    private static String channelUrl = "https://www.googleapis.com/youtube/v3/channels?part=snippet&id=%s&key=" + YoutubeAPI.getAPIkey();
    private static String channelPlaylist = "https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId=%s&key=" + YoutubeAPI.getAPIkey();
    private static String channelVideo = "https://www.googleapis.com/youtube/v3/search?part=snippet,id&order=date&channelId=%s&key=" + YoutubeAPI.getAPIkey();

    public static String getChannelUrl() {
        return channelUrl;
    }

    public static String getChannelVideo() {
        return channelVideo;
    }

    public static String getChannelPlaylist() {
        return channelPlaylist;
    }
}
