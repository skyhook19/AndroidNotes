package com.dev.notes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dev.notes.R;
import com.dev.notes.model.db.HelperFactory;
import com.dev.notes.model.pojo.Note;
import com.dev.notes.model.pojo.NoteTags;
import com.dev.notes.model.pojo.Tag;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNoteActivity extends BaseActivity {

    private LatLng coord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddNoteButton();


        ListView choiceList = (ListView) findViewById(R.id.tagsLV);

        List<Tag> allTags = HelperFactory.getHelper().getTagDao().getAllTags();

        String[] tags = new String[allTags.size()];
        for (int i = 0; i < allTags.size(); i++) {
            tags[i] = allTags.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, tags);
        choiceList.setAdapter(adapter);




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


            List<Tag> allTags = HelperFactory.getHelper().getTagDao().getAllTags();

            String[] tags = new String[allTags.size()];
            for (int i = 0; i < allTags.size(); i++) {
                tags[i] = allTags.get(i).getName();
            }

            SparseBooleanArray chosen = ((ListView) findViewById(R.id.tagsLV)).getCheckedItemPositions();
            List<String> selected = new ArrayList<>();
            for (int i = 0; i < chosen.size(); i++) {
                if (chosen.valueAt(i)) {
                    selected.add(tags[chosen.keyAt(i)]);
                }
            }

            addNote(title, content, selected);
            forwardToNotesActivity();
        });
    }

    private void addNote(String title, String content, List<String> selectedTags) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setDate(new Date());
        if (coord != null) {
            note.setLatitude(coord.latitude);
            note.setLongitude(coord.longitude);
        }
        HelperFactory.getHelper().getNoteDao().create(note);

        for(String tagName: selectedTags) {
            Tag tag = HelperFactory.getHelper().getTagDao().getTagByName(tagName);

            NoteTags noteTag = new NoteTags();
            noteTag.setTag(tag);
            noteTag.setNote(note);
            HelperFactory.getHelper().getNoteTagsDao().create(noteTag);
        }
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
