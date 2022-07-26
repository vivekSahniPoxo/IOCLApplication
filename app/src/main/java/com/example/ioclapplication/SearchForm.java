package com.example.ioclapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchForm extends AppCompatActivity {

    Button add_accession_btn, Search_btn, retry_btn, stop_btn, New_, SubmitDetails;
    EditText accession_no;
    String buttonText;
    Handler handler;
    IUHFService iuhfService;
    RecyclerView recyclerView;
    List<Data_Model_Search> list_data_Recyclerview = new ArrayList<>();
    Adapter_list adapter_list;
    ProgressDialog dialog;
    CoordinatorLayout coordinate;
    String result;
    private LooperDemo looperDemo;
    List<String> rfidlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_form);

        //Binding Components
        coordinate = findViewById(R.id.coordinator);
        recyclerView = findViewById(R.id.recyclerView_Accession);
        Search_btn = findViewById(R.id.Reading_btn);
        stop_btn = findViewById(R.id.Stop_Search);
        retry_btn = findViewById(R.id.Retry);
        add_accession_btn = findViewById(R.id.Add_accession);
        accession_no = findViewById(R.id.Accession_no);
        New_ = findViewById(R.id.New_accession);
        SubmitDetails = findViewById(R.id.buttonrfidDetails);

        SubmitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rfidlist.size() > 0) {
                    try {
                        submit_Report();
                        dialog.setMessage("Submitting");
                        dialog.setCancelable(true);
                        dialog.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SearchForm.this, "No Data for Submit... ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                adapter_list.getFilter(msg.obj);
                rfidlist.add(String.valueOf(msg.obj));

            }
        };
        looperDemo = new LooperDemo();
        //Initialize of Progress Dialog
        dialog = new ProgressDialog(this);
//        tempList = new ArrayList<>();
        //Method for Swipe to Delete
//        enableSwipeToDeleteAndUndo();

        //Initialize object
        //Open UHF Module
        iuhfService = UHFManager.getUHFService(this);
        iuhfService.setAntennaPower(30);

//        Toast.makeText(Search_Form.this, "Range "+iuhfService.getFreqRegion(), Toast.LENGTH_SHORT).show();
        //Listeners
        retry_btn.setOnClickListener(v -> {
            if (list_data_Recyclerview.size() > 0) {
                adapter_list.RetrySearch();
            } else {
                dialog.setMessage("List Already Clear");
                dialog.setCancelable(true);
                dialog.show();
            }
        });

        Search_btn.setOnClickListener(v -> {

//            Scan(buttonText, handler);
            if (list_data_Recyclerview.size() > 0) {
                Button b = (Button) v;
                buttonText = b.getText().toString();
                Scan(buttonText, handler);
            } else {
                Toast.makeText(SearchForm.this, "No Data Found For Scan", Toast.LENGTH_SHORT).show();
            }
        });

        add_accession_btn.setOnClickListener(v -> {
            if (accession_no.length() > 0) {
                if (list_data_Recyclerview.size() > 0) {
                    adapter_list.notifyDataSetChanged();
                    adapter_list.RetrySearch();
                } else {
//                    dialog.setMessage("List Already Clear");
//                    dialog.setCancelable(true);
//                    dialog.show();
                }
                try {

                    FetchData();
                    dialog.show();
                    dialog.setMessage(getString(R.string.Dialog_Text));
                    dialog.setCancelable(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                accession_no.setError("Enter Input...");
            }
            accession_no.setText("");
        });
        New_.setOnClickListener(v -> Clear());

    }

    //Method for Search Data From Server using Accession Number
    private void FetchData() throws JSONException {
//        String url = "http://164.52.223.163:4510/api/GetAssetInfoBySearch?AssetId=" + accession_no.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, ApiUrl.SearchApi+accession_no.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    if (array.length() == 0) {
                        dialog.dismiss();
                        Toast.makeText(SearchForm.this, "No Data Available of this Access Id...", Toast.LENGTH_SHORT).show();
                    } else {

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
//                            String id = object.optString("id");
//                            String pO_unique_id = object.optString("pO_UNIQUE_ID");
//                            String saP_ASSET_CODE = object.optString("saP_ASSET_CODE");
//                            String seriaL_NO = object.optString("seriaL_NO");
//                            String status = object.optString("status");
//                            String planT_CODE = object.optString("planT_CODE");
//                            String employee = object.optString("employee");
//                            String department = object.optString("department");
//                            String rfidtagid = object.optString("rfidtagid");
//                            String warrantY_STATUS = object.optString("warrantY_STATUS");
//                            String tracK_ID = object.optString("tracK_ID");
//                            String useR_ACK = object.optString("useR_ACK");
//                            String asseT_ID = object.optString("asseT_ID");
//                            String nonpO_BILLNO = object.optString("alloteD_TO_PLANT");
//                            String remarks = object.optString("remarks");
//                            String amC_ASSET_CATEGORY = object.optString("amC_ASSET_CATEGORY");
//                            String amC_AMOUNT = object.optString("amC_AMOUNT");
//                            String amC_AMOUNT_TOTAL = object.optString("amC_AMOUNT_TOTAL");
//                            String pmdonelq = object.optString("pmdonelq");
//                            String fmsperday = object.optString("fmsperday");
//                            String uniquE_ID = object.optString("uniquE_ID");
//                            String pO_NUMBER = object.optString("pO_NUMBER");
//                            String pO_DATE = object.optString("pO_DATE");
//                            String pO_PDF_FILE = object.optString("pO_PDF_FILE");
//                            String asset = object.optString("asset");
//                            String quantity = object.optString("quantity");
//                            String contracT_NUMBER = object.optString("contracT_NUMBER");
//                            String completioN_DATE = object.optString("completioN_DATE");
//                            String vendor = object.optString("vendor");
//                            String warrantY_PERIOD = object.optString("warrantY_PERIOD");
//                            String warrantY_START_DATE = object.optString("warrantY_START_DATE");
//                            String warrantY_END_DATE = object.optString("warrantY_END_DATE");
//                            String totaL_VALUE = object.optString("totaL_VALUE");
//                            String oem = object.optString("oem");
//                            String model = object.optString("model");
//                            String ram = object.optString("ram");
//                            String hdd = object.optString("hdd");
//                            String os = object.optString("os");
//                            String printeR_TYPE = object.optString("printeR_TYPE");
//                            String nO_OF_PORTS = object.optString("nO_OF_PORTS");
//                            String alloteD_TO_LOCATION = object.optString("alloteD_TO_LOCATION");
//                            String alloteD_TO_USER = object.optString("alloteD_TO_USER");
//                            String misC_BILL_NO = object.optString("misC_BILL_NO");
//                            String location = object.optString("location");
//                            String asseT_STATUS = object.optString("asseT_STATUS");

                            String employee = object.optString("employee");
                            String taG_ID = object.optString("taG_ID");
                            String pO_NUMBER = object.optString("pO_NUMBER");
                            String asset = object.optString("asset");
                            String employeename = object.optString("employeename");
                            String component = object.optString("component");
                            String seriaL_NO = object.optString("seriaL_NO");
                            String oem = object.optString("oem");
                            String model = object.optString("model");
                            dialog.dismiss();

                            list_data_Recyclerview.add(new Data_Model_Search(employee,taG_ID,pO_NUMBER,asset,employeename,component,seriaL_NO,oem,model));
                            adapter_list = new Adapter_list(list_data_Recyclerview, getApplicationContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(adapter_list);
                            if (list_data_Recyclerview.size() > 0) {
                                Search_btn.setEnabled(true);
                                dialog.dismiss();
                            }
                        }

                    }

                    Log.e("response", response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SearchForm.this, "Server Error", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(SearchForm.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsObjRequest);

//        queue.add(jsObjRequest);

    }

//    private void enableSwipeToDeleteAndUndo() {
//
//        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//
//
//                final int position = viewHolder.getAdapterPosition();
//                final Data_Model_Search item = adapter_list.getData().get(position);
//                adapter_list.removeItem(position);
//
//
//                Snackbar snackbar = Snackbar
//                        .make(coordinate, "Item was removed from the list.", Snackbar.LENGTH_LONG);
//                snackbar.setAction("UNDO", view -> {
//
//                    adapter_list.restoreItem(item, position);
//                    recyclerView.scrollToPosition(position);
//                });
////
//                snackbar.setActionTextColor(Color.YELLOW);
//                snackbar.show();
//
//            }
//        };
//
//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
//        itemTouchhelper.attachToRecyclerView(recyclerView);
//    }

    //Method for Clear Data from Components
    public void Clear() {
        list_data_Recyclerview.clear();
        adapter_list = new Adapter_list(list_data_Recyclerview, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter_list);
//        search_data.setText("");
        accession_no.setText("");
    }

    private void Scan(String buttonText, Handler handler) {
        if (buttonText.matches("Start")) {
            iuhfService.openDev();
            iuhfService.selectCard(1, "", false);
            iuhfService.inventoryStart();
            Search_btn.setText("STOP");
            add_accession_btn.setEnabled(false);
            iuhfService.setOnInventoryListener(var1 -> {
//
//                System.out.println("Start Reading" + tempList);
                //if (Search_btn.getText()=="Start")

                result = var1.getEpc();
                looperDemo.execute(() -> {
                    Message message = Message.obtain();
                    message.obj = result;
                    handler.sendMessage(message);
                });
            });

        } else {
            add_accession_btn.setEnabled(true);
            iuhfService.inventoryStop();
            iuhfService.closeDev();
            Search_btn.setText("Start");
        }

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_F1://KeyEvent { action=ACTION_UP, keyCode=KEYCODE_F1, scanCode=59, metaState=0, flags=0x8, repeatCount=0, eventTime=13517236, downTime=13516959, deviceId=1, source=0x101 }
                buttonText = Search_btn.getText().toString();
                if (list_data_Recyclerview.size() > 0) {
                    Scan(buttonText, handler);
                } else {
                    Toast.makeText(SearchForm.this, "No Data Found For Scan", Toast.LENGTH_SHORT).show();
                }

                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    private void submit_Report() throws JSONException {

        //Getting Shared Data
//        String rfid = sh.getString("RFID NO", null);
//        String status = sh.getString("Status", null);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
//        String dt_str = "2000-01-01T13:00:00.000Z";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


        String url = "http://164.52.223.163:4510/api/WriteRfidTagDetails";
        JSONObject object = new JSONObject();
        //JSONArray array = new JSONArray();
        JSONArray array = new JSONArray();
        for (int i = 0; i < rfidlist.size(); i++) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("tagId", rfidlist.get(i));

            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    jsonObject.put("location", "0100");
                    jsonObject.put("hhrId", "SD60RT");
                    jsonObject.put("readTime", now);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            array.put(jsonObject);
        }

        object.put("rfidRead", array);
//
        RequestQueue queue = Volley.newRequestQueue(this);
        System.out.println("JSON DATA " + object);

        final String requestBody = object.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            Toast.makeText(SearchForm.this, "Submitted " + response + " ...", Toast.LENGTH_SHORT).show();
            Clear();
            rfidlist.clear();
            Log.i("VOLLEY Submit", response);
            dialog.dismiss();
        }, error -> {
            rfidlist.clear();
            Toast.makeText(SearchForm.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("VOLLEY Negative", error.toString() + "Submitted Response : " + error.networkResponse.data + "UTF-8");
            System.out.println("Error Submitting" + error.getCause());
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

        queue.add(stringRequest);
    }
}