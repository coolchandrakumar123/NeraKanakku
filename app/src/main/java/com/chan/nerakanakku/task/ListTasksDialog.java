package com.chan.nerakanakku.task;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import com.chan.nerakanakku.util.NeraKanakkuSharedPrefs;

/**
 * Created by chandra-1765 on 2/4/17.
 */

public class ListTasksDialog extends AlertDialog
{

    private Context context;
    private AlertDialog.Builder listDialogBuilder;
    private ArrayAdapter<String> arrayAdapter;
    private boolean isListAvailable = false;
    private TaskListener taskListener;

    private DialogInterface.OnClickListener cancelClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.dismiss();
        }
    };

    private DialogInterface.OnClickListener addTaskClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.dismiss();
            showAddTasksDialog();
        }
    };

    private DialogInterface.OnClickListener itemClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            String selectedTask = arrayAdapter.getItem(which);
            if(taskListener != null)
            {
                taskListener.onTaskSelected(selectedTask);
            }
            dialog.dismiss();
        }
    };

    public ListTasksDialog(Context context, TaskListener taskListener)
    {
        super(context);
        this.context = context;
        this.taskListener = taskListener;
        init();
    }

    private void init()
    {
        listDialogBuilder = new AlertDialog.Builder(context);
        listDialogBuilder.setTitle("Tasks:");

        arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);
        String tasksList = NeraKanakkuSharedPrefs.getTasksList(context);
        if(tasksList != null)
        {
            isListAvailable = true;
            String[] tasks = tasksList.split(NeraKanakkuSharedPrefs.SEPARATOR);
            arrayAdapter.addAll(tasks);
        }
        /*arrayAdapter.add("Hardik");
        arrayAdapter.add("Archit");
        arrayAdapter.add("Jignesh");
        arrayAdapter.add("Umang");
        arrayAdapter.add("Gatti");*/

        listDialogBuilder.setNegativeButton("cancel", cancelClickListener);
        listDialogBuilder.setPositiveButton("Add Task", addTaskClickListener);
        listDialogBuilder.setAdapter(arrayAdapter, itemClickListener);
    }

    public void show()
    {
        if(isListAvailable)
        {
            listDialogBuilder.show();
        }
        else
        {
            showAddTasksDialog();
        }
    }

    private void showAddTasksDialog()
    {
        new AddTasksDialog(context, taskListener).show();
    }

}
