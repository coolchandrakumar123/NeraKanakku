package com.chan.nerakanakku.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.LocaleList;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chan.nerakanakku.R;

/**
 * Created by chandra-1765 on 7/21/16.
 */

public class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.ItemViewHolder> implements ItemTouchListener
{

    private Activity activity = null;
    private static final String[] STRINGS = new String[]{"One", "Two", "Three"};

    public interface OnDragStartListener
    {
        void onDragStarted(RecyclerView.ViewHolder viewHolder);
    }

    private final List<String> mItems = new ArrayList<>();

    private final OnDragStartListener mDragStartListener;

    public TasksRecyclerViewAdapter(Activity activity, OnDragStartListener dragStartListener)
    {
        mDragStartListener = dragStartListener;
        //mItems.addAll(Arrays.asList(STRINGS));
        this.activity = activity;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_list_item_row, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position)
    {
        holder.textView.setText(mItems.get(position));
        holder.handleView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN)
                {
                    mDragStartListener.onDragStarted(holder);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemDismiss(int position)
    {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition)
    {
        String prev = mItems.remove(fromPosition);
        mItems.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public int getItemCount()
    {
        return mItems.size();
    }

    public void addItem(String item)
    {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void removeItem()
    {
        int position = mItems.size() - 1;
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements ItemViewHolderListener
    {

        public final TextView textView;
        public final Button handleView;

        public ItemViewHolder(View itemView)
        {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            handleView = (Button) itemView.findViewById(R.id.handle);
        }

        @Override
        public void onItemSelected()
        {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear()
        {
            itemView.setBackgroundColor(0);
        }
    }
}
