package com.scanez.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scanez.R;
import com.scanez.db.DatabaseHelper;
import com.scanez.model.Data;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aspl on 27/3/18.
 */

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.MyViewHolder> {

    Context context;
    List<Data> dataList;

    private DatabaseHelper mDatabase;
    Recycler_Adapter recycler_adapter;

    public Recycler_Adapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
        mDatabase = new DatabaseHelper(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_code_history, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        setFadeAnimation(holder.itemView);

        final Data singleData = dataList.get(position);
        holder.codeNote.setText(singleData.getNote());
        holder.codeDate.setText(singleData.getDate_time());

        Picasso.get()
                .load(singleData.getImagePath())
                .resize(100, 100)
                .centerCrop()
                .into(holder.codeImage);

        holder.codeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database
                mDatabase.deleteProduct(singleData.getKey());
                dataList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.code_image)
        ImageView codeImage;
        @BindView(R.id.code_date)
        TextView codeDate;
        @BindView(R.id.code_qr)
        ImageView codeQr;
        @BindView(R.id.code_barcode)
        ImageView codeBarcode;
        @BindView(R.id.code_note)
        TextView codeNote;
        @BindView(R.id.code_delete)
        ImageView codeDelete;
        @BindView(R.id.layout_animation)
        LinearLayout layoutAnimation;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }
}
