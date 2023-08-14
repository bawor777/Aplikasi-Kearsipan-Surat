/*
 *  Aplikasi Surat
 *     SuratAdapter.java
 *     Created by Rifan Kurniawan on 14/8/2022
 *     email 	    : cah2701@gmail.com
 *     Copyright © 2022 Rifan Kurniawan. All rights reserved.
 */

package com.example.aplikasisurat.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aplikasisurat.Activity.UpdateActivity;
import com.example.aplikasisurat.Config;
import com.example.aplikasisurat.Model_Surat.DataSuratItem;
import com.example.aplikasisurat.R;

import java.util.ArrayList;
import java.util.List;

public class SuratAdapter extends RecyclerView.Adapter<SuratAdapter.HolderSurat>{
    private Context ctx;
    private List<DataSuratItem> listSurat;
//    private boolean idSurat;

//    ApiInterface mApiInterface;

    public SuratAdapter(Context ctx, List<DataSuratItem> listSurat) {
        this.ctx = ctx;
        this.listSurat = listSurat;
    }


    @Override
    public HolderSurat onCreateViewHolder( ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_surat,parent,false);
        HolderSurat holder = new HolderSurat(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(HolderSurat holder, @SuppressLint("RecyclerView") final int position) {
//        DataSuratItem ds = listSurat.get(position);
        holder.tvKodeCabang.setText(listSurat.get(position).getKode_cabang());
        holder.tvJudul.setText(listSurat.get(position).getJudul());
        holder.tvNoSurat.setText(listSurat.get(position).getNoSurat());
        holder.tvTanggal.setText(listSurat.get(position).getTanggal());

        Glide.with(holder.itemView.getContext())
                .load (Config.IMAGES_URL +listSurat.get(position).getFoto())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.imgFoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), UpdateActivity.class);
                mIntent.putExtra("id", listSurat.get(position).getId());
                mIntent.putExtra("kode_cabang", listSurat.get(position).getKode_cabang());
                mIntent.putExtra("judul", listSurat.get(position).getJudul());
                mIntent.putExtra("no_surat", listSurat.get(position).getNoSurat());
                mIntent.putExtra("tanggal", listSurat.get(position).getTanggal());
                mIntent.putExtra("foto", listSurat.get(position).getFoto());
                view.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return listSurat.size();
    }

    public void setFilter(ArrayList<DataSuratItem> filterModel) {
        listSurat = new ArrayList<>();
        listSurat.addAll(filterModel);
        notifyDataSetChanged();
    }


    public class HolderSurat extends RecyclerView.ViewHolder {
        public TextView tvKodeCabang, tvJudul, tvNoSurat, tvTanggal;
        public ImageView imgFoto;

        public HolderSurat(View itemView) {
            super(itemView);
            tvKodeCabang = itemView.findViewById(R.id.tv_item_kode_cabang);
            tvJudul = itemView.findViewById(R.id.tv_item_judul);
            tvNoSurat = itemView.findViewById(R.id.tv_item_noSurat);
            tvTanggal = itemView.findViewById(R.id.tv_item_tanggal);
            imgFoto = itemView.findViewById(R.id.img_item_photo);
        }

    }

}

//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
//                    dialogPesan.setMessage("Pilih Operaasi yang akan dilakukan");
//                    dialogPesan.setCancelable(true);
//
//                    idSurat = Boolean.parseBoolean((tvJudul.getText().toString()));
//
//                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int i) {
//                        deleteSurat();
//                        dialog.dismiss();
//                            ((LihatActivity)ctx).refresh();
//
//                        }
//                    });
//
//                    dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int i) {
//
//                        }
//                    });
//                    dialogPesan.show();
//                    return false;
//                }
//            });
//
//        }
//        private void deleteSurat (){
//        ApiInterface apiSurat = ApiClient.getClient().create(ApiInterface.class);
//            Call<Surat> hapusSurat = apiSurat.postDeleteSurat(idSurat);
//
//            hapusSurat.enqueue(new Callback<Surat>() {
//                @Override
//                public void onResponse(Call<Surat> call, Response<Surat> response) {
//                 boolean status = response.body().isStatus();
//                 String pesan = response.body().getMessage();
//
//                    Toast.makeText(ctx, "status : "+status+"pesan : "+pesan, Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onFailure(Call<Surat> call, Throwable t) {
//
//                    Toast.makeText(ctx, "Gagal konek "+t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            });
//        }
//    }
//}