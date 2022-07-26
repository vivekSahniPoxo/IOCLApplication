package com.example.ioclapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AppConfigureForm extends AppCompatActivity {
    EditText devicenm;
    TextView devicenameshow;
    Button save, update;
    LocalDB localDB;
    public static String DEviceNAME = "";
    List<DatamodelLocal> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_configure_form);
        devicenm = findViewById(R.id.Devicename);
        devicenameshow = findViewById(R.id.SetDevicename);
        save = findViewById(R.id.buttonSave);
        update = findViewById(R.id.buttonUpdate);

        list = new ArrayList<>();
        localDB = new LocalDB(this);
        if (localDB.isTableExists()) {
            list = localDB.getAllContacts();
            if (list.size() > 0) {
                DEviceNAME = list.get(0).getDeviceName();
            }
        } else {
            Toast.makeText(AppConfigureForm.this, "Please Enter Device Name", Toast.LENGTH_SHORT).show();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (devicenm.getText().toString().isEmpty()) {
                    devicenm.setError("Please Enter String");
                } else {
                    localDB.addContact(new DatamodelLocal(devicenm.getText().toString()));
                    devicenm.setText("");

                }
            }
        });

        devicenameshow.setText(DEviceNAME);
        if (DEviceNAME.toString() == null || DEviceNAME.toString() == "") {
            update.setVisibility(View.GONE);
            save.setVisibility(View.VISIBLE);
        } else {
            update.setVisibility(View.VISIBLE);
            save.setVisibility(View.GONE);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (devicenm.getText().toString().isEmpty()) {
                    devicenm.setError("Enter name...");
                } else {
                    localDB.updateContact(new DatamodelLocal(devicenm.getText().toString()), "1");
                    devicenm.setText("");
                }
            }
        });
    }

}