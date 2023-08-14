/*
 *  Aplikasi Surat
 *     Surat.java
 *     Created by Rifan Kurniawan on 14/8/2022
 *     email 	    : cah2701@gmail.com
 *     Copyright © 2022 Rifan Kurniawan. All rights reserved.
 */

package com.example.aplikasisurat.Model_Surat;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Surat{

	@SerializedName("data_surat")
	private List<DataSuratItem> dataSurat;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setDataSurat(List<DataSuratItem> dataSurat){
		this.dataSurat = dataSurat;
	}

	public List<DataSuratItem> getDataSurat(){
		return dataSurat;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}