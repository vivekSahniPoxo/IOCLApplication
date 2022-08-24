package com.example.ioclapplication;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Mapping extends AppCompatActivity {
    IUHFService iuhfService;
    String epc, result;
    CardView ReadingCard;
    Spinner assetId;
    Button Search, ViewDetals, Mapped;
    String AssetKey, location, asseT_ID1, pO_DATE1;
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

                if (result == null) {

                    Toast.makeText(Mapping.this, "Please Read RFID tag...", Toast.LENGTH_SHORT).show();
                }
                else if(result.endsWith("FF04EE")){
                    Toast.makeText(Mapping.this, "Keep the Tag Closer to Reader...", Toast.LENGTH_SHORT).show();

                }
                else {
                    try {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            MappingTags();
                            dialog.setMessage("Mapping Tag....");
                            dialog.setCancelable(false);
                            dialog.show();
                        }
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
        iuhfService.setAntennaPower(5);
        iuhfService.setOnInventoryListener(var1 -> {
            epc = var1.getEpc();
        });

    }

    private void Scan() {
        String Datavalue="";
        iuhfService.openDev();
        Datavalue = iuhfService.read_area(1, "2", "6", "00000000");
       if (Datavalue.endsWith("FF04EE"))
       {
           Toast.makeText(Mapping.this, "Keep the Tag Closer to Reader...", Toast.LENGTH_SHORT).show();

       }else {
           result=Datavalue;
           Toast.makeText(Mapping.this, "" + result, Toast.LENGTH_SHORT).show();
       }

    }


        private void FetchData(String epcvalue) throws JSONException {
            RequestQueue queue = Volley.newRequestQueue(this);
    //        String url = "http://164.52.223.163:4510/api/GetAssetInfo?Assetid=" + epcvalue;
    //        String url = "http://mudvprfidiis:82/api/GetAssetInfo?Assetid=" + epcvalue;
            StringRequest sr = new StringRequest(Request.Method.GET, ApiUrl.AssetIDDAta+epcvalue, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject object = new JSONObject(response);
                        String employee = object.optString("employee");
                        String pO_NUMBER1 = object.optString("pO_NUMBER");
                        String asset = object.optString("asset");
                        String employeename = object.optString("employeename");
                        String component = object.optString("component");
                        String seriaL_NO1 = object.optString("seriaL_NO");
                        String oem = object.optString("oem");
                        String model = object.optString("model");

    //
                        asseT_ID.setText(employeename);
                        seriaL_NO.setText(employee);
                        planT_CODE.setText(asset);
                        pO_NUMBER.setText(seriaL_NO1);
                        pO_DATE.setText(oem);
                        department.setText(model);
                        ASSET_CODE.setText(component);
                        alloteD_TO_PLANT.setText(pO_NUMBER1);
    ////                    Publisher.setText(LibraryItemType1);
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
//        String url = "http://164.52.223.163:4510/api/GetAssetId";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, ApiUrl.GetAssetID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    listId.add("Select ID");
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void MappingTags() throws JSONException {
//        String url = "http://164.52.223.163:4510/api/MapRfidTag";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            JSONObject obj = new JSONObject();
            obj.put("id", "0");
            obj.put("assetId", AssetKey);
            obj.put("tagId", result);
            obj.put("location", "0100");
            obj.put("updatetime", now);

        RequestQueue queue = Volley.newRequestQueue(this);


        final String requestBody = obj.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.MapRfidID, response -> {

            try {
                JSONObject object = new JSONObject(response);
                String message =object.optString("message");
                String result=object.optString("status");
                if (!result.matches("false")){
                    listId.clear();
                    FetchAssetId();
                    asseT_ID.setText("");
                    seriaL_NO.setText("");
                    planT_CODE.setText("");
                    pO_NUMBER.setText("");
                    pO_DATE.setText("");
                    department.setText("");
                    ASSET_CODE.setText("");
                    alloteD_TO_PLANT.setText("");

                    Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            Toast.makeText(Mapping.this, "" + response, Toast.LENGTH_SHORT).show();
            Log.i("VOLLEY", response);
            dialog.dismiss();
//            listId.clear();
//            FetchAssetId();
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
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_F1://KeyEvent { action=ACTION_UP, keyCode=KEYCODE_F1, scanCode=59, metaState=0, flags=0x8, repeatCount=0, eventTime=13517236, downTime=13516959, deviceId=1, source=0x101 }
                Scan();
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        iuhfService=UHFManager.getUHFService(Mapping.this);
    }
//
}