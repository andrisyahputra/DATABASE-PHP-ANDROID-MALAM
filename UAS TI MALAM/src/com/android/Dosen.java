package com.android;

public class Dosen extends Koneksi {
	 String URL = "http://10.0.2.2/Android_DB_Kampus/server.php";
	 String url = "";
	 String response = "";

	 public String tampilDosen() {
	  try {
	   url = URL + "?operasi=view";
	   System.out.println("URL Tampil dosen: " + url);
	   response = call(url);
	  } catch (Exception e) {
	  }
	  return response;
	 }

	 public String inserDosen(String nidn , String nama_dosen, String pend_akhir ) {
	  try {
	   url = URL + "?operasi=insert&NIDN="+ nidn + "&Nama_Dosen=" + nama_dosen + "&Pend_Terakhir=" + pend_akhir;
	   System.out.println("URL Insert Dosen : " + url);
	   response = call(url);
	  } catch (Exception e) {
	  }
	  return response;
	 }

	 public String getDosenById(int id) {
	  try {
	   url = URL + "?operasi=get_dosen_by_id&id=" + id;
	   System.out.println("URL Insert Dosen : " + url);
	   response = call(url);
	  } catch (Exception e) {
	  }
	  return response;
	 }

	 public String updateDosen(String id, String nidn , String nama_dosen, String pend_akhir) {
	  try {
	   url = URL + "?operasi=update&id=" + id + "&NIDN=" + nidn + "&Nama_Dosen=" + nama_dosen + "&Pend_Terakhir=" + pend_akhir;
	   System.out.println("URL Insert Dosen : " + url);
	   response = call(url);
	  } catch (Exception e) {
	  }
	  return response;
	 }

	 public String deleteDosen(int id) {
	  try {
	   url = URL + "?operasi=delete&id=" + id;
	   System.out.println("URL Delete Dosen: " + url);
	   response = call(url);
	  } catch (Exception e) {
	  }
	  return response;
	 }

	}
