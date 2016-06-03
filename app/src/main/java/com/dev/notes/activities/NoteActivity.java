package com.dev.notes.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.dev.notes.R;
import com.dev.notes.model.pojo.Note;
public class NoteActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Note note = (Note) extras.getSerializable("note");

            TextView textView = (TextView) findViewById(R.id.noteTitleTV);
            textView.setText(note.getTitle());

            TextView textView2 = (TextView) findViewById(R.id.noteContentTV);
            textView2.setText(note.getContent());
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_note;
    }

    @Override
    protected int getNavDrawerId() {
        return R.id.drawer_note;
    }

    @Override
    protected int getNavId() {
        return R.id.nav_view_note;
    }
}
