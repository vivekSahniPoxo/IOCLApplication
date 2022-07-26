package com.example.ioclapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.viewholder> {
    List<String> templist;
    List<InventoryDataModel> list;
    Context context;

    public InventoryAdapter(List<String> tempList_Inventory, List<InventoryDataModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.templist = tempList_Inventory;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list, parent, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        InventoryDataModel model = list.get(position);
        holder.language.setText(model.getSeriaL_NO());
        holder.publisher.setText(model.getAsset());
        holder.head_title.setText(model.getEmployee());
        holder.head_subject.setText(model.getEmployeename());
        holder.h1.setText("Employee Name :");
        holder.h2.setText("Employee ID :");
        holder.h3.setText("Asset Name :");
        holder.h4.setText("Serial Number :");

        //Change color if data found
        if (model.getColor() == "Green") {
//            holder.cardView.setCardBackgroundColor(Color.rgb(46, 139, 87));
            holder.ListLayout.setBackgroundColor(Color.rgb(46, 139, 87));
            holder.head_subject.setTextColor(Color.parseColor("#FFFFFF"));
            holder.head_title.setTextColor(Color.parseColor("#FFFFFF"));
            holder.publisher.setTextColor(Color.parseColor("#FFFFFF"));
            holder.language.setTextColor(Color.parseColor("#FFFFFF"));
            holder.h4.setTextColor(Color.parseColor("#FFFFFF"));
            holder.h3.setTextColor(Color.parseColor("#FFFFFF"));
            holder.h2.setTextColor(Color.parseColor("#FFFFFF"));
            holder.h1.setTextColor(Color.parseColor("#FFFFFF"));

        } else {
            holder.ListLayout.setBackgroundColor(Color.parseColor("#F0FFFF"));
            holder.head_subject.setTextColor(Color.parseColor("#000000"));
            holder.head_title.setTextColor(Color.parseColor("#000000"));
            holder.publisher.setTextColor(Color.parseColor("#000000"));
            holder.language.setTextColor(Color.parseColor("#000000"));
            holder.h1.setTextColor(Color.parseColor("#000000"));
            holder.h2.setTextColor(Color.parseColor("#000000"));
            holder.h3.setTextColor(Color.parseColor("#000000"));
            holder.h4.setTextColor(Color.parseColor("#000000"));
        }

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(context,AuditDetrails.class);
//                i.putExtra("keyId",model.getAuditId());
//                context.startActivity(i);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    int bookFooundCount = 0;

    public int getFilter(Object search_value) {

        if (templist.contains(search_value)) {
            for (InventoryDataModel row : list) {

                if (row.getTaG_ID().equals(search_value)) {
                    row.setColor("Green");
                    row.setStatusF("True");
                    bookFooundCount = bookFooundCount + 1;
                    notifyDataSetChanged();
                    break;
                }
            }
            templist.remove(search_value);
        }


        return bookFooundCount;
    }



    public class viewholder extends RecyclerView.ViewHolder {
        TextView Subject, Title, publisher, author, edition, language, access_No, head_subject, head_title, expand, minimize;
        LinearLayout list_layout;
        CardView cardView, card_details;
        ConstraintLayout ListLayout;
        TextView h1, h2, h3, h4;

        public viewholder(@NonNull View itemView) {
            super(itemView);


            ListLayout = itemView.findViewById(R.id.LayoutList);
            Subject = itemView.findViewById(R.id.Subject);
            Title = itemView.findViewById(R.id.Booktitle);
//             expand = itemView.findViewById(R.id.expand);
//             minimize = itemView.findViewById(R.id.minimize);
            list_layout = itemView.findViewById(R.id.list_layout);
            cardView = itemView.findViewById(R.id.cardView);
            card_details = itemView.findViewById(R.id.cardView_Details);
            publisher = itemView.findViewById(R.id.Publisher);
            author = itemView.findViewById(R.id.Authorname);
            edition = itemView.findViewById(R.id.Edition);
            language = itemView.findViewById(R.id.Language);
            access_No = itemView.findViewById(R.id.Access_No);
            head_subject = itemView.findViewById(R.id.Head_subject);
            head_title = itemView.findViewById(R.id.Head_Tilte);
            h1 = itemView.findViewById(R.id.textView6);
            h2 = itemView.findViewById(R.id.textView7);
            h3 = itemView.findViewById(R.id.textView8);
            h4 = itemView.findViewById(R.id.textView9);


        }


    }

}
