package com.dev.notes.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.dev.notes.R;

public class NoteActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("noteTitle");
            TextView textView = (TextView) findViewById(R.id.noteTitleTV);
            textView.setText(title);
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_note;
    }
}
