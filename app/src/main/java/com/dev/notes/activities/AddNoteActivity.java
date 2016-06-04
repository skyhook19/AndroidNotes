package com.dev.notes.activities;

import android.content.Intent;
import android.location.Geocoder;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.notes.R;
import com.dev.notes.model.db.HelperFactory;
import com.dev.notes.model.pojo.Note;
import com.dev.notes.model.pojo.NoteTags;
import com.dev.notes.model.pojo.Tag;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddNoteActivity extends BaseActivity {

    private LatLng coord;
    private static final int COORD_REQUEST = 1;
    private boolean isItemEditing = false;
    private Note note;

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
            isItemEditing = true;
            note = (Note) extras.getSerializable("note");
            ((TextView) findViewById(R.id.addNoteTitle)).setText(note.getTitle());
            ((TextView) findViewById(R.id.addNoteContent)).setText(note.getContent());
        }
        else note = new Note();


        ((ImageButton) findViewById(R.id.attachLocationBtn)).setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivityForResult(intent, COORD_REQUEST);
        });


        extras = getIntent().getExtras();
        if (extras != null) {
            LatLng coord = extras.getParcelable("coord");
            this.coord = coord;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COORD_REQUEST) {
            if (resultCode == 7) {
                LatLng latLng = data.getParcelableExtra("coord");
                this.coord = latLng;
                Geocoder gcd = new Geocoder(this, Locale.getDefault());
                List<android.location.Address> addresses = null;
                try {
                    addresses = gcd.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addresses.size() > 0) {
                        //((ImageButton) findViewById(R.id.attachLocationBtn)).setText(addresses.get(0).getLocality());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initAddNoteButton() {
        ImageButton button = (ImageButton) findViewById(R.id.addNoteButton);
        button.setOnClickListener((v) -> {
            String title = ((TextView) findViewById(R.id.addNoteTitle)).getText().toString();
            String content = ((TextView) findViewById(R.id.addNoteContent)).getText().toString();

            if (title.trim().isEmpty() || content.trim().isEmpty()) {
                Toast.makeText(this, "Title or content is empty:(", Toast.LENGTH_SHORT).show();
                return;
            }

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
            note = null;
            isItemEditing = false;
            forwardToNotesActivity();
        });
    }

    private void addNote(String title, String content, List<String> selectedTags) {
        note.setTitle(title);
        note.setContent(content);
        if (coord != null) {
            note.setLatitude(coord.latitude);
            note.setLongitude(coord.longitude);
        }

        if(!isItemEditing) {
            note.setDate(new Date());
            HelperFactory.getHelper().getNoteDao().create(note);
            for (String tagName : selectedTags) {
                Tag tag = HelperFactory.getHelper().getTagDao().getTagByName(tagName);

                NoteTags noteTag = new NoteTags();
                noteTag.setTag(tag);
                noteTag.setNote(note);
                HelperFactory.getHelper().getNoteTagsDao().create(noteTag);
            }
        }
        else {
            HelperFactory.getHelper().getNoteDao().update(note);
            for (String tagName : selectedTags) {
                Tag tag = HelperFactory.getHelper().getTagDao().getTagByName(tagName);
                boolean hasTags = false;
                for(NoteTags noteTag : HelperFactory.getHelper().getNoteTagsDao().getAllNoteTags()){
                    if(noteTag.getNote() == note) {
                        noteTag.setTag(tag);
                        HelperFactory.getHelper().getNoteTagsDao().update(noteTag);
                        hasTags = true;
                    }
                }

                if(!hasTags){
                    NoteTags noteTag = new NoteTags();
                    noteTag.setTag(tag);
                    noteTag.setNote(note);
                    HelperFactory.getHelper().getNoteTagsDao().create(noteTag);
                }
            }
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
