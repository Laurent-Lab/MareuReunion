package laurent.benard.mareureunion.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.List;

import laurent.benard.mareureunion.R;
import laurent.benard.mareureunion.controler.DI;
import laurent.benard.mareureunion.controler.InterfaceReunionApiServices;
import laurent.benard.mareureunion.controler.ReunionsGenerateur;
import laurent.benard.mareureunion.model.Reunion;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ReunionsAdapter mReunionsAdapter;
    private InterfaceReunionApiServices services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_reunions_list);
        services = DI.getReunionsApiServices();

        mRecyclerView = findViewById(R.id.fragment_list_items);
        mReunionsAdapter = new ReunionsAdapter(services.getReunions());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(mReunionsAdapter);

    }
}
