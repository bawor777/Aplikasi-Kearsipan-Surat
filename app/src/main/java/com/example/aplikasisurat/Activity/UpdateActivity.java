/*
 *  Aplikasi Surat
 *     UpdateActivity.java
 *     Created by Rifan Kurniawan on 14/8/2022
 *     email 	    : cah2701@gmail.com
 *     Copyright © 2022 Rifan Kurniawan. All rights reserved.
 */

package com.example.aplikasisurat.Activity;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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

public class UpdateActivity extends AppCompatActivity {

    EditText edtKodeCabang, edtJudul, edtNoSurat, edtTanggal;
    ImageView imgHolder;
    String ID;
    Button btnGalery, btnUpdate;

    private String mediaPath;
    private String postPath;


    ApiInterface mApiInterface;
    private static final int REQUEST_PICK_PHOTO = Config.REQUEST_PICK_PHOTO;
    private static final int REQUEST_WRITE_PERMISSION = Config.REQUEST_WRITE_PERMISSION;

    private final int ALERT_DIALOG_CLOSE = Config.ALERT_DIALOG_CLOSE;
    private final int ALERT_DIALOG_DELETE = Config.ALERT_DIALOG_DELETE;
//    private static final String UPDATE_FLAG = Config.UPDATE_FLAG;

    // Akses Izin Ambil Gambar dari Storage

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            updateImageUpload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //identifikasi komponen action bar
        String actionBarTitle;
        actionBarTitle = "Ubah";
        Objects.requireNonNull(getSupportActionBar()).setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //identifikasi komponen form
        edtKodeCabang = (EditText) findViewById(R.id.edt_kode_cabang);
        edtJudul = (EditText) findViewById(R.id.edt_judul);
        edtNoSurat = (EditText) findViewById(R.id.edt_no_surat);
        edtTanggal = (EditText) findViewById(R.id.edt_tanggal);
        imgHolder = (ImageView) findViewById(R.id.imgHolder);
        btnGalery = (Button) findViewById(R.id.btn_galery);
        btnUpdate = (Button) findViewById(R.id.btn_submit);

        // Identifikasi intent ke Komponen Form
        Intent mIntent = getIntent();
        ID = mIntent.getStringExtra("id");
        edtKodeCabang.setText(mIntent.getStringExtra("kode_cabang"));
        edtJudul.setText(mIntent.getStringExtra("judul"));
        edtNoSurat.setText(mIntent.getStringExtra("no_surat"));
        edtTanggal.setText(mIntent.getStringExtra("tanggal"));



        // Masukan Gambar Ke Image View Gunakan Glide
        Glide.with(UpdateActivity.this)
                .load(Config.IMAGES_URL + mIntent.getStringExtra("foto"))
                .fitCenter()
                .into(imgHolder);


        //definisi api
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        //fungsi tombol gallery
        btnGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                       android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
            }
        });
        // Fungsi Tombol Update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateImageUpload();
            }
        });

    }

        // Akses Izin Ambil Gambar dari Storage
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if(resultCode == RESULT_OK){
                if (requestCode == REQUEST_PICK_PHOTO){
                    if (data != null){
                        //ambil foto dr galeri
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        assert  cursor !=null;
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        mediaPath = cursor.getString(columnIndex);
                        imgHolder.setImageURI(data.getData());
                        cursor.close();

                        postPath = mediaPath;
                    }
                }
            }
        }


    // update Gambar
    private void updateImageUpload() {
//        final String tanggal = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (mediaPath == null) {
            Toast.makeText(getApplicationContext(), "Pilih gambar dulu, baru simpan ...!", Toast.LENGTH_LONG).show();
        } else {
            File imagefile = new File(mediaPath);
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/foto"), imagefile);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto", imagefile.getName(), reqBody);

            Call<Surat> ubahSurat = mApiInterface.postUpdateSurat(partImage,
                    RequestBody.create(MediaType.parse("text/plain"), ID),
                    RequestBody.create(MediaType.parse("text/plain"), edtKodeCabang.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), edtJudul.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), edtNoSurat.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), edtTanggal.getText().toString())
            );
            ubahSurat.enqueue(new Callback<Surat>() {
                @Override
                public void onResponse(Call<Surat> call, Response<Surat> response) {
                    Toast.makeText(UpdateActivity.this ,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    LihatActivity.ma.refresh();
                    finish();
                }

                @Override
                public void onFailure(Call<Surat> call, Throwable t) {
                    Log.d("RETRO", "ON FAILUR :" +t.getMessage());
//                    Log.d("RETRO", "ON FAILURE : " + t.getCause());
                    Toast.makeText(getApplicationContext(), "Error, foto", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    // Cek Versi Android Tuk Minta Izin
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            updateImageUpload();

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);

    }


    // Menu Kembali Ke Home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "BATAL";
            dialogMessage = "Apakah Anda ingin membatalkan Perubahan Data ?";
        } else {
            dialogMessage = "Apakah Anda yakin ingin Menghapus Data ini ?";
            dialogTitle = "HAPUS SURAT";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (isDialogClose) {
                            finish();
                        } else {
                            // Kode Hapus
                            if (!ID.trim().isEmpty()) {
                                Call<Surat> deleteSurat = mApiInterface.deleteSurat(ID );
                                deleteSurat.enqueue(new Callback<Surat>() {
                                    @Override
                                    public void onResponse(Call<Surat> call, Response<Surat> response) {
                                        Toast.makeText(UpdateActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT);
                                        LihatActivity.ma.refresh();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<Surat> call, Throwable t) {
                                        Toast.makeText(UpdateActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(getApplicationContext(), "Error" + t, Toast.LENGTH_LONG).show();
                                    }

                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                })

                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
