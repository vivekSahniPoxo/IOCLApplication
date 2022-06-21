package com.example.ioclapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.speedata.libuhf.IUHFService;
import com.speedata.libuhf.UHFManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Mapping extends AppCompatActivity {
    IUHFService iuhfService;
    String epc, result;
    CardView ReadingCard;
    Spinner assetId;
    Button Search, ViewDetals, Mapped;
    String AssetKey, location, asseT_ID1,pO_DATE1;
ProgressDialog dialog;
    List<String> listId;
    TextView ASSET_CODE, seriaL_NO, planT_CODE, pO_NUMBER, pO_DATE, department, asseT_ID, alloteD_TO_PLANT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);
        ReadingCard = findViewById(R.id.button_Scan);
        assetId = findViewById(R.id.spinner2value);
        Search = findViewById(R.id.Stop_Search);
        ViewDetals = findViewById(R.id.ViewAll);
        Mapped = findViewById(R.id.submitbn);
        listId = new ArrayList<>();
        asseT_ID = findViewById(R.id.Booktitle);
        seriaL_NO = findViewById(R.id.SolarCell);
        planT_CODE = findViewById(R.id.MonthPV);
        pO_NUMBER = findViewById(R.id.MonthSolar);
        pO_DATE = findViewById(R.id.IECcertificate);
        department = findViewById(R.id.textView4);
        ASSET_CODE = findViewById(R.id.textView6);
        alloteD_TO_PLANT = findViewById(R.id.textView8);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Fetch Id....");
        dialog.setCancelable(false);
        dialog.show();


        Mapped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (result==null)
                    {
                        Toast.makeText(Mapping.this, "Please Read RFID tag...", Toast.LENGTH_SHORT).show();
                    }else {
                        try {
                            dialog.setMessage("Mapping Tag....");
                            dialog.setCancelable(false);
                            dialog.show();
                            MappingTags();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
            }
        });


        FetchAssetId();
        ReadingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scan();
            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (AssetKey.length() > 0) {
                        FetchData(AssetKey);
                        dialog.setMessage("Fetch data....");
                        dialog.setCancelable(false);
                        dialog.show();
                    } else {
                        Toast.makeText(Mapping.this, "Please Select Asset ID", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        iuhfService = UHFManager.getUHFService(this);
        iuhfService.setOnInventoryListener(var1 -> {
            epc = var1.getEpc();
        });

    }

    private void Scan() {
        iuhfService.openDev();
        result = iuhfService.read_area(1, "2", "6", "00000000");
        Toast.makeText(Mapping.this, "" + result, Toast.LENGTH_SHORT).show();
    }


    private void FetchData(String epcvalue) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://164.52.223.163:4510/api/GetAssetInfo?Assetid=" + epcvalue;
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject object = new JSONObject(response);
                    String saP_ASSET_CODE1 = object.optString("saP_ASSET_CODE");
                    String seriaL_NO1 = object.optString("seriaL_NO");
                    String planT_CODE1 = object.optString("planT_CODE");
                    String department1 = object.optString("department");
                    asseT_ID1 = object.optString("asseT_ID");
                    String alloteD_TO_PLANT1 = object.optString("alloteD_TO_PLANT");
                    String pO_NUMBER1 = object.optString("pO_NUMBER");
                     pO_DATE1 = object.optString("pO_DATE");
                    location = object.optString("alloteD_TO_LOCATION");
//
                    String[] separated = pO_DATE1.split("T");
                    // this will contain "Fruit"

                    String date1 = separated[0];

                    asseT_ID.setText(asseT_ID1);
                    seriaL_NO.setText(seriaL_NO1);
                    planT_CODE.setText(planT_CODE1);
                    pO_NUMBER.setText(pO_NUMBER1);
                    pO_DATE.setText(date1);
                    department.setText(department1);
                    ASSET_CODE.setText(saP_ASSET_CODE1);
                    alloteD_TO_PLANT.setText(alloteD_TO_PLANT1);
//                    Publisher.setText(LibraryItemType1);
//                    RFIDNo.setText(RFIDNo1);
//                    AccessNo.setText(Publisher1);
//                    Author.setText(ItemStatus1);
//                    Title.setText(Author1);
//                    YearOfPublication.setText(YearOfPublication1);
//                    EntryDate.setText(EntryDate1);
                    dialog.dismiss();


                    Log.e("response", response.toString());
                } catch (Exception e) {
                    Toast.makeText(Mapping.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    dialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mapping.this, "No Data Found...", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(sr);
    }


    private void FetchAssetId() {
        String url = "http://164.52.223.163:4510/api/GetAssetId";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String id = object.getString("asseT_ID");
                        listId.add(id);
                    }

                    SetupIDSpinner(listId);
dialog.dismiss();
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                dialog.dismiss();
                }
//                    final List<String> List = new ArrayList<>(Arrays.asList(value));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mapping.this, error.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        });

        queue.add(request);

    }

    private void SetupIDSpinner(List<String> listId) {
        //Country name Spinner

        final ArrayAdapter<String> SpinnerCountrty = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listId);
        SpinnerCountrty.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        assetId.setAdapter(SpinnerCountrty);
        assetId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AssetKey = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void MappingTags() throws JSONException {
        String url = "http://164.52.223.163:4510/api/MapRfidTag";
        JSONObject obj = new JSONObject();
        obj.put("id", "0");
        obj.put("assetId", AssetKey);
        obj.put("tagId", result);
        obj.put("location", location);
        obj.put("updatetime", "2022-06-13T06:45:17.169");

        RequestQueue queue = Volley.newRequestQueue(this);


        final String requestBody = obj.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {

            Toast.makeText(Mapping.this, ""+response, Toast.LENGTH_SHORT).show();
            Log.i("VOLLEY", response);
            dialog.dismiss();
        }, error -> {
            Log.e("VOLLEY Negative", error.toString());
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
        };

        queue.add(stringRequest);
    }

//
}