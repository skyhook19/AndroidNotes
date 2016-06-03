package com.dev.notes.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dev.notes.R;
import com.dev.notes.model.db.HelperFactory;
import com.dev.notes.model.pojo.Note;
import com.dev.notes.model.pojo.NoteTags;
import com.dev.notes.model.pojo.Tag;

import java.util.ArrayList;
import java.util.List;

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

            List<Tag> tags = new ArrayList<>();
            List<NoteTags> allNoteTags = HelperFactory.getHelper().getNoteTagsDao().getAllNoteTags();
            for (NoteTags noteTag: allNoteTags) {
                if (noteTag.getNote().getId().equals(note.getId())) {
                    tags.add(HelperFactory.getHelper().getTagDao().getTagById(noteTag.getTag().getId()));
                }
            }

            String[] tagNames = new String[tags.size()];
            for (int i = 0; i < tags.size(); i++) {
                tagNames[i] = tags.get(i).getName();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_multiple_choice, tagNames);
            ((ListView) findViewById(R.id.noteTags)).setAdapter(adapter);
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
