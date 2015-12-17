package com.camera.simplewebcam;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by chase on 3/23/15.
 *
 */
public class Utils {
    private final static String TAG = Utils.class.getSimpleName();
    public final static String METEOR_URL = "http://gps.chaselambda.com"; // IP address of DO server

    public static class PostReq extends AsyncTask<String, Void, Boolean> {
        private Callback callback;

        public PostReq(Callback callback) {
            super();
            this.callback = callback;
        }

        public interface Callback {
            void onComplete(Boolean myData);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            HttpClient httpClient = new DefaultHttpClient();
            try {
                Log.d(TAG, "SENDING request " + urls[0]);
                HttpPost request = new HttpPost(urls[0]);
                httpClient.execute(request);
                return true;
            }catch (Exception ex) {
                Log.e(TAG, "FAILED request");
            } finally {
                httpClient.getConnectionManager().shutdown();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (callback != null) {
                callback.onComplete(result);
            }
        }
    }

    public static void postReq(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost request = new HttpPost(url);
            httpClient.execute(request);
        }catch (Exception ex) {
            Log.e(TAG, "FAILED request");
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }
}
