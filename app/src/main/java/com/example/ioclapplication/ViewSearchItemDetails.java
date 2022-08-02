package com.example.ioclapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ViewSearchItemDetails extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ModelViewDetails> list = new ArrayList<>();
    //    ProgressDialog dialog=new ProgressDialog(this);
    ModelViewDetails data_models;
    TextView Searchk;
    Button Searchbutton;
    String Datevalue = "no";
    final Calendar myCalendar = Calendar.getInstance();
    ProgressDialog dialog;
    DetailsViewItem adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_item_details);
        recyclerView = findViewById(R.id.Listreyclerview);
        Searchbutton = findViewById(R.id.SearchDate);
        Searchk = findViewById(R.id.Accession_no1);

        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();

        };
        dialog = new ProgressDialog(this);



        Searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Datevalue.matches("no")) {

                    FetchAssetId();

        dialog.setMessage("Fetching Data...");
        dialog.setCancelable(false);
                    dialog.show();
                } else {
                    Toast.makeText(ViewSearchItemDetails.this, "please Select Date ", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Searchk.setOnClickListener(view -> new DatePickerDialog(ViewSearchItemDetails.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());

//
//Searchk.addTextChangedListener(new TextWatcher() {
//    @Override
//    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//    }
//
//    @Override
//    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//    }
//
//    @Override
//    public void afterTextChanged(Editable editable) {
//        filter(editable.toString());
//    }
//});
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        Searchk.setText(dateFormat.format(myCalendar.getTime()));
        Datevalue = dateFormat.format(myCalendar.getTime());

    }

    private void FetchAssetId() {
        LocalDB localDB = new LocalDB(this);
        List<DatamodelLocal> listdb = localDB.getAllContacts();
        String devicename = "";
        try {
            devicename= listdb.get(0).getDeviceName();
        }catch (Exception e )
        {
            if (devicename.length()==0)
            {
                devicename="SD60RT";
            }
            e.printStackTrace();
        }
//        String url = "http://164.52.223.163:4510/api/ReadRfidByDate?Date=" + Datevalue;
//        String url="http://164.52.223.163:4510/api/ReadRfidByDate?Date="+Datevalue.concat("&HHRID=")+devicename;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, ApiUrl.ScannedItemList + Datevalue.concat("&HHRID=") + devicename, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    list.clear();
                    if (array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String id = object.optString("id");
                            String tagid = object.optString("tagId");
                            String location = object.optString("location");
                            String hhrId = object.optString("hhrId");
                            String readTime = object.optString("readTime");
                            String assetName = object.optString("assetName");
                            String assetid = object.optString("asset_Id");
                            list.add(new ModelViewDetails(id, tagid, location, hhrId, readTime, assetid, assetName));
                        }

                        adapter = new DetailsViewItem(getApplicationContext(), list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapter);

//                        RearrangeItems();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(ViewSearchItemDetails.this, response.concat(" HHR ID is not Match..."), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                    dialog.dismiss();
                    Toast.makeText(ViewSearchItemDetails.this, "No Data ...", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewSearchItemDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        });

        queue.add(request);

    }

//    private void filter(String text) {
//        // creating a new array list to filter our data.
//        ArrayList<ModelViewDetails> filteredlist = new ArrayList<>();
//
//        // running a for loop to compare elements.
//        for (ModelViewDetails item : list) {
//            // checking if the entered string matched with any item of our recycler view.
//            if (item.getReadTime().toLowerCase().contains(text.toLowerCase())) {
//                // if the item is matched we are
//                // adding it to our filtered list.
//                filteredlist.add(item);
//            }
//        }
//        if (filteredlist.isEmpty()) {
//            // if no item is added in filtered list we are
//            // displaying a toast message as no data found.
//            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
//        } else {
//            // at last we are passing that filtered
//            // list to our adapter class.
//            adapter.filterList(filteredlist);
//        }
//    }

//    private void RearrangeItems() {
//        Collections.shuffle(list, new Random(System.currentTimeMillis()));
//        Collections.sort(list, new Comparator<ModelViewDetails>() {
//            @Override
//            public int compare(ModelViewDetails data_model, ModelViewDetails t1) {
//                return data_model.getReadTime().compareToIgnoreCase(t1.getReadTime());
//            }
//        });
//         adapter = new DetailsViewItem(this, list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setAdapter(adapter);
//    }
}