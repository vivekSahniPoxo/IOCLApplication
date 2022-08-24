package com.example.ioclapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class UpdateLocation extends AppCompatActivity {
    Spinner dailogspinner,spinnerwing;
    EditText dailogEdittext;
    RadioGroup radioGroup;
    Button dailogupdate, dailogcancel;
    String Dailspinnerid, dailogseatno, dailogSeatSelect;
    List<String> datavalue = new ArrayList<>();
    String value = null;
    String user=null;
    String cabin1,Cubical1,wingval;
    ProgressDialog dialog;
     List<String> listwing = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location);

        dialog = new ProgressDialog(this);
        spinnerwing=findViewById(R.id.spinnerwing);
        radioGroup = findViewById(R.id.RadioButtonGroup);
        dailogspinner = findViewById(R.id.spinner2_location);
        dailogEdittext = findViewById(R.id.editTextTextPersonName);
        dailogupdate = findViewById(R.id.btnupdate);
        dailogcancel = findViewById(R.id.btncancel);
        Intent i = getIntent();
        user=i.getStringExtra("UserID");

        dailogcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateLocation.this,Identify.class));
            }
        });

        dailogupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dailogEdittext.getText().length()>0)
                {
                    try {
                        String seat = dailogEdittext.getText().toString();
                        submit_Report(seat);
                        dialog.setCancelable(false);
                        dialog.setMessage("Loading...");
                        dialog.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    dailogEdittext.setError("Enter Floor no");
                }
            }
        });

        listwing.add("Small");
        listwing.add("Large");
        ArrayAdapter<String> location = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listwing);
        // Drop down layout style - list view with radio button
        location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerwing.setAdapter(location);

        spinnerwing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                wingval=adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(UpdateLocation.this, ""+wingval, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });













        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
        dialog.show();
        GetLocationID();
        dailogspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Dailspinnerid = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        radioGroup.clearCheck();
//        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
//                RadioButton MaterialId = (RadioButton) group.findViewById(R.id.MaterialID);
//                RadioButton MaterialCode = (RadioButton) group.findViewById(R.id.MaterialCode);
//
//                if (checkedId == R.id.MaterialCode) {
//                    dailogSeatSelect = String.valueOf(MaterialCode.getText());
//                    Toast.makeText(UpdateLocation.this, MaterialCode.getText(), Toast.LENGTH_SHORT).show();
//                } else if (checkedId == R.id.MaterialID) {
//                    dailogSeatSelect = String.valueOf(MaterialId.getText());
//                    Toast.makeText(UpdateLocation.this, MaterialId.getText(), Toast.LENGTH_SHORT).show();
//                }
//            });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                value =
                        ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId()))
                                .getText().toString();
                Toast.makeText(getBaseContext(), value, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void GetLocationID() {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, ApiUrl.LocationID, response -> {
            datavalue.clear();

            try {

                dialog.dismiss();
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String val = object.optString("maxfloorno");
                    for(int j=0;j<=Integer.parseInt(val);j++)
                    {
                        datavalue.add(String.valueOf(j));
                    }


                    Log.i("DAtTA", val);
//System.out.print("DAtTA",val);
                }
                SetUPSpinner(datavalue);

            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
            }


//                System.out.println("Array Value " + data_value);
//
        }, error -> {
            Toast.makeText(UpdateLocation.this, error.getMessage(), Toast.LENGTH_LONG).show();
            dialog.dismiss();

        });

        queue.add(request);

    }

    private void SetUPSpinner(List<String> datavalue) {

        ArrayAdapter<String> location = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datavalue);
        // Drop down layout style - list view with radio button
        location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        dailogspinner.setAdapter(location);
    }

    private void submit_Report(String seat) throws JSONException {

        dailogEdittext.setText("");
        if (value.matches("Cabin"))
        {
            cabin1="Yes";
            Cubical1="NO";

        }else {
            cabin1="No";
            Cubical1="Yes";
        }
        if(user==null)
        {
            user="0";
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("useR_ID",user );
        jsonObject.put("wing",wingval);
        jsonObject.put("floor",Dailspinnerid);
        jsonObject.put("cabin",cabin1 );
        jsonObject.put("cubical",Cubical1 );
        jsonObject.put("seaT_NO",seat);

        RequestQueue queue = Volley.newRequestQueue(this);
//

        final String requestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.UpdateLocation, response -> {
            Toast.makeText(UpdateLocation.this, response, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UpdateLocation.this,Identify.class));

            finish();
            Log.i("VOLLEY Submit", response);

            dialog.dismiss();
        }, error -> {
            try {
//                Clear();
                Log.e("VOLLEY Negative", String.valueOf(error.networkResponse.statusCode));
                Log.e("VOLLEY Negative", String.valueOf(error.getMessage()));
                if (error.networkResponse.statusCode == 404) {
                    Toast.makeText(UpdateLocation.this, "No Result Found", Toast.LENGTH_SHORT).show();
                } else if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(UpdateLocation.this, "Bad Request", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateLocation.this, "Unable to process the request", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                Log.e("VOLLEY Negative", String.valueOf(error.getMessage()));

                Toast.makeText(this, "Update...", Toast.LENGTH_SHORT).show();

            }
            dialog.dismiss();
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                System.out.println("Response Code " + response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }

}