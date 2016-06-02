package com.dev.notes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.dev.notes.R;
import com.dev.notes.model.db.HelperFactory;
import com.dev.notes.model.pojo.Note;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class AddNoteActivity extends BaseActivity {

    private LatLng coord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddNoteButton();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            LatLng coord = extras.getParcelable("coord");
            this.coord = coord;
        }
    }

    private void initAddNoteButton() {
        Button button = (Button) findViewById(R.id.addNoteButton);
        button.setOnClickListener((v) -> {
            String title = ((TextView) findViewById(R.id.addNoteTitle)).getText().toString();
            String content = ((TextView) findViewById(R.id.addNoteContent)).getText().toString();

            addNote(title, content);
            forwardToNotesActivity();
        });
    }

    private void addNote(String title, String content) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setDate(new Date());
        note.setLatitude(coord.latitude);
        note.setLongitude(coord.longitude);
        HelperFactory.getHelper().getNoteDao().create(note);
    }

    private void forwardToNotesActivity() {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_note;
    }

    @Override
    protected void initFloatButton() {
        //NOP
    }

    @Override
    protected int getNavDrawerId() {
        return R.id.drawer_addnote;
    }

    @Override
    protected int getNavId() {
        return R.id.nav_view_addnote;
    }
}
