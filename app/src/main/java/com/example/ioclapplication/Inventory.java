package com.example.ioclapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.speedata.libuhf.bean.SpdInventoryData;
import com.speedata.libuhf.interfaces.OnSpdInventoryListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Inventory extends AppCompatActivity {

    long lastTimeMillis;
    private int soundId;
    private SoundPool soundPool;
    RecyclerView recyclerView;
    List<InventoryDataModel> list;
    InventoryAdapter adapter;
    List<String> TempList_Inventory;
    TextView total, found, not_found;
    int counter = 0, len, not_founded;
    List<ReportModel> listreport;
    int page = 1, limit = 0;
    //    CoordinatorLayout coordinatorLayout;
    List<DatamodelLocal> listdb;
    String keyid;
    ProgressDialog dialog;
    IUHFService iuhfService;
    String buttonText;
    File filepath;
    LocalDB localDB;
    boolean isInventoryRunning = false;
    Handler handler;
    Button Search, Submit, NewBtn, Back_Btn;

    private LooperDemo looperDemo;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        recyclerView = findViewById(R.id.recyclerView);
        total = findViewById(R.id.Total);
        found = findViewById(R.id.Found);
        not_found = findViewById(R.id.not_found);
        NewBtn = findViewById(R.id.New_Button);
        Search = findViewById(R.id.Search_);
//        coordinatorLayout = findViewById(R.id.coordinator);
        Back_Btn = findViewById(R.id.Back_Button);
        Submit = findViewById(R.id.Submit_Button);
        TempList_Inventory = new ArrayList<>();
        list = new ArrayList<>();
        listdb = new ArrayList<>();
        localDB = new LocalDB(this);
        listreport = new ArrayList<>();

//        fastScroller = findViewById(R.id.fasttrcv);
//        fastScroller.attachRecyclerView(recyclerView);
        //Method for Left Swipe to Delete
//        enableSwipeToDeleteAndUndo();
        dialog = new ProgressDialog(this);

        Back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (filepath.exists()) {
//                    UpdateExcel();
//                } else {
//                    createExcelSheet();
//                }
//                startActivity(new Intent(AuditDetrails.this, AuditForm.class));
//                finish();
//                createExcelSheet();
            }
        });
        NewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clear();
            }
        });
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
//                adapter_list.getFilter(msg.obj);
                counter = adapter.getFilter(msg.obj);
//                counter = counter+fnd;
//                iuhfService.inventoryStop();
//                iuhfService.closeDev();
                not_founded = len - counter;
                total.setText(String.valueOf(len));
                found.setText(String.valueOf(counter));
                not_found.setText(String.valueOf(not_founded));


            }
        };
        looperDemo = new LooperDemo();
        try {
            //iuhfService = UHFManager.getUHFService(this);
//        iuhfService.setAntennaPower(30);
           // iuhfService.setFrequency(865);
        } catch (Exception e){
            Log.d("Exception",e.toString());
        }

        Search.setOnClickListener(v -> {
            Button b = (Button) v;
            if (list.size() > 0) {

                new Thread(new Runnable() {
                    public void run() {
                        buttonText = b.getText().toString();
                        Scan(buttonText, handler);
                    }
                }).start();

                Submit.setEnabled(true);
            } else {
                Toast.makeText(Inventory.this, "No Data Found For Scan", Toast.LENGTH_SHORT).show();
            }

//            String result = iuhfService.read_area(1, "2", "6", "00000000");

        });

//        keyid = getIntent().getStringExtra("keyId");
//        limit = Integer.parseInt(getIntent().getStringExtra("length"));
        FetchData();
        dialog.setMessage("Fetching Details...");
        dialog.setCancelable(false);
        dialog.show();

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
//                int totalItemCount = layoutManager.getItemCount();
//                int lastVisible = layoutManager.findLastVisibleItemPosition();
//
//                boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
//                if (totalItemCount > 0 && endHasBeenReached) {
//                    //you have reached to the bottom of your recycler view
//                    page++;
//                    FetchData(keyid, page, limit);
//                    dialog.show();
//
//                }
//            }
//        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                new androidx.appcompat.app.AlertDialog.Builder(getApplication())
//                        .setIcon(R.drawable.exitapp)
//                        .setTitle("Generate Excel")
//                        .setMessage("Do ypu want to generate excel file ?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                        {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                              callmethod() ;
//                              createExcelSheet();
//                            }
//
//
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                callmethod();
//                            }
//                        })
//                        .show();

                try {
                    if (list.size() > 0) {
                        String textvla = (String) Search.getText();
                        if (textvla.matches("Start")) {
                            submit_Report();
                            dialog.show();
                            dialog.setMessage(getString(R.string.Dialog_Text));
                            dialog.setCancelable(false);
                        } else {
                            Toast.makeText(Inventory.this, "Please Stop Scanning ....", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(Inventory.this, "NO Data in list", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void FetchData() {

//        String url = "http://164.52.223.163:4504/api/GetauditDetails?AuditId=" + epcvalue;

//        String url = "http://164.52.223.163:4510/api/GetMapedItems";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, ApiUrl.GetMappedITem, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    JSONArray array = new JSONArray(response);
//

//                        JSONObject object1 = new JSONObject(response);
                    JSONArray array = new JSONArray(response);
                    len = len + array.length();
                    total.setText(String.valueOf(len));
                    not_found.setText("0");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
//
                        String employee = object.optString("employee");
                        String taG_ID = object.optString("taG_ID");
                        String asseT_ID = object.optString("asseT_ID");
                        String pO_NUMBER = object.optString("pO_NUMBER");
                        String asset = object.optString("asset");
                        String employeename = object.optString("employeename");
                        String component = object.optString("component");
                        String oem = object.optString("oem");
                        String location = object.optString("location");
                        String model = object.optString("model");
                        String SErialNo = object.optString("seriaL_NO");

//                        localDB.addContact(new SearchDataModel(auditid, rfidNo, status, assetId, assetName, assetTag, serialNo, model, category, assetStatus, company, manufacturer, location, purchaseCost, supplier, orderNo, purchaseDate, notes));
                        Back_Btn.setEnabled(true);
//                        Back_Btn.setBackgroundColor(Integer.parseInt("#FF00AA"));
                        TempList_Inventory.add(taG_ID);

//                        setuplist(TempList_Inventory);
                        list.add(new InventoryDataModel(employee, taG_ID, asseT_ID, pO_NUMBER, asset, employeename, component, SErialNo, location, model));
                        adapter = new InventoryAdapter(TempList_Inventory, list, getApplicationContext());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapter);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                    Toast.makeText(Inventory.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Inventory.this, "" + error, Toast.LENGTH_SHORT).show();
                Log.d("Error", String.valueOf(error));
                dialog.dismiss();
            }
        });

        // below line is to make
        // a json object request.
        queue.add(request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));

    }

//    private void setuplist(List<String> tempList_inventory) {
//        list = localDB.getAllContacts();
//        adapter = new DetailsAdapter(tempList_inventory, list, getApplicationContext());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setAdapter(adapter);
//        dialog.dismiss();
//    }

    private void Scan(String buttonText, Handler handler) {
        new Thread(new Runnable() {
            public void run() {
                if (buttonText.matches("Start")) {
                    iuhfService.openDev();
                    iuhfService.selectCard(1, "", false);
                    iuhfService.inventoryStart();
                    Search.setText("STOP");

                    iuhfService.setOnInventoryListener(new OnSpdInventoryListener() {
                        @Override
                        public void getInventoryData(SpdInventoryData var1) {
                            result = var1.getEpc();
                        looperDemo.execute(() -> {
                            Message message = Message.obtain();
                            message.obj = result;
                            handler.sendMessage(message);
                        });

                        try {
                                long timeMillis = System.currentTimeMillis();
                                long l = timeMillis - lastTimeMillis;
                                if (l < 100) {
                                    return;
                                }
                                lastTimeMillis = System.currentTimeMillis();
                                soundPool.play(soundId, 1, 1, 0, 0, 1);


                            } catch (Exception e){
                                Log.d("exception",e.toString());
                            }
                        }

                        @Override
                        public void onInventoryStatus(int i) {

                        }
                    });


//                    iuhfService.setOnInventoryListener(var1 -> {
//
//                        result = var1.getEpc();
//                        looperDemo.execute(() -> {
//                            Message message = Message.obtain();
//                            message.obj = result;
//                            handler.sendMessage(message);
//                        });
//
//                    });

                } else {
                    iuhfService.inventoryStop();
                    iuhfService.closeDev();
//                adapter_list.RetrySearch();
                    Search.setText("Start");
                }
            }
        }).start();


    }


    private void submit_Report() throws JSONException {
        listdb = localDB.getAllContacts();
        String devicename = "";
        try {
            devicename = listdb.get(0).getDeviceName();
        } catch (Exception e) {
            if (devicename.length() == 0) {
                devicename = "SD60RT";
            }
            e.printStackTrace();
        }


        JSONObject object = new JSONObject();

        JSONArray array = new JSONArray();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println("Today DATA " + dtf.format(now));
            Date currentTime = Calendar.getInstance().getTime();
//            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            long currentTimeInSecond = System.currentTimeMillis();
            listreport.clear();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getStatusF() == "True") {
                    listreport.add(new ReportModel(list.get(i).getTaG_ID(), "0100", devicename, String.valueOf(now),list.get(i).asseT_ID,list.get(i).asset));
                }
            }

        }


        for (int i = 0; i < listreport.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tagId", listreport.get(i).getTagId());
            jsonObject.put("location", listreport.get(i).getLocation());
            jsonObject.put("hhrId", listreport.get(i).getHhrId());
            jsonObject.put("readTime", listreport.get(i).getReadTime());
            jsonObject.put("asset_Id",listreport.get(i).getAssetID());
            jsonObject.put("assetName",listreport.get(i).getAssetName());
            array.put(jsonObject);
        }


        object.put("rfidRead", array);
//        String url = "http://164.52.223.163:4510/api/WriteRfidTagDetails";
        RequestQueue queue = Volley.newRequestQueue(this);
        System.out.println("JSON DATA " + object);

        final String requestBody = object.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.WriteRfidDetails, response -> {
            Toast.makeText(Inventory.this, response, Toast.LENGTH_SHORT).show();
            Clear();
            Log.i("VOLLEY Submit", response);
            startActivity(new Intent(Inventory.this,MainActivity.class));
            finish();
            dialog.dismiss();
        }, error -> {
            try {
                Clear();
                Log.e("VOLLEY Negative", String.valueOf(error.networkResponse.statusCode));
                Log.e("VOLLEY Negative", String.valueOf(error.getMessage()));
                if (error.networkResponse.statusCode == 404) {
                    Toast.makeText(Inventory.this, "No Result Found", Toast.LENGTH_SHORT).show();
                } else if (error.networkResponse.statusCode == 400) {
                    Toast.makeText(Inventory.this, "Bad Request", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Inventory.this, "Unable to process the request", Toast.LENGTH_SHORT).show();

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

    //Method for Clear Data from components
    public void Clear() {
        if (list.size() > 0) {
//            Submit.setEnabled(true);
            NewBtn.setEnabled(true);
            list.clear();
            adapter = new InventoryAdapter(TempList_Inventory, list, getApplicationContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(Inventory.this, "List is Empty", Toast.LENGTH_SHORT).show();
        }


//        adapter.notifyDataSetChanged();
//        Accession.setText("");
        total.setText("0");
        not_found.setText("0");
        found.setText("0");


    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_F1://KeyEvent { action=ACTION_UP, keyCode=KEYCODE_F1, scanCode=59, metaState=0, flags=0x8, repeatCount=0, eventTime=13517236, downTime=13516959, deviceId=1, source=0x101 }
//                Button b = (Button) v;
                if (list.size() > 0) {

                    new Thread(new Runnable() {
                        public void run() {
                            buttonText = Search.getText().toString();
                            Scan(buttonText, handler);

                        }
                    }).start();
                    Submit.setEnabled(true);
                } else {
                    Toast.makeText(Inventory.this, "No Data Found For Scan", Toast.LENGTH_SHORT).show();
                }


                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Inventory.this, MainActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopInventoryService();
    }

    private void stopInventoryService() {
        iuhfService.closeDev();

    }


    @Override
    protected void onStart() {
        super.onStart();
        initializeUHF();
    }

    @SuppressLint("ResourceAsColor")
    private void initializeUHF() {
        try {
            iuhfService = UHFManager.getUHFService(this);
            iuhfService.openDev();
            iuhfService.setAntennaPower(30);

            // iuhfService.setFrequency(2);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}