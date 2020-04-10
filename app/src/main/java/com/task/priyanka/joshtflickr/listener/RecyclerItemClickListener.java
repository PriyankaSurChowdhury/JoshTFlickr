package com.task.priyanka.joshtflickr.listener;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = RecyclerItemClickListener.class.getSimpleName();

    public interface OnRecyclerClickListener {
        void OnItemClickListener(View view, int position);
    }

    private final OnRecyclerClickListener mlistener;
    private final GestureDetectorCompat mgestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerClickListener listener) {
        this.mlistener = listener;
        this.mgestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp");
                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (view != null && mlistener != null) {
                    Log.d(TAG, "onItemClick");
                    mlistener.OnItemClickListener(view, recyclerView.getChildAdapterPosition(view));

                }
                return true;

            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mgestureDetector != null) {
            boolean result = mgestureDetector.onTouchEvent(e);
            return result;
        } else {
            return false;
        }
    }
}
