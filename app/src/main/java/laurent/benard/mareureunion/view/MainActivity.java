package laurent.benard.mareureunion.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import laurent.benard.mareureunion.R;
import laurent.benard.mareureunion.controler.DI;
import laurent.benard.mareureunion.controler.DeleteMeetingEvent;
import laurent.benard.mareureunion.controler.InterfaceMeetingApiServices;
import laurent.benard.mareureunion.model.Meeting;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    FloatingActionButton btnFloat;

    private RecyclerView mRecyclerView;
    private MeetingsAdapter mMeetingsAdapter;
    private InterfaceMeetingApiServices services;
    private List<Meeting> mMeetings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reunions_list);
        services = DI.getMeetingsApiServices();
        mRecyclerView = findViewById(R.id.fragment_list_items);
        mMeetingsAdapter = new MeetingsAdapter(mMeetings);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(mMeetingsAdapter);
        btnFloat = findViewById(R.id.floatingActionButton);
        openActivityAdd();
    }

    /**
     * Lancement de addActivity
     */
    public void openActivityAdd(){
        btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), addActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem itemRoom = menu.findItem(R.id.salle_filter);
        final MenuItem itemDate = menu.findItem(R.id.date_filter);
        final MenuItem itemReset = menu.findItem(R.id.reset_filter);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(itemRoom);

        itemReset.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mMeetingsAdapter.getFilter().filter("");
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menu != null){
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            searchView.setIconified(true);
                            return false;
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            mMeetingsAdapter.getFilter().filter(newText);
                            mMeetingsAdapter.notifyDataSetChanged();
                            return false;
                        }
                    });
                }
            }
        });

        itemDate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                itemRoom.collapseActionView();
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dilog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String monthText;
                        String dayText;
                        month = month + 1;
                        if (month<10){
                            monthText = "0" + month;
                        }else{
                            monthText = String.valueOf(month);
                        }
                        if (dayOfMonth<10){
                            dayText = "0" + dayOfMonth;
                        }else{
                            dayText = String.valueOf(dayOfMonth);
                        }
                        String date = dayText + "/" + monthText + "/" + year;
                        mMeetingsAdapter.getFilter().filter(date);
                    }
                }, year, month, day
                );
                dilog.show();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    /**
     *
     * @param view
     * @param year
     * @param month
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) { }

    /**
     * Initie la liste de réunions
     */
    private void init(){
        mMeetings.clear();
        mMeetings.addAll(services.getMeetings());
        mMeetingsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    /**
     * Suppression des réunions à la rotation de l'écran
     */
    @Override
    public void onDestroy(){
        DI.getNewInstanceApiService();
        super.onDestroy();
    }

    /**
     *
     * @param event
     */
    @Subscribe
    public void onDeleteReunion(DeleteMeetingEvent event) {
        services.deleteMeeting(event.meeting);
        init();
    }
}
