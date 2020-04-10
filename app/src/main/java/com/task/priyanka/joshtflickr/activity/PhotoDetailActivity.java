package com.task.priyanka.joshtflickr.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.task.priyanka.joshtflickr.R;
import com.task.priyanka.joshtflickr.model.PhotoData;

public class PhotoDetailActivity extends BaseActivity {
    TextView tv_photoDetailTitle, tv_photoDetailTags, tv_photoAuthor;
    ImageView iv_photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        activateToolbar(true);

        initUi();
    }

    private void initUi() {

        tv_photoDetailTitle = findViewById(R.id.tv_photoDetailTitle);
        tv_photoDetailTags = findViewById(R.id.tv_photoDetailTags);
        tv_photoAuthor = findViewById(R.id.tv_photoAuthor);

        Intent intent = getIntent();
        PhotoData photoData = (PhotoData) intent.getSerializableExtra(PHOTO_TRANSFER);
        if (photoData != null) {
            Resources resources = getResources();

            iv_photoView = findViewById(R.id.iv_photoView);
            Glide.with(this)
                    .load(photoData.getBig_img_url())
                    .apply(new RequestOptions()
                            .error(R.drawable.ic_photo)
                    )
                    .into(iv_photoView);

            tv_photoDetailTitle.setText(resources.getString(R.string.photo_title_text, photoData.getTitle()));
            tv_photoDetailTags.setText(resources.getString(R.string.photo_tags_text, photoData.getTags()));
            tv_photoAuthor.setText(resources.getString(R.string.photo_author_text, photoData.getAuthor()));
        }
    }

}
