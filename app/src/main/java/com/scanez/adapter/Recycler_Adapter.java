package com.scanez.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scanez.R;
import com.scanez.db.DatabaseHelper;
import com.scanez.model.Data;

import java.io.File;
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        setFadeAnimation(holder.itemView);

        final Data singleData = dataList.get(position);
        holder.codeNote.setText(singleData.getNote());
        holder.codeDate.setText(singleData.getDate_time());

        if (singleData.getType().equalsIgnoreCase("Qr")){
            holder.codeBarcode.setImageResource(R.drawable.nav_qr);
        }
        else if (singleData.getType().equalsIgnoreCase("Barcode")){
            holder.codeBarcode.setImageResource(R.drawable.nav_barcode);
        }

        final File imgFile = new File(singleData.getImagePath());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.codeImage.setImageBitmap(myBitmap);
        } else {
            holder.codeImage.setImageResource(R.drawable.holderimage);
        }

        holder.codeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_code_display);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ImageView code_display=(ImageView) dialog.findViewById(R.id.display_image);
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                code_display.setImageBitmap(myBitmap);
                TextView ok=(TextView) dialog.findViewById(R.id.click_ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        holder.codeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                // Setting Dialog Title
                alertDialog.setTitle("Confirm Delete...");
                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want delete this?");
                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.dialog_delete);
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                mDatabase.deleteProduct(singleData.getKey());
                                dataList.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });
                // Showing Alert Message
                alertDialog.show();
                //delete row from database

            }
        });
        holder.codeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable;
                Bitmap bitmap1;
                bitmapDrawable = (BitmapDrawable) holder.codeImage.getDrawable();// get the from imageview or use your drawable from drawable folder
                bitmap1 = bitmapDrawable.getBitmap();
                String imgBitmapPath= MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap1,"title",null);
                Uri imgBitmapUri=Uri.parse(imgBitmapPath);
                String shareText="Share image and text";
                Intent shareIntent=new Intent(Intent.ACTION_SEND);
                shareIntent.setType("*/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM,imgBitmapUri);
                shareIntent.putExtra(Intent.EXTRA_TEXT, singleData.getNote());
                context.startActivity(Intent.createChooser(shareIntent,"Share Code using"));

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
        @BindView(R.id.code_share)
        ImageView codeShare;

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
