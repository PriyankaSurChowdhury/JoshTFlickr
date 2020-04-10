package com.task.priyanka.joshtflickr.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.task.priyanka.joshtflickr.R;
import com.task.priyanka.joshtflickr.adapter.FlickrRecycleViewAdapter;
import com.task.priyanka.joshtflickr.listener.RecyclerItemClickListener;
import com.task.priyanka.joshtflickr.model.DownloadStatus;
import com.task.priyanka.joshtflickr.model.GetFlickrJsonData;
import com.task.priyanka.joshtflickr.model.PhotoData;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity implements GetFlickrJsonData.OnDataAvailable, RecyclerItemClickListener.OnRecyclerClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private FlickrRecycleViewAdapter recycleViewAdapter;
    private ArrayList<PhotoData> data;
    private DownloadStatus status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateToolbar(false);

        initRecycler();
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));
        recycleViewAdapter = new FlickrRecycleViewAdapter(new ArrayList<PhotoData>(), this);
        recyclerView.setAdapter(recycleViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.action_about) {
            showDialogAbout(MainActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GetFlickrJsonData flickrJsonData = new GetFlickrJsonData("en-us", true, this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String queryTags = sharedPreferences.getString(FLICKR_QUERY, "");
        if (queryTags != null) {
            if (queryTags.length() > 0) {
                flickrJsonData.execute(queryTags);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        GetFlickrJsonData flickrJsonData = new GetFlickrJsonData("en-us", true, this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String queryTags = sharedPreferences.getString(FLICKR_QUERY, "");
        if (queryTags != null) {
            if (queryTags.length() > 0) {
                flickrJsonData.execute(queryTags);
            }
        }
    }

    @Override
    public void onDataAvailable(ArrayList<PhotoData> data, DownloadStatus status) {
        this.data = data;
        this.status = status;
        if (status == DownloadStatus.OK) {
//            Log.d(TAG, "json---"+data);
            recycleViewAdapter.loadNewData(data);
        } else {
            Log.e(TAG, "Download failed---" + status);

        }

    }

    @Override
    public void OnItemClickListener(View view, int position) {
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        intent.putExtra(PHOTO_TRANSFER, recycleViewAdapter.getPhotoData(position));
        startActivity(intent);
    }

    private void showDialogAbout(final Context context) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Dialog);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_about);
        dialog.setCanceledOnTouchOutside(true);

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

}

