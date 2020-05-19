package laurent.benard.mareureunion.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import laurent.benard.mareureunion.R;
import laurent.benard.mareureunion.controler.DI;
import laurent.benard.mareureunion.controler.DeleteReunionEvent;
import laurent.benard.mareureunion.controler.InterfaceReunionApiServices;
import laurent.benard.mareureunion.controler.QueryEvent;
import laurent.benard.mareureunion.model.Reunion;

public class MainActivity extends AppCompatActivity  {

    FloatingActionButton btnFloat;

    private RecyclerView mRecyclerView;
    private ReunionsAdapter mReunionsAdapter;
    private InterfaceReunionApiServices services;
    private List<Reunion> mReunions = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reunions_list);
        services = DI.getReunionsApiServices();
        mRecyclerView = findViewById(R.id.fragment_list_items);
        mReunionsAdapter = new ReunionsAdapter(mReunions);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(mReunionsAdapter);

        btnFloat = findViewById(R.id.floatingActionButton);

        btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), addActivity.class);
                v.getContext().startActivity(intent);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_filter);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mReunionsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    /**
     * Initie la liste de réunions
     */
    private void init(){
        mReunions.clear();
        mReunions.addAll(services.getReunions());
        mReunionsAdapter.notifyDataSetChanged();
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
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    /**
     *
     * @param event
     */
    @Subscribe
    public void onDeleteReunion(DeleteReunionEvent event) {
        services.deleteReunion(event.reunion);
        init();
    }

}
