package com.example.ioclapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

public class Identify extends AppCompatActivity {

    Button Search, NEW_data, Retry;
    //    EditText search_key;
    TextView LibraryItemType, BookAddedIn, BookCategory, ItemStatus, SubjectTitle, Language, Edition, Publisher, RFIDNo, AccessNo, Author, Title, YearOfPublication, EntryDate;
    ProgressDialog dialog;
    CardView scan_button;
    IUHFService iuhfService;
    public List<String> tempList;
    String epc;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);

        //Binding Components
        scan_button = findViewById(R.id.button_Scan);
//        search_key = findViewById(R.id.Search_key);
        Retry = findViewById(R.id.Retry);
        Search = findViewById(R.id.Search_rfid_button);
        LibraryItemType = findViewById(R.id.Library_item);
        NEW_data = findViewById(R.id.New_accession);
        BookAddedIn = findViewById(R.id.Book_Add);
        BookCategory = findViewById(R.id.BookCategory);
        ItemStatus = findViewById(R.id.Item_status);
        SubjectTitle = findViewById(R.id.Subject_t);
        Language = findViewById(R.id.Language);
        Edition = findViewById(R.id.Edition);
        Publisher = findViewById(R.id.Publisher);
        RFIDNo = findViewById(R.id.RFID_NO);
        AccessNo = findViewById(R.id.Access_No);
        Author = findViewById(R.id.Authorname);
        Title = findViewById(R.id.Booktitle);
        YearOfPublication = findViewById(R.id.YearOfPublication);
        EntryDate = findViewById(R.id.EntryDate);

        iuhfService = UHFManager.getUHFService(this);
        tempList = new ArrayList<>();
        iuhfService.setAntennaPower(10);
        iuhfService.setOnInventoryListener(var1 -> {
//                    tempList.add(var1.getEpc());
//                    System.out.println("List Data" + tempList);
            epc = var1.getEpc();
//            if (epc != null) {
////                try {
//////                    Toast.makeText(Identify_Form.this, epc, Toast.LENGTH_SHORT).show();
////                    //Toast.makeText(Identify_Form.this, "Start Fetching Data...", Toast.LENGTH_SHORT).show();
//////                    FetchData(epc);
////                    //Toast.makeText(Identify_Form.this, "Method Called", Toast.LENGTH_SHORT).show();
//////                    dialog.show();
//////                    dialog.setMessage(getString(R.string.Dialog_Text));
//////                    dialog.setCancelable(false);
////                    iuhfService.inventoryStop();
////                    //Toast.makeText(Identify_Form.this, "Inventory Stopped", Toast.LENGTH_SHORT).show();
////                    iuhfService.closeDev();
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
//            }
            //Toast.makeText(Identify_Form.this, epc, Toast.LENGTH_SHORT).show();
        });
        //Initialize of Dialog Box
        dialog = new ProgressDialog(this);

        //Listeners
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scan();
            }
        });

        Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    iuhfService.inventoryStop();
//                    iuhfService.closeDev();
//                    Toast.makeText(Identify_Form.this, "Start Fetching Data...", Toast.LENGTH_SHORT).show();
                    if (result != null) {
                        FetchData(result);
                        dialog.show();
                        dialog.setMessage(getString(R.string.Dialog_Text));
                        dialog.setCancelable(false);
                    } else {

                        dialog.setMessage("List Already Clear");
                        dialog.setCancelable(true);
                        dialog.show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        NEW_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Identify.this)
                        .setIcon(R.drawable.exit_icon)
                        .setTitle("Quit")
                        .setMessage("Are you sure you want to Clear the List ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ClearData();
                                tempList.clear();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    iuhfService.inventoryStop();
//                    iuhfService.closeDev();
//                    Toast.makeText(Identify_Form.this, "Start Fetching Data...", Toast.LENGTH_SHORT).show();
//                    FetchData(tempList);
//                    dialog.show();
//                    dialog.setMessage(getString(R.string.Dialog_Text));
//                    dialog.setCancelable(false);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    //Method For Clear Components
    private void ClearData() {
        LibraryItemType.setText("--");
        BookAddedIn.setText("--");
        BookCategory.setText("--");
        ItemStatus.setText("--");
        SubjectTitle.setText("--");
        Language.setText("--");
        Edition.setText("--");
        Publisher.setText("--");
        RFIDNo.setText("--");
        AccessNo.setText("--");
        Author.setText("--");
        Title.setText("--");
        YearOfPublication.setText("--");
        EntryDate.setText("--");


    }

    //Method For Fetching Data from Server
    private void FetchData(String epcvalue) throws JSONException {
        String url = "http://164.52.223.163:4510/api/GetAssetInfoBySearch?RfidTagId=" + epcvalue;

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    if (array.length() == 0) {
                        dialog.dismiss();
                        Toast.makeText(Identify.this, "No Data Available of this tag...", Toast.LENGTH_SHORT).show();
                    } else {


                        JSONObject object = array.getJSONObject(0);
                        String pO_unique_id = object.getString("pO_UNIQUE_ID");
                        String saP_ASSET_CODE = object.getString("saP_ASSET_CODE");
                        String seriaL_NO = object.getString("seriaL_NO");
                        String status = object.getString("status");
                        String planT_CODE = object.getString("planT_CODE");
                        String employee = object.getString("employee");
                        String department = object.getString("department");
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
                        String pmdonecq = object.getString("pmdonecq");
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


                        LibraryItemType.setText(model);
                        BookAddedIn.setText(alloteD_TO_LOCATION);
                        BookCategory.setText(amC_ASSET_CATEGORY);
                        ItemStatus.setText(printeR_TYPE);
                        SubjectTitle.setText(location);
                        Language.setText(pO_NUMBER);
                        Edition.setText(alloteD_TO_USER);
                        Publisher.setText(quantity);
//                        RFIDNo.setText(RFIDNo1);
                        AccessNo.setText(vendor);
                        Author.setText(saP_ASSET_CODE);
                        Title.setText(asseT_ID);
                        YearOfPublication.setText(oem);
//                        EntryDate.setText(EntryDate1);
                        dialog.dismiss();
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
                Toast.makeText(Identify.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        queue.add(jsObjRequest);

    }

    private void Scan() {
        iuhfService.openDev();
        result = iuhfService.read_area(1, "2", "6", "00000000");

        if (result != null) {
            try {
//                    Toast.makeText(Identify_Form.this, epc, Toast.LENGTH_SHORT).show();
                //Toast.makeText(Identify_Form.this, "Start Fetching Data...", Toast.LENGTH_SHORT).show();
                FetchData(result);
                dialog.show();
                dialog.setMessage(getString(R.string.Dialog_Text));
                dialog.setCancelable(false);
                iuhfService.inventoryStop();
                iuhfService.closeDev();
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
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
}