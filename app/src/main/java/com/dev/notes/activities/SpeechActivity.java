package com.dev.notes.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.notes.R;
import com.dev.notes.model.db.HelperFactory;
import com.dev.notes.model.pojo.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SpeechActivity extends BaseActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnSpeak = (Button) findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(v -> promptSpeechInput());
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_speech;
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Note note = new Note();
                    note.setContent(result.get(0));
                    note.setTitle("Voice generated note");
                    note.setDate(new Date());
                    HelperFactory.getHelper().getNoteDao().create(note);
                    forwardToNotesActivity();
                }
                break;
            }
        }
    }

    private void forwardToNotesActivity() {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected int getNavDrawerId() {
        return R.id.drawer_speech;
    }

    @Override
    protected int getNavId() {
        return R.id.nav_view_speech;
    }
}
