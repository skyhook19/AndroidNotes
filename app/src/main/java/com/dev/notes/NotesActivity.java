package com.dev.notes;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dev.notes.model.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Note> notes = new ArrayList<Note>();
        populateNotes(notes);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
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

    private void populateNotes(List<Note> notes){
        for (int i = 0; i<50; i++){
            Note note = new Note();
            note.setTitle("Item №" + i);
            note.setContent("awdawdawdawdfgfgbfawdawdawdawdfgfgbfawdawdawd" +
                    "awdfgfgbfawdawdawdawdfgfgbfawdawdawdawdfgfgbfawdawdawdawdfgfgbf");
            note.setDate(new Date());
            note.setType(Note.Type.values()[i%3]);
            notes.add(note);
        }
    }
}
