package com.scanez.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.scanez.R;
import com.scanez.activity.BaseActivity;
import com.scanez.activity.MainActivity;
import com.scanez.db.DatabaseHelper;
import com.scanez.model.Data;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by aspl on 24/3/18.
 */

public class Dashboard_Fragment extends BaseFragment {

    String inputValue, Qrnotes;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    Bitmap bitmapQr, bitmapBarcode;
    QRGEncoder qrgEncoder;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    String TAG = "GenerateQRCode";

    View view;
    @BindView(R.id.clickScanQRCode)
    ImageView clickScanQRCode;
    Unbinder unbinder;
    @BindView(R.id.txt_clickScanQRCode)
    TextView txtClickScanQRCode;
    @BindView(R.id.clickToBarcode)
    ImageView clickToBarcode;
    @BindView(R.id.txt_clickToBarcode)
    TextView txtClickToBarcode;
    @BindView(R.id.linear_ClickById)
    LinearLayout linearClickById;
    @BindView(R.id.linear_ClickByName)
    LinearLayout linearClickByName;
    @BindView(R.id.edt_nameOrid)
    EditText edtNameOrid;

    AlertDialog dialog;
    @BindView(R.id.btn_SearchCode)
    Button btnSearchCode;
    DatabaseHelper myDb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        unbinder = ButterKnife.bind(this, view);
        myDb = new DatabaseHelper(getActivity());
        return view;
    }

    @Override
    public void setToolbarForFragment() {
        ((BaseActivity) getActivity()).getAppbar().setVisibility(View.VISIBLE);
        ((BaseActivity) getActivity()).getToolBar().setVisibility(View.VISIBLE);
        ((BaseActivity) getActivity()).getImgToolBarHome().setVisibility(View.VISIBLE);
        ((BaseActivity) getActivity()).getImgToolBarBack().setVisibility(View.GONE);
        ((BaseActivity) getActivity()).getTextViewToolBarTitle().setText("sCanEz");
        ((BaseActivity) getActivity()).getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.clickScanQRCode)
    public void onClickedQRCodeScanner() {
        mainInterfaces.OpenFullScanner();
    }

    @OnClick({R.id.txt_clickScanQRCode, R.id.clickToBarcode, R.id.txt_clickToBarcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_clickScanQRCode:
                mainInterfaces.OpenFullScanner();
                break;
            case R.id.clickToBarcode:
                mainInterfaces.OpenFullScanner();
                break;
            case R.id.txt_clickToBarcode:
                mainInterfaces.OpenFullScanner();
                break;
        }
    }

    @OnClick({R.id.linear_ClickById, R.id.linear_ClickByName, R.id.edt_nameOrid, R.id.btn_SearchCode})
    public void onClickeView(View view) {
        switch (view.getId()) {
            case R.id.linear_ClickById:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog_by_id, null);
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
                Button qr = (Button) mView.findViewById(R.id.btn_GenerateQR);
                Button barcode = (Button) mView.findViewById(R.id.btn_GenerateBarcode);
                final EditText id = (EditText) mView.findViewById(R.id.edt_id);

                qr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputValue = id.getText().toString().trim();
                        if (inputValue.length() > 0) {
                            dialog.dismiss();
                            WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
                            Display display = manager.getDefaultDisplay();
                            Point point = new Point();
                            display.getSize(point);
                            int width = point.x;
                            int height = point.y;
                            int smallerDimension = width < height ? width : height;
                            smallerDimension = smallerDimension * 3 / 4;

                            qrgEncoder = new QRGEncoder(
                                    inputValue, null,
                                    QRGContents.Type.TEXT,
                                    smallerDimension);

                            try {
                                bitmapQr = qrgEncoder.encodeAsBitmap();
                            } catch (WriterException e) {
                                e.printStackTrace();
                            }

                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                            View mView = getLayoutInflater().inflate(R.layout.dialog_imageview, null);
                            mBuilder.setView(mView);

                            ImageView qr = (ImageView) mView.findViewById(R.id.dialog_imageview);
                            Button saveQr = (Button) mView.findViewById(R.id.btn_SaveQR);
                            final EditText note = (EditText) mView.findViewById(R.id.edt_note);

                            qr.setImageBitmap(bitmapQr);

                            dialog = mBuilder.create();
                            dialog.show();

                            saveQr.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {

                                        final File file = new File(savePath, System.currentTimeMillis() + ".jpg");
                                        FileOutputStream out = null;
                                        final Bitmap bitmap = bitmapQr;
                                        try {
                                            out = new FileOutputStream(file);
                                            if (bitmap != null) {
                                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                            } else {
                                                throw new FileNotFoundException();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        } finally {
                                            if (out != null) {
                                                out.flush();
                                                out.close();

                                                if (bitmap != null) {


                                                    /*save = QRGSaver.save(savePath, id.getText().toString().trim(), bitmapQr, QRGContents.ImageType.IMAGE_JPEG);

                                                    result = save ? "Image Saved" : "Image Not Saved";
                                                    Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();*/

                                                    Date c = Calendar.getInstance().getTime();
                                                    System.out.println("Current time => " + c);

                                                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                                    String formattedDate = df.format(c);

                                                    Qrnotes = note.getText().toString();

                                                    Data newProduct = new Data(Qrnotes, "Qr", formattedDate, file.getPath());
                                                    myDb.addProduct(newProduct);

                                                    dialog.dismiss();
                                                    Toast.makeText(getContext(), "Saved successfully", Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } else {
                            id.setError("Required");
                        }
                    }
                });

                barcode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputValue = id.getText().toString().trim();

                        if (inputValue.length() > 0) {
                            try {
                                dialog.dismiss();

                                bitmapBarcode = encodeAsBitmap(id.getText().toString(), BarcodeFormat.CODE_128, 600, 300);

                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                                View mView = getLayoutInflater().inflate(R.layout.dialog_imageview, null);
                                mBuilder.setView(mView);

                                ImageView qr = (ImageView) mView.findViewById(R.id.dialog_imageview);
                                Button saveBarcode = (Button) mView.findViewById(R.id.btn_SaveQR);
                                final EditText note = (EditText) mView.findViewById(R.id.edt_note);

                                qr.setImageBitmap(bitmapBarcode);

                                dialog = mBuilder.create();
                                dialog.show();

                                saveBarcode.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {

                                            final File file = new File(savePath, System.currentTimeMillis() + ".jpg");
                                            FileOutputStream outs = null;
                                            final Bitmap bitmap = bitmapBarcode;
                                            try {
                                                outs = new FileOutputStream(file);
                                                if (bitmap != null) {
                                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outs);
                                                } else {
                                                    throw new FileNotFoundException();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            } finally {
                                                if (outs != null) {
                                                    outs.flush();
                                                    outs.close();

                                                    if (bitmap != null) {

                                                        Date c = Calendar.getInstance().getTime();
                                                        System.out.println("Current time => " + c);

                                                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                                        String formattedDate = df.format(c);

                                                        Qrnotes = note.getText().toString();

                                                        Data newProduct = new Data(Qrnotes, "Barcode", formattedDate, file.getPath());
                                                        myDb.addProduct(newProduct);

                                                        dialog.dismiss();
                                                        Toast.makeText(getContext(), "Saved successfully", Toast.LENGTH_LONG).show();

                                                    }
                                                }
                                            }
                                        } catch (Exception es) {
                                            es.printStackTrace();
                                        }
                                    }
                                });

                            } catch (WriterException e) {
                                e.printStackTrace();
                            }
                        } else {
                            id.setError("Required");
                        }
                    }
                });

                break;
            case R.id.linear_ClickByName:

                AlertDialog.Builder mBuilders = new AlertDialog.Builder(getActivity());
                View mViews = getLayoutInflater().inflate(R.layout.dialog_by_name, null);
                mBuilders.setView(mViews);
                dialog = mBuilders.create();
                dialog.show();
                Button qrs = (Button) mViews.findViewById(R.id.btn_GenerateQRasName);
                Button barcodes = (Button) mViews.findViewById(R.id.btn_GenerateBarcodeAsName);
                final EditText ids = (EditText) mViews.findViewById(R.id.edt_name);

                qrs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputValue = ids.getText().toString().trim();
                        if (inputValue.length() > 0) {
                            dialog.dismiss();
                            WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
                            Display display = manager.getDefaultDisplay();
                            Point point = new Point();
                            display.getSize(point);
                            int width = point.x;
                            int height = point.y;
                            int smallerDimension = width < height ? width : height;
                            smallerDimension = smallerDimension * 3 / 4;

                            qrgEncoder = new QRGEncoder(
                                    inputValue, null,
                                    QRGContents.Type.TEXT,
                                    smallerDimension);
                            try {
                                bitmapQr = qrgEncoder.encodeAsBitmap();

                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                                View mView = getLayoutInflater().inflate(R.layout.dialog_imageview, null);
                                mBuilder.setView(mView);

                                ImageView qr = (ImageView) mView.findViewById(R.id.dialog_imageview);
                                Button saveQr = (Button) mView.findViewById(R.id.btn_SaveQR);
                                final EditText note = (EditText) mView.findViewById(R.id.edt_note);

                                qr.setImageBitmap(bitmapQr);

                                dialog = mBuilder.create();
                                dialog.show();

                                saveQr.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        boolean save;
                                        String result;
                                        try {
                                                final File file = new File(savePath, System.currentTimeMillis() + ".jpg");
                                                FileOutputStream out = null;
                                                final Bitmap bitmap = bitmapQr;
                                                try {
                                                    out = new FileOutputStream(file);
                                                    if (bitmap != null) {
                                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                                    } else {
                                                        throw new FileNotFoundException();
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                } finally {
                                                    if (out != null) {
                                                        out.flush();
                                                        out.close();

                                                        if (bitmap != null) {
                                                            Date c = Calendar.getInstance().getTime();
                                                            System.out.println("Current time => " + c);

                                                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                                            String formattedDate = df.format(c);

                                                            Qrnotes = note.getText().toString();

                                                            Data newProduct = new Data(Qrnotes, "Qr", formattedDate, file.getPath());
                                                            myDb.addProduct(newProduct);

                                                            dialog.dismiss();
                                                            Toast.makeText(getContext(), "Saved successfully", Toast.LENGTH_LONG).show();

                                                        }
                                                    }
                                                }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                            } catch (WriterException e) {
                                Log.v(TAG, e.toString());
                            }
                        } else {
                            ids.setError("Required");
                        }
                    }
                });

                barcodes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputValue = ids.getText().toString().trim();

                        if (inputValue.length() > 0) {
                            try {
                                dialog.dismiss();

                                bitmapBarcode = encodeAsBitmap(ids.getText().toString(), BarcodeFormat.CODE_128, 600, 300);

                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                                View mView = getLayoutInflater().inflate(R.layout.dialog_imageview, null);
                                mBuilder.setView(mView);

                                ImageView qr = (ImageView) mView.findViewById(R.id.dialog_imageview);
                                Button saveBarcode = (Button) mView.findViewById(R.id.btn_SaveQR);
                                final EditText note = (EditText) mView.findViewById(R.id.edt_note);

                                qr.setImageBitmap(bitmapBarcode);

                                dialog = mBuilder.create();
                                dialog.show();

                                saveBarcode.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {

                                            final File file = new File(savePath, System.currentTimeMillis() + ".jpg");
                                            FileOutputStream outs = null;
                                            final Bitmap bitmap = bitmapBarcode;
                                            try {
                                                outs = new FileOutputStream(file);
                                                if (bitmap != null) {
                                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outs);
                                                } else {
                                                    throw new FileNotFoundException();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            } finally {
                                                if (outs != null) {
                                                    outs.flush();
                                                    outs.close();

                                                    if (bitmap != null) {

                                                        Date c = Calendar.getInstance().getTime();
                                                        System.out.println("Current time => " + c);

                                                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                                        String formattedDate = df.format(c);

                                                        Qrnotes = note.getText().toString();

                                                        Data newProduct = new Data(Qrnotes, "Barcode", formattedDate, file.getPath());
                                                        myDb.addProduct(newProduct);

                                                        dialog.dismiss();
                                                        Toast.makeText(getContext(), "Saved successfully", Toast.LENGTH_LONG).show();

                                                    }
                                                }
                                            }
                                        } catch (Exception es) {
                                            es.printStackTrace();
                                        }
                                    }
                                });

                            } catch (WriterException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ids.setError("Required");
                        }
                    }
                });


                break;
            case R.id.edt_nameOrid:
                break;
            case R.id.btn_SearchCode:

                    String searchableUser = edtNameOrid.getText().toString();

                    Data consUserRegistration = myDb.findProduct(searchableUser);

                    if (myDb.isMasterEmpty()) {
                        if (consUserRegistration!=null) {
                            String searchUser = consUserRegistration.getImagePath();
                            String searchname = consUserRegistration.getNote();
                            String searchtype = consUserRegistration.getType();
                            String searchdate = consUserRegistration.getDate_time();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            View mView1 = getLayoutInflater().inflate(R.layout.dialog_search, null);
                            builder.setView(mView1);

                            TextView name = (TextView) mView1.findViewById(R.id.search_name);
                            TextView date = (TextView) mView1.findViewById(R.id.search_date);
                            TextView type = (TextView) mView1.findViewById(R.id.search_type);
                            ImageView code_image = (ImageView) mView1.findViewById(R.id.search_image);

                            name.setText(searchname);
                            date.setText(searchdate);
                            type.setText(searchtype);
                            Picasso.get().load(searchUser).into(code_image);
                            dialog = builder.create();
                            dialog.show();
                        }else {
                            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                break;
        }
    }

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }


}
