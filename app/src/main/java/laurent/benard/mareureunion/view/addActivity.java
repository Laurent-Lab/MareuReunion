package laurent.benard.mareureunion.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.os.Bundle;

import android.text.InputType;
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
import laurent.benard.mareureunion.controler.InterfaceMeetingApiServices;
import laurent.benard.mareureunion.model.Meeting;

public class addActivity extends AppCompatActivity {

    TextInputLayout topicInput;
    TextInputLayout roomInput;
    TextInputLayout dateInput;
    TextInputEditText dateEditText;
    TextInputLayout hourInput;
    TextInputEditText hourEditText;
    TextInputEditText participantsEditText;
    ImageView colorRoomPicture;
    int colorRoom;
    Button buttonAdd;
    Spinner spinnerCustom;
    final Calendar calendar = Calendar.getInstance();
    final Calendar calendarDate = Calendar.getInstance();
    private InterfaceMeetingApiServices services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initActivity();
        addOntimeSet();
        addOnDateSet();
        addRoomCustom();
        registerMeeting();

    }

    /**
     * Initiation de la vue
     */
    public void initActivity(){
        services = DI.getMeetingsApiServices();
        topicInput = findViewById(R.id.txt_input_layout_sujet);
        roomInput = findViewById(R.id.txt_input_layout_lieu);
        dateInput = findViewById(R.id.txt_input_layout_date);
        dateEditText = findViewById(R.id.txt_input_date);
        hourInput = findViewById(R.id.txt_input_layout_heure);
        hourEditText = findViewById(R.id.txt_input_heure);
        participantsEditText = findViewById(R.id.txt_input_participants);
        colorRoomPicture = findViewById(R.id.fragment_item_img_circle);
        spinnerCustom = findViewById(R.id.spinner);
        buttonAdd = findViewById(R.id.button_addReunion);
    }

    /**
     * bouton addReunion
     */
    public void registerMeeting(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMeeting();
            }
        });
    }

    /**
     * Selection d'une salle
     */
    private void addRoomCustom(){
        final ArrayList<String> roomsCustom = new ArrayList<>();
        roomsCustom.add("mario");
        roomsCustom.add("luigi");
        roomsCustom.add("vaultboy");
        spinnerCustom.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, roomsCustom));

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
        final TimePickerDialog.OnTimeSetListener hourTimer = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendarDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarDate.set(Calendar.MINUTE, minute);
                String myFormat = "HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                hourEditText.setText(sdf.format(calendarDate.getTime()));
            }
        };

        hourEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(addActivity.this, hourTimer, calendarDate.get(Calendar.HOUR_OF_DAY),
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
            colorRoom = (R.drawable.ic_fiber_manual_record_red_24dp);
        }
        else if(mColor.contains("luigi")){
            colorRoom = (R.drawable.ic_fiber_manual_record_green_24dp);
        }else {
            colorRoom = (R.drawable.ic_fiber_manual_record_yellow_24dp);
        }
   }

    /**
     * Création d'une réunion
     */
    public void addMeeting() {

        String lieu = spinnerCustom.getSelectedItem().toString();
        addColor();
        String heure = hourInput.getEditText().getText().toString();
        String sujet = topicInput.getEditText().getText().toString();
        participantsEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        String participantsType = participantsEditText.getEditableText().toString();
        String date = dateInput.getEditText().getText().toString();
        int color = colorRoom;

        Meeting meeting = new Meeting(color, heure, lieu, sujet, participantsType, date);
        services.createMeeting(meeting);
        finish();
    }

    /**
     * Valider une réunion
     * @return
     */
    private boolean validateMeeting(){
        String topic = topicInput.getEditText().getText().toString().trim();
        String hour = hourInput.getEditText().getText().toString();
        String date = dateInput.getEditText().getText().toString();
        String location = spinnerCustom.getSelectedItem().toString().trim();
        String participantsType = participantsEditText.getEditableText().toString();

        if(topic.isEmpty() || hour.isEmpty() || date.isEmpty() || location.isEmpty() || participantsType.isEmpty()){
            Toast.makeText(getApplicationContext(), "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
            return false;
        }else if(topic.length() > 20) {
            topicInput.setError("Ce champ doit contenir moins de 20 caractères");
            return false;
        }else if (services.isEmailValid(participantsEditText.getText().toString()) == false){
            participantsEditText.setError("Vous devez rentrer un email valide");
            return false;
        }else {
            topicInput.setError(null);
            participantsEditText.setError(null);
            return true;
        }
    }

    /**
     * Enregistrer une réunion
     */
    private void saveMeeting(){
        if (!validateMeeting()){
            return;
        }else{
            addMeeting();
        }
    }
}
