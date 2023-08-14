/*
 *  Aplikasi Surat
 *     DataLogin.java
 *     Created by Rifan Kurniawan on 14/8/2022
 *     email 	    : cah2701@gmail.com
 *     Copyright © 2022 Rifan Kurniawan. All rights reserved.
 */

package com.example.aplikasisurat.Model_Login;

import com.google.gson.annotations.SerializedName;

public class DataLogin{

	@SerializedName("nip")
	private String nip;

	@SerializedName("id")
	private String id;

	public void setNip(String nip){
		this.nip = nip;
	}

	public String getNip(){
		return nip;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}
}