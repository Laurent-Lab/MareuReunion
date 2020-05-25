package laurent.benard.mareureunion.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;

import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import laurent.benard.mareureunion.R;
import laurent.benard.mareureunion.controler.DI;
import laurent.benard.mareureunion.controler.InterfaceReunionApiServices;
import laurent.benard.mareureunion.model.Reunion;

public class addActivity extends AppCompatActivity {

    TextInputLayout sujetInput;
    TextInputLayout salleInput;
    TextInputEditText salleEditText;
    TextInputLayout dateInput;
    TextInputEditText dateEditText;
    TextInputLayout heureInput;
    TextInputEditText heureEditText;
    TextInputLayout participantsInput;
    ImageView colorSalleImage;
    int colorSalle;
    Button buttonAdd;
    Spinner spinnerCustom;
    final Calendar calendar = Calendar.getInstance();
    final Calendar calendarDate = Calendar.getInstance();
    private InterfaceReunionApiServices services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        services = DI.getReunionsApiServices();
        sujetInput = findViewById(R.id.txt_input_layout_sujet);
        salleInput = findViewById(R.id.txt_input_layout_lieu);
        dateInput = findViewById(R.id.txt_input_layout_date);
        dateEditText = findViewById(R.id.txt_input_date);
        heureInput = findViewById(R.id.txt_input_layout_heure);
        heureEditText = findViewById(R.id.txt_input_heure);
        participantsInput = findViewById(R.id.txt_input_layout_participants);
        colorSalleImage = findViewById(R.id.fragment_item_img_circle);
        spinnerCustom = findViewById(R.id.spinner);
        addOntimeSet();
        addOnDateSet();
        addSalleCustom();



        buttonAdd = findViewById(R.id.button_addReunion);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReunion();
            }
        });
    }

    /**
     * Selection d'une salle
     */
    private void addSalleCustom(){
        final ArrayList<String> sallesCustom = new ArrayList<>();
        sallesCustom.add("mario");
        sallesCustom.add("luigi");
        sallesCustom.add("vaultboy");
        spinnerCustom.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, sallesCustom));

        spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCustom.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * TimePicker
     */
    private void addOntimeSet(){
        final TimePickerDialog.OnTimeSetListener heureTimer = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendarDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarDate.set(Calendar.MINUTE, minute);
                String myFormat = "HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                heureEditText.setText(sdf.format(calendarDate.getTime()));
            }
        };

        heureEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(addActivity.this, heureTimer, calendarDate.get(Calendar.HOUR_OF_DAY),
                        calendarDate.get(Calendar.MINUTE), false).show();
            }
        });
    }

    /**
     * DatePicker
     */
    private void addOnDateSet(){
        final DatePickerDialog.OnDateSetListener dateTimer = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                dateEditText.setText(sdf.format(calendar.getTime()));
            }
        };
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(addActivity.this, dateTimer, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Couleur automatique
     */
    void addColor(){

        String mColor = spinnerCustom.getSelectedItem().toString();
        if (mColor.contains("mario")){
            colorSalle = (R.drawable.ic_fiber_manual_record_red_24dp);
        }
        else if(mColor.contains("luigi")){
            colorSalle = (R.drawable.ic_fiber_manual_record_green_24dp);
        }else {
            colorSalle = (R.drawable.ic_fiber_manual_record_yellow_24dp);
        }
   }

    /**
     * Création d'une réunion
     */
    public void addReunion() {

        String lieu = spinnerCustom.getSelectedItem().toString();
        addColor();
        String heure = heureInput.getEditText().getText().toString();
        String sujet = sujetInput.getEditText().getText().toString();
        String participants = participantsInput.getEditText().getText().toString();
        String date = dateInput.getEditText().getText().toString();
        int color = colorSalle;

        Reunion reunion = new Reunion(color, heure, lieu, sujet, participants, date);
        services.createReunion(reunion);
        finish();
    }

    /**
     * Valider une réunion
     * @return
     */
    private boolean validateReunion(){
        String sujet = sujetInput.getEditText().getText().toString().trim();
        String heure = heureInput.getEditText().getText().toString();
        String date = dateInput.getEditText().getText().toString();
        String lieu = spinnerCustom.getSelectedItem().toString().trim();
        String participants = participantsInput.getEditText().getText().toString().trim();

        if(sujet.isEmpty() || heure.isEmpty() || date.isEmpty() || lieu.isEmpty() || participants.isEmpty()){
            Toast.makeText(getApplicationContext(), "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
            return false;
        }else if(sujet.length() > 10) {
            sujetInput.setError("Ce champ doit contenir moins de 10 caractères");
            return false;
        }else if (participants.length() > 50) {
            participantsInput.setError("Ce champ doit contenir moins de 50 caractères");
            return false;
        }else {
            sujetInput.setError(null);
            participantsInput.setError(null);
            return true;
        }
    }

    /**
     * Enregistrer une réunion
     */
    private void saveReunion(){
        if (!validateReunion()){
            return;
        }else{
            addReunion();
        }
    }
}
