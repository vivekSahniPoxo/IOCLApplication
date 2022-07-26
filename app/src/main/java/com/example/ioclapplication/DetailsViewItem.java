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

public class DetailsViewItem extends RecyclerView.Adapter<DetailsViewItem.MYviewholder> {
    Context context;
    List<ModelViewDetails> list;

    public DetailsViewItem(Context context, List<ModelViewDetails> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MYviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list, parent, false);
        return new DetailsViewItem.MYviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MYviewholder holder, int position) {
        ModelViewDetails model = list.get(position);

        String currentString = model.getReadTime();
        String[] separated = currentString.split("T");
        // this will contain "Fruit"

        holder.ListLayout.setBackgroundColor(Color.parseColor("#F0FFFF"));

        String date1 = separated[0];
        String date2 = separated[1];
        holder.language.setText(model.getHhrId());
        holder.publisher.setText( date1.concat(" "+date2));
        holder.head_title.setText(model.getAssetId());
        holder.head_subject.setText(model.getAssetname());
        holder.h1.setText("Asset Name :");
        holder.h2.setText("Asset Id :");
        holder.h3.setText("Date :");
        holder.h4.setText("HHT ID :");
    }

    public void filterList(List<ModelViewDetails> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        list = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MYviewholder extends RecyclerView.ViewHolder {
        TextView Subject, Title, publisher, author, edition, language, access_No, head_subject, head_title, expand, minimize;
        LinearLayout list_layout;
        CardView cardView, card_details;
        TextView h1, h2, h3, h4;
        ConstraintLayout ListLayout;

        public MYviewholder(@NonNull View itemView) {
            super(itemView);
            ListLayout = itemView.findViewById(R.id.LayoutList);
            Subject = itemView.findViewById(R.id.Subject);
//            expand = itemView.findViewById(R.id.expand);
//            minimize = itemView.findViewById(R.id.minimize);
            Title = itemView.findViewById(R.id.Booktitle);
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
