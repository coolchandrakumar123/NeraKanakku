/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chan.nerakanakku.task;

import com.chan.nerakanakku.R;
import com.chan.nerakanakku.util.NeraKanakkuSharedPrefs;
import com.chan.nerakanakku.util.NeraKanakkuUtil;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TasksSummaryFragment extends Fragment implements TasksRecyclerViewAdapter.OnDragStartListener
{

    private Context context;
    //private TextView timerTextView;
    //private TextView taskTextView;
    private View root;
    private TasksRecyclerViewAdapter recyclerViewAdapter;
    private ItemTouchHelper itemTouchHelper;

    public TasksSummaryFragment()
    {
        // Requires empty public constructor
    }

    public static TasksSummaryFragment newInstance()
    {
        return new TasksSummaryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.tasks_summary, container, false);
        context = getActivity();
        //timerTextView = (TextView) root.findViewById(R.id.timer_text);
        //taskTextView = (TextView) root.findViewById(R.id.task_text);
        //clearText();
        initRecyclerView();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        showTaskSummary();
    }

    private void initRecyclerView()
    {
        recyclerViewAdapter = new TasksRecyclerViewAdapter(getActivity(), this);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(recyclerViewAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /*private void clearText()
    {
        taskTextView.setText("Beat the Best");
        timerTextView.setText("00:00:00");
    }*/

    private void showTaskSummary()
    {
        String tasksList = NeraKanakkuSharedPrefs.getTasksList(context);
        if(tasksList != null && tasksList != "")
        {
            String[] tasks = tasksList.split(NeraKanakkuSharedPrefs.SEPARATOR);
            for(int i = 0;i < tasks.length;i++)
            {
                String task = tasks[i];
                long workMillis = NeraKanakkuSharedPrefs.getTaskWorkTime(context, task);
                String timerStr = task + " : " + NeraKanakkuUtil.convertMilliSeconds(workMillis);
                recyclerViewAdapter.addItem(timerStr);
                //summaryList += task + " : " + timerStr + "\n";
            }
        }
        else
        {
            Snackbar.make(root, "No Items", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDragStarted(RecyclerView.ViewHolder viewHolder)
    {
        itemTouchHelper.startDrag(viewHolder);
    }

}
