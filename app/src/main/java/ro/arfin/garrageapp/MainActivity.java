package ro.arfin.garrageapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ro.arfin.garrageapp.Database.CarModelObject;
import ro.arfin.garrageapp.Database.DatabaseOperations;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseOperations databaseOperations = new DatabaseOperations();
    private ArrayList<CarModelObject> modelContent = databaseOperations.getModelList();

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView previousDay;
    private ImageView nextDay;
    private TextView currentDate;
    private Calendar cal = Calendar.getInstance();
    //private DatabaseQuery mQuery;
    private RelativeLayout mLayout;
    private Integer eventIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNetworkAvailable();
        databaseOperations.getDatabaseUpdate();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.calendar_view, null);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                CalendarView calendar = (CalendarView) mView.findViewById(R.id.calendarView);
                calendar.setDate(cal.getTimeInMillis());

                calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month,
                                                    int dayOfMonth) {
                        cal.set(year, month, dayOfMonth);
                        currentDate.setText(displayDateInString(cal.getTime()));
                        dialog.dismiss();

                    }
                });

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // mQuery = new DatabaseQuery(this);
        mLayout = (RelativeLayout) findViewById(R.id.left_event_column);
        eventIndex = mLayout.getChildCount();
        // eventIndex = 9;
        currentDate = (TextView) findViewById(R.id.display_current_date);
        currentDate.setText(displayDateInString(cal.getTime()));
      //  displayDailyEvents();
        previousDay = (ImageView) findViewById(R.id.previous_day);
        nextDay = (ImageView) findViewById(R.id.next_day);

        previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousCalendarDate();
            }
        });
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCalendarDate();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_constatare) {
            // Handle the camera action
        } else if (id == R.id.nav_programare) {

            Intent intent = new Intent(MainActivity.this, Programare.class);
            intent.putExtra("modelContent", modelContent);
            startActivity(intent);

        } else if (id == R.id.nav_revizie) {

        } else if (id == R.id.nav_calendar) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void isNetworkAvailable() {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return;
                        }
                    }

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Nu exista conexiune la internet. Aplicatia se va inchide.");
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                dlgAlert.create().show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String displayDateInString(Date mDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM, yyyy", Locale.getDefault());
        return formatter.format(mDate);
    }

    private void previousCalendarDate() {
      //  mLayout.removeViewAt(eventIndex - 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        currentDate.setText(displayDateInString(cal.getTime()));
       // displayDailyEvents();
    }

    private void nextCalendarDate() {
      //  mLayout.removeViewAt(eventIndex - 1);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        currentDate.setText(displayDateInString(cal.getTime()));
      //  displayDailyEvents();
    }

    private void displayDailyEvents() {
//        Date calendarDate = cal.getTime();
//        List<EventObjects> dailyEvent = mQuery.getAllFutureEvents(calendarDate);
//        for(EventObjects eObject : dailyEvent){
//            Date eventDate = eObject.getDate();
//            Date endDate = eObject.getEnd();
//            String eventMessage = eObject.getMessage();
//            int eventBlockHeight = getEventTimeFrame(eventDate, endDate);
//            Log.d(TAG, "Height " + eventBlockHeight);
//            displayEventSection(eventDate, eventBlockHeight, eventMessage);
//        }
        Date calendarDate = cal.getTime();

        DateFormat format = new SimpleDateFormat("d-MM-yyyy HH:mm", Locale.ENGLISH);
        System.out.println("AN " + calendarDate.getYear() + ":" + calendarDate.getMonth() + ":" + calendarDate.getDay());

        if (calendarDate.getYear() == 118 && calendarDate.getDay() == 0 && calendarDate.getMonth() == 6) {

            Date eventDate = null;
            try {
                eventDate = format.parse("8-07-2018 11:30");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date endDate = null;
            try {
                endDate = format.parse("8-07-2018 12:15");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String eventMessage = "Rezervare de proba.";

            EventObjects event = new EventObjects(eventMessage, eventDate, endDate);


            int eventBlockHeight = getEventTimeFrame(event.getDate(), event.getEnd());
            Log.d(TAG, "Height " + eventBlockHeight);
            displayEventSection(event.getDate(), eventBlockHeight, event.getMessage());
        }
    }

    private int getEventTimeFrame(Date start, Date end) {
        long timeDifference = end.getTime() - start.getTime();
        Calendar mCal = Calendar.getInstance();
        mCal.setTimeInMillis(timeDifference);
        int hours = mCal.get(Calendar.HOUR);
        int minutes = mCal.get(Calendar.MINUTE);
        return (hours * 60) + ((minutes * 60) / 100);
    }

    private void displayEventSection(Date eventDate, int height, String message) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String displayValue = timeFormatter.format(eventDate);
        String[] hourMinutes = displayValue.split(":");
        int hours = Integer.parseInt(hourMinutes[0]);
        int minutes = Integer.parseInt(hourMinutes[1]);
        Log.d(TAG, "Hour value " + hours);
        Log.d(TAG, "Minutes value " + minutes);
        int topViewMargin = (hours * 60) + ((minutes * 60) / 100);
        Log.d(TAG, "Margin top " + topViewMargin);
        createEventView(topViewMargin, height, message);
    }

    private void createEventView(int topMargin, int height, String message) {
        TextView mEventView = new TextView(MainActivity.this);
        RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lParam.topMargin = topMargin * 2;
        lParam.leftMargin = 24;
        mEventView.setLayoutParams(lParam);
        mEventView.setPadding(24, 0, 24, 0);
        mEventView.setHeight(height * 2);
        mEventView.setGravity(0x11);
        mEventView.setTextColor(Color.parseColor("#ffffff"));
        mEventView.setText(message);
        mEventView.setBackgroundColor(Color.parseColor("#3F51B5"));
        mLayout.addView(mEventView, eventIndex - 1);
    }
}
