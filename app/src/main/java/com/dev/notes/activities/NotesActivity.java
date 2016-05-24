package com.dev.notes.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    private void populateNotes(List<Note> notes) {
        for (Long i = 0L; i < 10L; i++) {
            Note note = new Note();
            note.setId(i);
            note.setTitle("Item №" + i);
            note.setContent("awdawdawdawdfgfgbfawdawdawdawdfgfgbfawdawdawd" +
                    "awdfgfgbfawdawdawdawdfgfgbfaw");
            note.setDate(new Date());
            HelperFactory.getHelper().getNoteDao().create(note);
        }
        List<Note> allNotes = HelperFactory.getHelper().getNoteDao().getAllNotes();
        notes.addAll(allNotes);
    }
}
