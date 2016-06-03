package com.dev.notes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.dev.notes.R;
import com.dev.notes.model.db.HelperFactory;
import com.dev.notes.model.pojo.Tag;


public class AddTagActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddTagButton();
    }

    private void initAddTagButton() {
        Button button = (Button) findViewById(R.id.addTagButton);
        button.setOnClickListener((v) -> {
            String name = ((TextView) findViewById(R.id.addTagName)).getText().toString();
            addTag(name);
            forwardToTagsActivity();
        });
    }

    private void addTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        HelperFactory.getHelper().getTagDao().create(tag);
    }

    private void forwardToTagsActivity() {
        Intent intent = new Intent(this, TagsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_tag;
    }

    @Override
    protected void initFloatButton() {
        //NOP
    }

    @Override
    protected int getNavDrawerId() {
        return R.id.drawer_addtag;
    }

    @Override
    protected int getNavId() {
        return R.id.nav_view_addtag;
    }
}
