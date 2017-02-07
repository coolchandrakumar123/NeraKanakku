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

package com.chan.nerakanakku.timer;

import com.chan.nerakanakku.R;
import com.chan.nerakanakku.task.ListTasksDialog;
import com.chan.nerakanakku.task.TaskListener;
import com.chan.nerakanakku.util.NeraKanakkuSharedPrefs;
import com.chan.nerakanakku.util.NeraKanakkuUtil;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TimerFragment extends Fragment implements TimerContract.View, TaskListener
{

    private Context context;
    private TimerContract.Presenter timerPresenter;
    private TextView timerTextView;
    private TextView taskTextView;
    private FloatingActionButton fab;
    private View root;

    private View.OnClickListener fabClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            triggerTimer();
        }
    };

    public TimerFragment()
    {
        // Requires empty public constructor
    }

    public static TimerFragment newInstance()
    {
        return new TimerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.content_main, container, false);
        context = getActivity();
        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(fabClickListener);
        timerPresenter = new TimerPresenter(this);
        timerTextView = (TextView) root.findViewById(R.id.timer_text);
        taskTextView = (TextView) root.findViewById(R.id.task_text);
        clearText();
        return root;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        String runningTask = NeraKanakkuSharedPrefs.getRunningTask(context);
        String timerState = NeraKanakkuSharedPrefs.getTimerState(context);
        if(runningTask != null && NeraKanakkuSharedPrefs.RUNNING.equals(timerState))
        {
            onTaskSelected(runningTask);
        }
    }

    private void clearText()
    {
        taskTextView.setText("Beat the Best");
        timerTextView.setText("00:00:00");
    }

    private void triggerTimer()
    {
        String timerState = NeraKanakkuSharedPrefs.getTimerState(context);
        if(NeraKanakkuSharedPrefs.STOPPED.equals(timerState))
        {
            new ListTasksDialog(getActivity(), this).show();
        }
        else
        {
            stopTimer();
        }
    }

    private void stopTimer()
    {
        fab.setImageResource(android.R.drawable.ic_media_play);
        timerPresenter.stopTimer();
        NeraKanakkuSharedPrefs.setTimerState(context, NeraKanakkuSharedPrefs.STOPPED);
        String runningTask = NeraKanakkuSharedPrefs.getRunningTask(context);
        if(runningTask != null)
        {
            long startMillis = NeraKanakkuSharedPrefs.getTaskStartTime(context);
            long diffMillis = System.currentTimeMillis() - startMillis;
            long workMillis = NeraKanakkuSharedPrefs.getTaskWorkTime(context, runningTask) + diffMillis;
            NeraKanakkuSharedPrefs.setTaskWorkTime(context, runningTask, workMillis);
            NeraKanakkuSharedPrefs.removeTaskStartTime(context);
            NeraKanakkuSharedPrefs.removeRunningTask(context);
        }
    }

    @Override
    public void onTaskSelected(String task)
    {
        taskTextView.setText(task);
        fab.setImageResource(android.R.drawable.ic_media_pause);

        NeraKanakkuSharedPrefs.setTimerState(context, NeraKanakkuSharedPrefs.RUNNING);
        NeraKanakkuSharedPrefs.setRunningTask(context, task);
        long startMillis = NeraKanakkuSharedPrefs.getTaskStartTime(context);
        if(startMillis == 0)
        {
            startMillis = System.currentTimeMillis();
            NeraKanakkuSharedPrefs.setTaskStartTime(context, startMillis);
        }
        timerPresenter.startTimer(timerTextView, startMillis);
    }

    private void showTaskSummary()
    {
        String summaryList = "";
        String tasksList = NeraKanakkuSharedPrefs.getTasksList(context);
        if(tasksList != null)
        {
            String[] tasks = tasksList.split(NeraKanakkuSharedPrefs.SEPARATOR);
            for(int i=0; i < tasks.length; i++)
            {
                String task = tasks[i];
                long workMillis = NeraKanakkuSharedPrefs.getTaskWorkTime(context, task);
                String timerStr = NeraKanakkuUtil.convertMilliSeconds(workMillis);
                summaryList += task + " : " + timerStr + "\n";
            }
        }
        if(summaryList != "")
        {
            taskTextView.setText("Task Summary");
            timerTextView.setText(summaryList);
        }
        else
        {
            Snackbar.make(root, "No Items", Snackbar.LENGTH_LONG).show();
        }
    }

}
