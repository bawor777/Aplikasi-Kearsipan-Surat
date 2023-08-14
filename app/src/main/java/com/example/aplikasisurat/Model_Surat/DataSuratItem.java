/*
 *  Aplikasi Surat
 *     DataSuratItem.java
 *     Created by Rifan Kurniawan on 14/8/2022
 *     email 	    : cah2701@gmail.com
 *     Copyright © 2022 Rifan Kurniawan. All rights reserved.
 */

package com.example.aplikasisurat.Model_Surat;

import com.google.gson.annotations.SerializedName;

public class DataSuratItem{

	@SerializedName("foto")
	private String foto;

	@SerializedName("no_surat")
	private String noSurat;

	@SerializedName("kode_cabang")
	private String kode_cabang;

	@SerializedName("id")
	private String id;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("judul")
	private String judul;

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	public void setNoSurat(String noSurat){
		this.noSurat = noSurat;
	}

	public String getNoSurat(){
		return noSurat;
	}

	public void setKode_cabang(String kode_cabang){
		this.kode_cabang = kode_cabang;
	}

	public String getKode_cabang(){
		return kode_cabang;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setJudul(String judul){
		this.judul = judul;
	}

	public String getJudul(){
		return judul;
	}
}