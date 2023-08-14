/*
 *  Aplikasi Surat
 *     LihatActivity.java
 *     Created by Rifan Kurniawan on 14/8/2022
 *     email 	    : cah2701@gmail.com
 *     Copyright © 2022 Rifan Kurniawan. All rights reserved.
 */

package com.example.aplikasisurat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasisurat.Adapter.SuratAdapter;
import com.example.aplikasisurat.Controller.ApiClient;
import com.example.aplikasisurat.Controller.ApiInterface;
import com.example.aplikasisurat.Model_Surat.DataSuratItem;
import com.example.aplikasisurat.Model_Surat.Surat;
import com.example.aplikasisurat.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatActivity extends AppCompatActivity {



    ApiInterface mApiInterface;
    private RecyclerView mRecycleView;
    private SuratAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static LihatActivity ma;
    private FloatingActionButton fabAdd;
    private ProgressBar progressBar;
    private List<DataSuratItem> listSurat =new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat);



        mRecycleView = findViewById(R.id.rv_surat);
        progressBar = findViewById(R.id.progressbar);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        ma=this;
        refresh();

        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(LihatActivity.this,InputActivity.class));
            }
        });
    }



    public void refresh() {
        progressBar.setVisibility(View.VISIBLE);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Surat> SuratCall = mApiInterface.getSurat();
        SuratCall.enqueue(new Callback<Surat>() {
            @Override
            public void onResponse(Call<Surat> call, Response<Surat> response) {
                response.body().getMessage();

                Toast.makeText(LihatActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                listSurat = response.body().getDataSurat();

                mAdapter = new SuratAdapter(LihatActivity.this, listSurat);
                mRecycleView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Surat> call, Throwable t) {
                Toast.makeText(LihatActivity.this, "Gagal Konek Server", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<DataSuratItem> itemFilter = new ArrayList<>();
                for (DataSuratItem dataSuratItem : listSurat){
                    String judul = dataSuratItem.getJudul().toLowerCase();
                    if (judul.contains(newText)) {
                        itemFilter.add(dataSuratItem);
                    }
                }
                mAdapter.setFilter(itemFilter);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    //            @Override
//            public void onResponse(Call<GetSurat1> call, Response<Surat>
//                    response) {
//                if (response.body() != null) {
//                    Log.d("Lihat Activity", "data null");
//                    List<Surat1> SuratList = response.body().getDataSurat();
//                    SuratList = response.body().getDataSurat();
//                    Log.d ("Retrofit Get", "Jumlah data Surat:" + String.valueOf(SuratList.size()));
//                    mAdapter = new SuratAdapter(LihatActivity.this, SuratList);
//                    mRecycleView.setAdapter(mAdapter);
//                    mRecycleView.setLayoutManager(mLayoutManager);
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<Surat> call, Throwable t) {
//                Toast.makeText(LihatActivity.this, "Gagal Konek : "+t.getMessage(), Toast.LENGTH_SHORT).show();
////                Log.e("Retrofit Get", t.toString());
//           k Versi Android Tuk Minta Izin
//    private void requestPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
//        } else {
//            saveImageUpload();
//        }
//    }
    }
//        Call<GetSurat> SuratCall = mApiInterface.getSurat();
//        SuratCall.enqueue(new Callback<GetSurat>() {
//            @Override
//            public void onResponse(Call<GetSurat> call, Response<GetSurat> response) {
//                List<Surat> SuratList = response.body().getListDataSurat();
//                Log.d("Retrofit Get ", "Jumlah data Surat :" +
//                        String.valueOf(SuratList.size()));
//                mAdapter = new SuratAdapter(SuratList);
//                mRecycleView.setAdapter(mAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<GetSurat> call, Throwable t) {
//                Log.e("Retrofit Get", t.toString());
//            }
//        });



