package com.example.android.e_meds.adapter;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.e_meds.activities.Medicine;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchBarAdapter extends RecyclerView.Adapter<SearchBarAdapter.SearchViewHolder> {
    ArrayList<String> arrayList=new ArrayList<>();
    Context context;
    View view;

    public SearchBarAdapter(Context context,ArrayList<String> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false);
       return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.text1.setText(arrayList.get(position));

        holder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tap(position);
            }
        });
    }

    private void tap(int position) {
        context.startActivity(new Intent(context, Medicine.class));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class SearchViewHolder  extends RecyclerView.ViewHolder{

        TextView text1;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            text1=itemView.findViewById(android.R.id.text1);
        }
    }
}
