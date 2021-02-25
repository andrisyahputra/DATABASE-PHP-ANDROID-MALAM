package com.android;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class UASTIMALAMActivity  extends Activity implements OnClickListener {
Dosen Dosen = new Dosen();
 TableLayout tabelDosen;

 Button buttonTambahDosen;
 ArrayList<Button> buttonEdit = new ArrayList<Button>();
 ArrayList<Button> buttonDelete = new ArrayList<Button>();

 JSONArray arrayDosen;


 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.main);

  tabelDosen = (TableLayout) findViewById(R.id.tableDosen);
  buttonTambahDosen = (Button) findViewById(R.id.buttonTambahDosen);
  buttonTambahDosen.setOnClickListener(this);

  TableRow barisTabel = new TableRow(this);
  barisTabel.setBackgroundColor(Color.RED);

  TextView viewHeaderNIDN = new TextView(this);
  TextView viewHeaderNama_Dosen = new TextView(this);
  TextView viewHeaderPend_Akhir = new TextView(this);
  TextView viewHeaderAction = new TextView(this);

  viewHeaderNIDN.setText("NIDN");  
  viewHeaderNama_Dosen.setText("Nama Dosen");
  viewHeaderPend_Akhir.setText("Pend Akhir");
  viewHeaderAction.setText("Action");

  viewHeaderNIDN.setPadding(5, 1, 5, 1);
  viewHeaderNama_Dosen.setPadding(5, 1, 5, 1);
  viewHeaderPend_Akhir.setPadding(5, 1, 5, 1);
  viewHeaderAction.setPadding(5, 1, 5, 1);

  barisTabel.addView(viewHeaderNIDN);
  barisTabel.addView(viewHeaderNama_Dosen);
  barisTabel.addView(viewHeaderPend_Akhir);
  barisTabel.addView(viewHeaderAction);

  tabelDosen.addView(barisTabel, new TableLayout.LayoutParams());

  try {

	  arrayDosen = new JSONArray(Dosen.tampilDosen());

   for (int i = 0; i < arrayDosen.length(); i++) {
    JSONObject jsonChildNode = arrayDosen.getJSONObject(i);
    String nidn = jsonChildNode.optString("NIDN");
    String nama_dosen = jsonChildNode.optString("Nama_Dosen");
    String pend_akhir = jsonChildNode.optString("Pend_Terakhir");
    String id = jsonChildNode.optString("id");

    System.out.println("NIDN :" + nidn);
    System.out.println("Nama Dosen :" + nama_dosen);
    System.out.println("Pendikan akhir :" + pend_akhir);

    barisTabel = new TableRow(this);

    if (i % 2 == 0) {
    	barisTabel.setBackgroundColor(Color.rgb(220,20,60));;
    }

    TextView viewNidn = new TextView(this);
    viewNidn.setText(nidn);
    viewNidn.setPadding(5, 1, 5, 1);
    barisTabel.addView(viewNidn);
    
    TextView viewNamaDosen = new TextView(this);
    viewNamaDosen.setText(nama_dosen);
    viewNamaDosen.setPadding(5, 1, 5, 1);
    barisTabel.addView(viewNamaDosen);

    TextView viewPend_Akhir = new TextView(this);
    viewPend_Akhir.setText(pend_akhir);
    viewPend_Akhir.setPadding(5, 1, 5, 1);
    barisTabel.addView(viewPend_Akhir);

    buttonEdit.add(i, new Button(this));
    buttonEdit.get(i).setId(Integer.parseInt(id));
    buttonEdit.get(i).setTag("Edit");
    buttonEdit.get(i).setText("Edit");
    buttonEdit.get(i).setOnClickListener(this);
    barisTabel.addView(buttonEdit.get(i));

    buttonDelete.add(i, new Button(this));
    buttonDelete.get(i).setId(Integer.parseInt(id));
    buttonDelete.get(i).setTag("Delete");
    buttonDelete.get(i).setText("Delete");
    buttonDelete.get(i).setOnClickListener(this);
    barisTabel.addView(buttonDelete.get(i));

    tabelDosen.addView(barisTabel, new TableLayout.LayoutParams());
   }
  } catch (JSONException e) {
   e.printStackTrace();
  }
 }

 public void onClick(View view) {

  if (view.getId() == R.id.buttonTambahDosen) {
   // Toast.makeText(MainActivity.this, "Button Tambah Data",
   // Toast.LENGTH_SHORT).show();

   tambahDosen();

  } else {
   /*
    * Melakukan pengecekan pada data array, agar sesuai dengan index
    * masing-masing button
    */
   for (int i = 0; i < buttonEdit.size(); i++) {

    /* jika yang diklik adalah button edit */
    if (view.getId() == buttonEdit.get(i).getId() && view.getTag().toString().trim().equals("Edit")) {
     // Toast.makeText(MainActivity.this, "Edit : " +
     // buttonEdit.get(i).getId(), Toast.LENGTH_SHORT).show();
     int id = buttonEdit.get(i).getId();
     getDataByID(id);

    } /* jika yang diklik adalah button delete */
    else if (view.getId() == buttonDelete.get(i).getId() && view.getTag().toString().trim().equals("Delete")) {
     // Toast.makeText(MainActivity.this, "Delete : " +
     // buttonDelete.get(i).getId(), Toast.LENGTH_SHORT).show();
     int id = buttonDelete.get(i).getId();
     deleteDosen(id);

    }
   }
  }
 }

 public void deleteDosen(int id) {
	 Dosen.deleteDosen(id);

  /* restart acrtivity */
  finish();
  startActivity(getIntent());

 }

 public void getDataByID(int id) {

  String NIDNEdit = null, nama_dosenEdit = null, Pend_AkhirEdit = null;
  JSONArray arrayPersonal;

  try {

   arrayPersonal = new JSONArray(Dosen.getDosenById(id));

   for (int i = 0; i < arrayPersonal.length(); i++) {
    JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
    NIDNEdit = jsonChildNode.optString("NIDN");
    nama_dosenEdit = jsonChildNode.optString("Nama_Dosen");
    Pend_AkhirEdit = jsonChildNode.optString("Pend_Terakhir");
   }
  } catch (JSONException e) {
   e.printStackTrace();
  }

  LinearLayout layoutInput = new LinearLayout(this);
  layoutInput.setOrientation(LinearLayout.VERTICAL);

  // buat id tersembunyi di alertbuilder
  final TextView viewId = new TextView(this);
  viewId.setText(String.valueOf(id));
  viewId.setTextColor(Color.TRANSPARENT);
  layoutInput.addView(viewId);

  final EditText EditNidn = new EditText(this);
  EditNidn.setText(NIDNEdit);
  layoutInput.addView(EditNidn);
  
  final EditText editNamaDosen = new EditText(this);
  editNamaDosen.setText(nama_dosenEdit);
  layoutInput.addView(editNamaDosen);

  final EditText editPend_Akhir = new EditText(this);
  editPend_Akhir.setText(Pend_AkhirEdit);
  layoutInput.addView(editPend_Akhir);

  AlertDialog.Builder builderEditDosen = new AlertDialog.Builder(this);
  builderEditDosen.setIcon(R.drawable.ic_launcher);
  builderEditDosen.setTitle("Update Dosen");
  builderEditDosen.setView(layoutInput);
  builderEditDosen.setPositiveButton("Update", new DialogInterface.OnClickListener() {

	  
   public void onClick(DialogInterface dialog, int which) {
	String nidn = EditNidn.getText().toString();
    String nama_dosen = editNamaDosen.getText().toString();
    String pend_akhir = editPend_Akhir.getText().toString();

    System.out.println("NIDN : " + nidn + " Nama Dosen : " + nama_dosen + "Pendidikan terakhir : " + pend_akhir);

    String laporan = Dosen.updateDosen(viewId.getText().toString(), 
    		EditNidn.getText().toString(),
    		editNamaDosen.getText().toString(),
    		editPend_Akhir.getText().toString());

    Toast.makeText(UASTIMALAMActivity.this, laporan, Toast.LENGTH_SHORT).show();

    /* restart acrtivity */
    finish();
    startActivity(getIntent());
   }

  });

  builderEditDosen.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

   public void onClick(DialogInterface dialog, int which) {
    dialog.cancel();
   }
  });
  builderEditDosen.show();

 }

 public void tambahDosen() {
  /* layout akan ditampilkan pada AlertDialog */
  LinearLayout layoutInput = new LinearLayout(this);
  layoutInput.setOrientation(LinearLayout.VERTICAL);

  final EditText EditNIDN = new EditText(this);
  EditNIDN.setHint("NIDN");
  layoutInput.addView(EditNIDN);
  
  final EditText editNamaDosen = new EditText(this);
  editNamaDosen.setHint("Nama Dosen");
  layoutInput.addView(editNamaDosen);

  final EditText editPend_Akhir = new EditText(this);
  editPend_Akhir.setHint("Pendidakan Terakhir");
  layoutInput.addView(editPend_Akhir);

  AlertDialog.Builder builderEditDosen = new AlertDialog.Builder(this);
  builderEditDosen.setIcon(R.drawable.ic_launcher);
  builderEditDosen.setTitle("Insert Dosen");
  builderEditDosen.setView(layoutInput);
  builderEditDosen.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
  
	  
   public void onClick(DialogInterface dialog, int which) {
	String nidn = EditNIDN.getText().toString();
    String nama_dosen = editNamaDosen.getText().toString();
    String pend_akhir = editPend_Akhir.getText().toString();

    System.out.println("NIDN : " + nidn + " Nama Dosen : " + nama_dosen + " Pendidikan Akhir : " + pend_akhir);

    String laporan = Dosen.inserDosen(nidn, nama_dosen, pend_akhir);

    Toast.makeText(UASTIMALAMActivity.this, laporan, Toast.LENGTH_SHORT).show();

    /* restart acrtivity */
    finish();
    startActivity(getIntent());
   }

  });

  builderEditDosen.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

	  
   public void onClick(DialogInterface dialog, int which) {
    dialog.cancel();
   }
  });
  builderEditDosen.show();
 }
}