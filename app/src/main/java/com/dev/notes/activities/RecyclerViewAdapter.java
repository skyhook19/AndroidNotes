package com.dev.notes.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dev.notes.model.db.HelperFactory;
import com.dev.notes.R;
import com.dev.notes.model.pojo.Note;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Note> notes;
    private Activity activity;

    public RecyclerViewAdapter(List<Note> notes, Activity activity) {
        this.notes = notes;
        this.activity = activity;
    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NoteActivity.class);
                intent.putExtra("noteTitle", "my bad");
                activity.startActivity(intent);
            }
        });
        return new ViewHolder(v);
    }

    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Note note = notes.get(i);
        viewHolder.title.setText(note.getTitle());
        viewHolder.date.setText(note.getDate().toString());
        viewHolder.deleteButtonListener.setRecord(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private void delete(Note note) {
        int position = notes.indexOf(note);
        notes.remove(position);
        HelperFactory.getHelper().getNoteDao().delete(note);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView date;
        private Button deleteButton;
        private DeleteButtonListener deleteButtonListener;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.note_title);
            date = (TextView) itemView.findViewById(R.id.note_date);
            deleteButton = (Button) itemView.findViewById(R.id.recyclerViewItemDeleteButton);
            deleteButtonListener = new DeleteButtonListener();
            deleteButton.setOnClickListener(deleteButtonListener);
        }
    }

    private class DeleteButtonListener implements View.OnClickListener {
        private Note note;

        @Override
        public void onClick(View v) {
            delete(note);
        }

        public void setRecord(Note note) {
            this.note = note;
        }
    }
}
