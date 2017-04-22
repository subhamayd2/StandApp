package com.aztechcorps.comedy.standapp.Background;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.aztechcorps.comedy.standapp.AllChannelActivity;
import com.aztechcorps.comedy.standapp.Fragment.ChannelFragment;
import com.aztechcorps.comedy.standapp.Misc.ApiUrl;
import com.aztechcorps.comedy.standapp.Misc.YoutubeAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class FetchChannelAsync extends AsyncTask<List<String>, Void, Object> {
    private Activity activity;

    public FetchChannelAsync(AllChannelActivity activity){
        this.activity = activity;
    }

    @Override
    protected Object doInBackground(List<String>... lists) {
        String[] arr = new String[lists[0].size()];
        for(int i = 0; i < lists[0].size(); i++){
            arr[i] = lists[0].get(i);
        }
        String ids = TextUtils.join(",", arr);
        String path = String.format(ApiUrl.getChannelUrl(), ids);

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(false);

            int resCode = conn.getResponseCode();
            if(resCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader( conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                in.close();
                conn.disconnect();
                return sb.toString();
            } else {
                conn.disconnect();
                return new String("false: " + resCode);
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        ((AllChannelActivity)activity).setupAdapter(o);
        Log.d("Channel: ", o.toString());
    }
}
