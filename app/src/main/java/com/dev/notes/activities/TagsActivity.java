package com.dev.notes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dev.notes.R;
import com.dev.notes.model.db.HelperFactory;
import com.dev.notes.model.pojo.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshTags();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshTags();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_tags;
    }

    @Override
    protected int getNavDrawerId() {
        return R.id.drawer_tags;
    }

    @Override
    protected int getNavId() {
        return R.id.nav_view_tags;
    }

    private void refreshTags() {
        List<Tag> tags = new ArrayList<Tag>();
        populateTags(tags);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tagRecyclerView);
        TagRecyclerViewAdapter adapter = new TagRecyclerViewAdapter(tags);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }

    private void populateTags(List<Tag> tags) {
        tags.addAll(HelperFactory.getHelper().getTagDao().getAllTags());
    }

    protected void initFloatButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            forwardToAddTagActivity();
        });
    }

    private void forwardToAddTagActivity() {
        Intent intent = new Intent(this, AddTagActivity.class);
        startActivity(intent);
    }
}
