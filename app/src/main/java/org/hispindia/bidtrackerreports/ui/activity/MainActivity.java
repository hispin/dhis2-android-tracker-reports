package org.hispindia.bidtrackerreports.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.hisp.dhis.android.sdk.ui.activities.INavigationHandler;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.ui.fragment.HICFragmentBase;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private HICFragmentBase hifragmentbased;
//    private static String TAG = MainActivity.class.getSimpleName();
    public final static String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    protected INavigationHandler mNavigationHandler;
//     @Bind(R.id.button)
//      Button button;
//
//     @Bind(R.id.button1)
//     Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hifragmentbased= new HICFragmentBase(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);


//        Button btn1 = (Button) findViewById(R.id.button1);
//        btn1.setOnClickListener(new OnClickListener() {
//
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(MainActivity.this, HIFragmentMain.class);
//                startActivity(intent);
//               // mNavigationHandler.switchFragment(new HIFragmentDistrictStockInHandReport(), HIFragmentDistrictStockInHandReport.TAG, true);
//                Toast.makeText(getBaseContext(), "Your answer is correct!" , Toast.LENGTH_SHORT ).show();
//            }
//        });
//        Button btn2 = (Button) findViewById(R.id.button);
//        btn2.setOnClickListener(new OnClickListener() {
//
//            public void onClick(View v)
//            {
//                Toast.makeText(getBaseContext(), "Your answer is correct!" , Toast.LENGTH_SHORT ).show();
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (position) {

            case 0:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName("org.hispindia.bidtrackerreports","org.hispindia.bidtrackerreports.ui.activity.HIActivitySplash"));
                startActivity(intent);
               //mNavigationHandler.switchFragment(new HIFragmentTodayScheduleReport(), HIFragmentTodayScheduleReport.TAG, true);
//                mNavigationHandler.switchFragment(new HIFragmentTodayScheduleReport(), HIFragmentTodayScheduleReport.TAG, true);
                title = getString(R.string.title_facility);
                break;
            case 1:
                Intent intent1 = new Intent(Intent.ACTION_MAIN);
                intent1.setComponent(new ComponentName("org.hisp.dhis.android.trackercapture","org.hisp.dhis.android.trackercapture.ui.MainActivity"));
                startActivity(intent1);
                //mNavigationHandler.switchFragment(new HIFragmentVaccineStatusReport(), HIFragmentVaccineStatusReport.TAG, true);
                //fragment = new HIFragmentVaccineStatusReport();
                title = getString(R.string.title_vaccine);
                break;
            case 2:
                Toast.makeText(getApplicationContext(), "Stock In Hand Report", Toast.LENGTH_SHORT).show();
                //mNavigationHandler.switchFragment(new HIFragmentStockInHandReport(), HIFragmentStockInHandReport.TAG, true);
                title = getString(R.string.title_stock);
                break;
            case 3:
                Toast.makeText(getApplicationContext(), "Stock Demand Report", Toast.LENGTH_SHORT).show();
                //mNavigationHandler.switchFragment(new HIFragmentStockDemandReport(), HIFragmentStockDemandReport.TAG, true);
                title = getString(R.string.title_demand);
                break;
            case 4:
                Toast.makeText(getApplicationContext(), "Hand vs Demand Report", Toast.LENGTH_SHORT).show();
                //mNavigationHandler.switchFragment(new HIFragmentStockInHandvsDemandReport(), HIFragmentStockInHandvsDemandReport.TAG, true);
                title = getString(R.string.title_handvsdemand);
                break;
            case 5:
                Toast.makeText(getApplicationContext(), "District Level Stock", Toast.LENGTH_SHORT).show();
                //mNavigationHandler.switchFragment(new HIFragmentDistrictStockInHandReport(), HIFragmentDistrictStockInHandReport.TAG, true);
                title = getString(R.string.title_district);
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }

    }

//    @OnClick(R.id.button)
//    public void button() {
//        mNavigationHandler.switchFragment(new HIFragmentDistrictStockInHandReport(), HIFragmentDistrictStockInHandReport.TAG, true);
//        Toast.makeText(getApplicationContext(), "Vaccinated Children Report", Toast.LENGTH_SHORT).show();
//    }
//    @OnClick(R.id.button1)
//    public void button1() {
//        mNavigationHandler.switchFragment(new HIFragmentDistrictStockInHandReport(), HIFragmentDistrictStockInHandReport.TAG, true);
//        Toast.makeText(getApplicationContext(), "Vaccinated Children Report", Toast.LENGTH_SHORT).show();
//    }

}