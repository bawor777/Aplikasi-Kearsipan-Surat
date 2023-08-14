/*
 *  Aplikasi Surat
 *     InputActivity.java
 *     Created by Rifan Kurniawan on 14/8/2022
 *     email 	    : cah2701@gmail.com
 *     Copyright © 2022 Rifan Kurniawan. All rights reserved.
 */

package com.example.aplikasisurat.Activity;

import static com.example.aplikasisurat.Activity.LihatActivity.ma;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasisurat.Config;
import com.example.aplikasisurat.Controller.ApiClient;
import com.example.aplikasisurat.Controller.ApiInterface;
import com.example.aplikasisurat.Model_Surat.Surat;
import com.example.aplikasisurat.R;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputActivity extends AppCompatActivity {
    EditText edtKodeCabang, edtJudul, edtNoSurat, edtTanggal;
    Button btnGalery, btSubmit;
    ImageView imgHolder;

    private String mediaPath;
    private String postPath;


    ApiInterface mApiInterface;
    private final int ALERT_DIALOG_CLOSE = Config.ALERT_DIALOG_CLOSE;
    private static final int REQUEST_PICK_PHOTO = Config.REQUEST_PICK_PHOTO;
    private static final int REQUEST_WRITE_PERMISSION = Config.REQUEST_WRITE_PERMISSION;
//    private static final String INSERT_FLAG = Config.INSERT_FLAG;

    // Akses Izin Ambil Gambar dari Storage
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            saveImageUpload();
        }
    }

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

//         Identifikasi Komponen Action Bar
        String actionBarTitle;
        actionBarTitle = "Tambah";
        Objects.requireNonNull(getSupportActionBar()).setTitle(actionBarTitle);
//        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Identifikasi Komponen Form
        edtKodeCabang = (EditText) findViewById(R.id.edt_kode_cabang);
        edtJudul = (EditText) findViewById(R.id.edt_judul);
        edtJudul.getBackground().setAlpha(128);
        edtNoSurat = (EditText) findViewById(R.id.edt_no_surat);
        edtTanggal = (EditText) findViewById(R.id.edt_tanggal);
        imgHolder = (ImageView) findViewById(R.id.imgHolder);
        btnGalery = (Button) findViewById(R.id.btn_galery);
        btSubmit = (Button) findViewById(R.id.btn_submit);


        // Definisi API
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        // Fungsi Tombol Pilih Galery
        btnGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
            }
        });

        // Fungsi Tombol Camera
//        btnCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if ( CameraIntent.resolveActivity(getPackageManager()) !=null){
//                    startActivityForResult(CameraIntent, REQUEST_PICK_PHOTO);
//                }
//
//            }
//        });

        // Fungsi Tombol Simpan
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
    }

    //    // Akses Izin Ambil Gambar dari Storage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    //ambil foto dr galeri
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    imgHolder.setImageURI(data.getData());

                    postPath = mediaPath;
                }
            }
        }
    }

    // Simpan Gambar
    private void saveImageUpload() {
//        final String tanggal = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (mediaPath == null) {
            Toast.makeText(getApplicationContext(), "Pilih gambar dulu, baru simpan ...!", Toast.LENGTH_LONG).show();
        } else {
            File imagefile = new File(mediaPath);
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto", imagefile.getName(), reqBody);

            Call<Surat> postSuratCall = mApiInterface.postSurat(partImage,
                    RequestBody.create(MediaType.parse("text/plain"), edtKodeCabang.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), edtJudul.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), edtNoSurat.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), edtTanggal.getText().toString())
            );
            postSuratCall.enqueue(new Callback<Surat>() {
                @Override
                public void onResponse(Call<Surat> call, Response<Surat> response) {
                    ma.refresh();

                    finish();
                }

                @Override
                public void onFailure(Call<Surat> call, Throwable t) {
                    Log.d("RETRO", "ON FAILUR :" + t.getMessage());
                }
            });
        }
    }

    // Cek Versi Android Tuk Minta Izin
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            saveImageUpload();
        }
    }

    @Override
    public void onBackPressed() { showAlertDialog(ALERT_DIALOG_CLOSE);}

    private void showAlertDialog(int type) {
        final boolean  isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle = null, dialogMessage = null;
        if (isDialogClose){
            dialogTitle = "BATAL";
            dialogMessage = "Apakah Anda ingin membatalkan Penambahan Data ?";
    }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(dialogTitle);
        builder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent list = new Intent(InputActivity.this, LihatActivity.class);
                startActivity(list);
                LihatActivity.ma.refresh();
                finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}

//    // Menu Kembali Ke Home
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // API 5+ solution
//                onBackPressed();
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//}
