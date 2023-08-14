/*
 *  Aplikasi Surat
 *     Login.java
 *     Created by Rifan Kurniawan on 14/8/2022
 *     email 	    : cah2701@gmail.com
 *     Copyright © 2022 Rifan Kurniawan. All rights reserved.
 */

package com.example.aplikasisurat.Model_Login;

import com.google.gson.annotations.SerializedName;

public class Login{

	@SerializedName("data_login")
	private DataLogin dataLogin;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setDataLogin(DataLogin dataLogin){
		this.dataLogin = dataLogin;
	}

	public DataLogin getDataLogin(){
		return dataLogin;
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