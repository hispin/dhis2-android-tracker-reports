package org.hispindia.bidtrackerreports.ui.activity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.Select;

import org.hisp.dhis.android.sdk.controllers.DhisController;
import org.hisp.dhis.android.sdk.controllers.DhisService;
import org.hisp.dhis.android.sdk.persistence.models.UserAccount;
import org.hisp.dhis.android.sdk.ui.activities.INavigationHandler;
import org.hisp.dhis.android.sdk.ui.activities.LoginActivity;
import org.hisp.dhis.android.sdk.utils.UiUtils;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.ui.fragment.HICFragmentBase;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private HICFragmentBase hifragmentbased;
//    private static String TAG = MainActivity.class.getSimpleName();
    public final static String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    protected INavigationHandler mNavigationHandler;
     Button buttonlogout;
    TextView tvname;
    //
//     @Bind(R.id.tvdname)
//    TextView tvdname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvname=(TextView)findViewById(R.id.tvdname);
       // buttonlogout=(Button)findViewById(R.id.buttonlogout);
//        hifragmentbased= new HICFragmentBase(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
        String dname=new Select().from(UserAccount.class).querySingle().getDisplayName();
        Log.e(TAG,"DNAME: in SM "+dname );
        tvname.setText(dname);



        Button buttonlogout= (Button) findViewById(R.id.buttonlogout);

        buttonlogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UiUtils.showConfirmDialog(MainActivity.this, getString(org.hisp.dhis.android.sdk.R.string.logout_title), getString(org.hisp.dhis.android.sdk.R.string.logout_message),
                        getString(org.hisp.dhis.android.sdk.R.string.logout_option), getString(org.hisp.dhis.android.sdk.R.string.cancel_option), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (DhisController.hasUnSynchronizedDatavalues) {
                                    //show error dialog
                                    UiUtils.showErrorDialog(MainActivity.this, getString(org.hisp.dhis.android.sdk.R.string.error_message),
                                            getString(org.hisp.dhis.android.sdk.R.string.unsynchronized_data_values),
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                } else {
                                    DhisService.logOutUser(MainActivity.this);
                                   finish();
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                }
                            }
                        });
            }
        });

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
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
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

//            case 0:
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.setComponent(new ComponentName("org.hispindia.bidtrackerreports","org.hispindia.bidtrackerreports.ui.activity.HIActivitySplash"));
//                startActivity(intent);
//               //mNavigationHandler.switchFragment(new HIFragmentTodayScheduleReport(), HIFragmentTodayScheduleReport.TAG, true);
////                mNavigationHandler.switchFragment(new HIFragmentTodayScheduleReport(), HIFragmentTodayScheduleReport.TAG, true);
//                title = getString(R.string.title_facility);
//                break;
            case 1:
                Intent intent1 = new Intent(Intent.ACTION_MAIN);
                intent1.setComponent(new ComponentName("org.hispindia.bidtrackerreports","org.hispindia.bidtrackerreports.ui.activity.HIActivitySplash"));
                startActivity(intent1);
                //mNavigationHandler.switchFragment(new HIFragmentVaccineStatusReport(), HIFragmentVaccineStatusReport.TAG, true);
                //fragment = new HIFragmentVaccineStatusReport();
                title = getString(R.string.title_vaccine);
                break;
            case 2:
                boolean installed = appInstalledOrNot("org.hisp.dhis.android.eventcapture");
                if(installed) {
                    //This intent will help you to launch if the package is already installed
                    Intent LaunchIntent = getPackageManager()
                            .getLaunchIntentForPackage("org.hisp.dhis.android.eventcapture");
                    startActivity(LaunchIntent);
                    Intent intent2 = new Intent(Intent.ACTION_MAIN);
                    intent2.setComponent(new ComponentName("org.hisp.dhis.android.eventcapture","org.hisp.dhis.android.sdk.ui.activities.SplashActivity"));
                    startActivity(intent2);
                    Toast.makeText(getApplicationContext(), "Switching to EventCapture", Toast.LENGTH_SHORT).show();
                    System.out.println("App is already installed on your phone");
                } else {
                    Toast.makeText(getApplicationContext(), "Eventcapture is not currently installed on your phone", Toast.LENGTH_SHORT).show();
                    System.out.println("App is not currently installed on your phone");
                }

                break;

            case 3:
                boolean installed1 = appInstalledOrNot("org.hisp.dhis.android.trackercapture");
                if(installed1) {
                    //This intent will help you to launch if the package is already installed
                    Intent LaunchIntent = getPackageManager()
                            .getLaunchIntentForPackage("org.hisp.dhis.android.trackercapture");
                    startActivity(LaunchIntent);
                    Intent intent2 = new Intent(Intent.ACTION_MAIN);
                    intent2.setComponent(new ComponentName("org.hisp.dhis.android.trackercapture","org.hisp.dhis.android.sdk.ui.activities.SplashActivity"));
                    startActivity(intent2);
                    Intent intent3 = new Intent(Intent.ACTION_MAIN);
                    intent3.setComponent(new ComponentName("org.hisp.dhis.android.trackercapture","org.hisp.dhis.android.sdk.ui.activities.SplashActivity"));
                    startActivity(intent3);
                    Toast.makeText(getApplicationContext(), "Switching to Trackercapture", Toast.LENGTH_SHORT).show();
                    System.out.println("App is already installed on your phone");
                } else {
                    Toast.makeText(getApplicationContext(), "Trackercapture is not currently installed on your phone", Toast.LENGTH_SHORT).show();
                    System.out.println("App is not currently installed on your phone");
                }

                break;
            case 4:
//                Toast.makeText(getApplicationContext(), "Switching to Datacapture", Toast.LENGTH_SHORT).show();
//                Intent intent4 = new Intent(Intent.ACTION_MAIN);
//                intent4.setComponent(new ComponentName("org.hisp.dhis.android.datacapture","org.hisp.dhis.android.sdk.ui.activities.SplashActivity"));
//                startActivity(intent4);
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