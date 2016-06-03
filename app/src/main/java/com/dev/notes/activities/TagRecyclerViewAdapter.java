package com.dev.notes.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dev.notes.R;
import com.dev.notes.model.db.HelperFactory;
import com.dev.notes.model.pojo.Tag;

import java.util.List;


public class TagRecyclerViewAdapter extends RecyclerView.Adapter<TagRecyclerViewAdapter.ViewHolder> {
    private List<Tag> tags;

    public TagRecyclerViewAdapter(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tags_recyclerviewitem, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Tag tag = tags.get(i);
        viewHolder.id = tag.getId();
        viewHolder.name.setText(tag.getName());
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Long id;
        private TextView name;
        private Button deleteButton;
        private DeleteButtonListener deleteButtonListener;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tag_title);
            deleteButton = (Button) itemView.findViewById(R.id.tagViewItemDeleteButton);
            deleteButtonListener = new DeleteButtonListener();
            deleteButton.setOnClickListener(deleteButtonListener);
        }
    }

    private void delete(Tag tag) {
        int position = tags.indexOf(tag);
        tags.remove(position);
        HelperFactory.getHelper().getTagDao().delete(tag);
        notifyItemRemoved(position);
    }

    private class DeleteButtonListener implements View.OnClickListener {
        private Tag tag;

        @Override
        public void onClick(View v) {
            delete(tag);
        }

        public void setNote(Tag tag) {
            this.tag = tag;
        }
    }
}
