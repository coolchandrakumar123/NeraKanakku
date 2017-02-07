package com.chan.nerakanakku;

import com.chan.nerakanakku.task.TasksSummaryFragment;
import com.chan.nerakanakku.timer.TimerFragment;
import com.chan.nerakanakku.util.NeraKanakkuSharedPrefs;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
{

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;

    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem)
        {
            switch(menuItem.getItemId())
            {
                case R.id.drawer_task_schedule:
                    // Do nothing, we're already on that screen
                    showTimerFragment();
                    break;
                case R.id.drawer_task_summary:
                    showTasksSummaryFragment();
                    break;
                case R.id.drawer_task_clear:
                    NeraKanakkuSharedPrefs.clearAllItems(getApplicationContext());
                    break;
                default:
                    break;
            }
            // Close the navigation drawer when an item is selected.
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView != null)
        {
            navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
        }
        showTimerFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTimerFragment()
    {
        TimerFragment timerFragment = TimerFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.contentFrame, timerFragment);
        transaction.commit();
        toolbar.setTitle(getResources().getString(R.string.timer_scheduling));
    }

    private void showTasksSummaryFragment()
    {
        TasksSummaryFragment tasksSummaryFragment = TasksSummaryFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.contentFrame, tasksSummaryFragment);
        transaction.commit();
        toolbar.setTitle(getResources().getString(R.string.action_summary));
    }
}
