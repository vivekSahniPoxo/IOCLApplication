package com.example.ioclapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    TextView LibraryItemType, BookAddedIn, BookCategory, ItemStatus, SubjectTitle, Language, Edition, Publisher, RFIDNo, AccessNo, Author, Title, YearOfPublication, EntryDate, Seatno1, Cubial11, Floor, Cabin;
    ProgressDialog dialog;
    CardView scan_button;
    IUHFService iuhfService;
    public List<String> tempList;
    String epc;
    String userID = null;
    String result;
    String employee=null,seat1="--";


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
        Seatno1 = findViewById(R.id.Seattxt);
        Floor = findViewById(R.id.floor);
        Cabin = findViewById(R.id.Cabintxt);
        Cubial11 = findViewById(R.id.cubicaltxt);


        Seatno1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (seat1.matches("--"))
                {
                    Toast.makeText(Identify.this, "Scan Tag first....", Toast.LENGTH_SHORT).show();
                }else {
                    Intent i = new Intent(Identify.this, UpdateLocation.class);
                    i.putExtra("UserID", employee);
                    startActivity(i);
                    finish();
                }
            }
        });


        iuhfService = UHFManager.getUHFService(this);
        iuhfService.setAntennaPower(5);
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
                        Toast.makeText(Identify.this, "" + result, Toast.LENGTH_SHORT).show();
                        FetchData(result);
                        dialog.show();
                        dialog.setMessage(getString(R.string.Dialog_Text));
                        dialog.setCancelable(false);
                    } else {

                        Toast.makeText(Identify.this, "Scan Tag Again...", Toast.LENGTH_SHORT).show();
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
        Floor.setText("--");
        Cabin.setText("--");
        Cubial11.setText("--");
        Seatno1.setText("--");

    }

    //Method For Fetching Data from Server
    private void FetchData(String epcvalue) throws JSONException {
//        String url = "http://mudvprfidiis:82/api/GetAssetInfoBySearch?RfidTagId=" + epcvalue;
//        String url = "http://164.52.223.163:4510/api/GetAssetInfoBySearch?RfidTagId=" + epcvalue;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, ApiUrl.IdentifyApi + epcvalue, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    if (array.length() == 0) {
                        dialog.dismiss();
                        Toast.makeText(Identify.this, "No Data Available of this tag...", Toast.LENGTH_SHORT).show();
                    } else {


                        JSONObject object = array.getJSONObject(0);
                         employee = object.optString("employee");
                        String taG_ID = object.optString("taG_ID");
                        String pO_NUMBER = object.optString("pO_NUMBER");
                        String asset = object.optString("asset");
                        String employeename = object.optString("employeename");
                        String component = object.optString("component");
                        String seriaL_NO = object.optString("seriaL_NO");
                        String oem = object.optString("oem");
                        String model = object.optString("model");
                        userID = object.optString("useR_ID");
                        String wing = object.optString("wing");
                        String floor = object.optString("floor");
                        String cabin = object.optString("cabin");
                         seat1 = object.optString("seaT_NO");
                        String cubical = object.optString("cubical");

                        dialog.dismiss();


                        LibraryItemType.setText(model);
//                        BookAddedIn.setText();
//                        BookCategory.setText();
                        ItemStatus.setText(asset);
//                        SubjectTitle.setText();
                        Language.setText(pO_NUMBER);
//                        Edition.setText();
                        Publisher.setText(seriaL_NO);
//                        RFIDNo.setText(RFIDNo1);
                        AccessNo.setText(component);
                        Author.setText(employee);
                        Title.setText(employeename);
                        YearOfPublication.setText(oem);
                        Floor.setText(floor);
                        Cabin.setText(cabin);
                        Cubial11.setText(cubical);
                        Seatno1.setText(seat1);
//                        EntryDate.setText(EntryDate1);
                        dialog.dismiss();
                    }
                    Log.e("response", response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            dialog.dismiss();
            Toast.makeText(Identify.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
        });


        queue.add(jsObjRequest);

    }

    private void Scan() {
        iuhfService.openDev();
        result = iuhfService.read_area(1, "2", "6", "00000000");
//        Toast.makeText(Identify.this, ""+result, Toast.LENGTH_SHORT).show();
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