package com.task.priyanka.joshtflickr.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class RawJsonData extends AsyncTask<String, Void, String> {
    private static final String TAG = RawJsonData.class.getSimpleName();
    private final OnDownloadComplete Callback;
    private DownloadStatus downloadStatus;

    interface OnDownloadComplete {
        void onDownloadComplete(String data, DownloadStatus status);
    }

    public RawJsonData(OnDownloadComplete callback) {
        downloadStatus = DownloadStatus.IDLE;
        Callback = callback;
    }

    void runOnSameThread(String s) {
        Log.d(TAG, "runOnSameThread: starts");
//        onPostExecute(doInBackground(s));
        if (Callback != null) {
            Callback.onDownloadComplete(doInBackground(s), downloadStatus);
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (Callback != null) {
            Callback.onDownloadComplete(s, downloadStatus);
        }
        Log.d(TAG, "onPostExecute: COMPLETE");
    }


    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection feedloader = null;
        BufferedReader bufferedReader = null;
        if (strings == null) {
            downloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }

        try {
            downloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);
            feedloader = (HttpURLConnection) url.openConnection();
            feedloader.setRequestMethod("GET");
            feedloader.connect();
            int response = feedloader.getResponseCode();
            Log.d(TAG, "doInBackground---" + response);
            StringBuilder builder = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(feedloader.getInputStream()));
            String line;
            while (null != (line = bufferedReader.readLine())) {
                builder.append(line).append("\n");
            }
            downloadStatus = DownloadStatus.OK;
            return builder.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "Invalid URL " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "io error ---" + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "error " + e.getMessage());

        } finally {

            if (feedloader != null) {
                feedloader.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.e(TAG, "err " + e.getMessage());
                }
            }
        }
        downloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }

}
