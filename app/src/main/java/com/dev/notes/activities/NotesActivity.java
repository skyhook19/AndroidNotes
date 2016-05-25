package com.dev.notes.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dev.notes.model.db.HelperFactory;
import com.dev.notes.R;
import com.dev.notes.model.pojo.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshNotes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshNotes();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    private void refreshNotes() {
        List<Note> notes = new ArrayList<Note>();
        populateNotes(notes);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(notes, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }

    private void populateNotes(List<Note> notes) {
        notes.addAll(HelperFactory.getHelper().getNoteDao().getAllNotes());
    }
}
