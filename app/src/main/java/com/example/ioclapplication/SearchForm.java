package com.example.ioclapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.speedata.libuhf.IUHFService;
import com.speedata.libuhf.UHFManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchForm extends AppCompatActivity {

    Button add_accession_btn, Search_btn, retry_btn, stop_btn, New_;
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
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                adapter_list.getFilter(msg.obj);

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
        String url = "http://164.52.223.163:4510/api/GetAssetInfoBySearch?AssetId=" + accession_no.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    if (array.length() == 0) {
                        dialog.dismiss();
                        Toast.makeText(SearchForm.this, "No Data Available of this Accession...", Toast.LENGTH_SHORT).show();
                    } else {

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String id = object.getString("id");
                            String pO_unique_id = object.getString("pO_UNIQUE_ID");
                            String saP_ASSET_CODE = object.getString("saP_ASSET_CODE");
                            String seriaL_NO = object.getString("seriaL_NO");
                            String status = object.getString("status");
                            String planT_CODE = object.getString("planT_CODE");
                            String employee = object.getString("employee");
                            String department = object.getString("department");
                            String rfidtagid = object.getString("rfidtagid");
                            String warrantY_STATUS = object.getString("warrantY_STATUS");
                            String tracK_ID = object.getString("tracK_ID");
                            String useR_ACK = object.getString("useR_ACK");
                            String asseT_ID = object.getString("asseT_ID");
                            String nonpO_BILLNO = object.getString("alloteD_TO_PLANT");
                            String remarks = object.getString("remarks");
                            String amC_ASSET_CATEGORY = object.getString("amC_ASSET_CATEGORY");
                            String amC_AMOUNT = object.getString("amC_AMOUNT");
                            String amC_AMOUNT_TOTAL = object.getString("amC_AMOUNT_TOTAL");
                            String pmdonelq = object.getString("pmdonelq");
                            String fmsperday = object.getString("fmsperday");
                            String uniquE_ID = object.getString("uniquE_ID");
                            String pO_NUMBER = object.getString("pO_NUMBER");
                            String pO_DATE = object.getString("pO_DATE");
                            String pO_PDF_FILE = object.getString("pO_PDF_FILE");
                            String asset = object.getString("asset");
                            String quantity = object.getString("quantity");
                            String contracT_NUMBER = object.getString("contracT_NUMBER");
                            String completioN_DATE = object.getString("completioN_DATE");
                            String vendor = object.getString("vendor");
                            String warrantY_PERIOD = object.getString("warrantY_PERIOD");
                            String warrantY_START_DATE = object.getString("warrantY_START_DATE");
                            String warrantY_END_DATE = object.getString("warrantY_END_DATE");
                            String totaL_VALUE = object.getString("totaL_VALUE");
                            String oem = object.getString("oem");
                            String model = object.getString("model");
                            String ram = object.getString("ram");
                            String hdd = object.getString("hdd");
                            String os = object.getString("os");
                            String printeR_TYPE = object.getString("printeR_TYPE");
                            String nO_OF_PORTS = object.getString("nO_OF_PORTS");
                            String alloteD_TO_LOCATION = object.getString("alloteD_TO_LOCATION");
                            String alloteD_TO_USER = object.getString("alloteD_TO_USER");
                            String misC_BILL_NO = object.getString("misC_BILL_NO");
                            String location = object.getString("location");
                            String asseT_STATUS = object.getString("asseT_STATUS");


                            list_data_Recyclerview.add(new Data_Model_Search(id,pO_unique_id,saP_ASSET_CODE,seriaL_NO ,status ,planT_CODE ,employee,department,rfidtagid,rfidtagid,warrantY_STATUS,tracK_ID,useR_ACK,asseT_ID,nonpO_BILLNO ,remarks ,amC_ASSET_CATEGORY,amC_AMOUNT,amC_AMOUNT_TOTAL ,pmdonelq ,fmsperday,uniquE_ID,pO_NUMBER,pO_DATE,pO_PDF_FILE,asset,quantity,contracT_NUMBER,completioN_DATE,vendor,warrantY_PERIOD,warrantY_START_DATE,warrantY_END_DATE,totaL_VALUE,oem,model,ram,hdd,os,printeR_TYPE,nO_OF_PORTS,alloteD_TO_LOCATION,alloteD_TO_USER,misC_BILL_NO,location,asseT_STATUS));
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
}