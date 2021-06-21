package com.pnp.siasismobile.ui.biodata;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pnp.siasismobile.MainActivity;
import com.pnp.siasismobile.R;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateFragment extends AppCompatActivity {
    EditText edtnama,edtttl,edtagama,edtnohp,
            edtalamat,edtanakke,edtstatuskeluarga,edttahunditerima
            ,edtsemesterditerima,edtasalsekolah,edtalamatasalsekolah,edtpass
            ,edttahun_ijazah_sebelumnya,edtnomor_ijazah_sebelumnya,
            edttahun_skhun_sebelumya,edtnomor_skhun_sebelumnya,edtnama_ayah,
            edtnama_ibu,edtalamat_ortu,edtpekerjaan_ayah,edtpekerjaan_ibu,edtnama_wali,
            edtalamat_wali,edtno_hp_wali,edtpekerjaan_wali;
    String nama,ttl,jekel,agama,password,encrypt,nohp,alamat,nis,
            anakke,statuskeluarga,tahunditerima,semesterditerima,
            asalsekolah,alamatasalsekolah,tahun_ijazah_sebelumnya,
            nomor_ijazah_sebelumnya,tahun_skhun_sebelumya,
            nomor_skhun_sebelumnya,nama_ayah,nama_ibu,alamat_ortu,
            pekerjaan_ayah,pekerjaan_ibu,nama_wali,alamat_wali,no_hp_wali,pekerjaan_wali;

    CardView btnsimpan;
    Toolbar update_toolbar;
    RadioGroup rdgjekel;
    RadioButton rdbjekel;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;
    ArrayList<HashMap<String, String>> list_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update);
        update_toolbar = findViewById(R.id.update_toolbar);

        edtnama = findViewById(R.id.edtnama);
        edtpass = findViewById(R.id.edtpass);
        edtttl = findViewById(R.id.edtttl);
        edtagama = findViewById(R.id.edtagama);
        edtnohp = findViewById(R.id.edtnohp);
        edtalamat = findViewById(R.id.edtalamat);
        edtanakke = findViewById(R.id.edtanakke);
        edtstatuskeluarga = findViewById(R.id.edtstatuskeluarga);
        edttahunditerima = findViewById(R.id.edttahunditerima);
        edtsemesterditerima = findViewById(R.id.edtsemesterditerima);
        edtasalsekolah = findViewById(R.id.edtasalsekolah);
        edtalamatasalsekolah = findViewById(R.id.edtalamatasalsekolah);

        edttahun_ijazah_sebelumnya = findViewById(R.id.edttahun_ijazah_sebelumnya);
        edtnomor_ijazah_sebelumnya = findViewById(R.id.edtnomor_ijazah_sebelumnya);
        edttahun_skhun_sebelumya = findViewById(R.id.edttahun_skhun_sebelumya);
        edtnomor_skhun_sebelumnya = findViewById(R.id.edtnomor_skhun_sebelumnya);
        edtnama_ayah = findViewById(R.id.edtnama_ayah);
        edtnama_ibu = findViewById(R.id.edtnama_ibu);
        edtalamat_ortu = findViewById(R.id.edtalamat_ortu);
        edtpekerjaan_ayah = findViewById(R.id.edtpekerjaan_ayah);
        edtpekerjaan_ibu = findViewById(R.id.edtpekerjaan_ibu);
        edtnama_wali = findViewById(R.id.edtnama_wali);
        edtalamat_wali = findViewById(R.id.edtalamat_wali);
        edtno_hp_wali = findViewById(R.id.edtno_hp_wali);
        edtpekerjaan_wali = findViewById(R.id.edtpekerjaan_wali);
        rdgjekel = findViewById(R.id.rdgjekel);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        requestQueue = Volley.newRequestQueue(UpdateFragment.this);
        progressDialog = new ProgressDialog(UpdateFragment.this);
        list_data = new ArrayList<HashMap<String, String>>();

        btnsimpan = findViewById(R.id.btnsimpan);

        setSupportActionBar(update_toolbar);
        getSupportActionBar().setTitle("Biodata");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListSiswa();
        TglLahir();
        BtnSave();


    }

    private void BtnSave() {
        Intent intent = getIntent();
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Jekel();
                final String URL_UPDATE= "http://192.168.56.1/adm_siasis/backend/update_siswa.php";
                progressDialog.setMessage("silahkan tunggu,input data ke server sedang berlangsung");
                progressDialog.show();

                nis = (String)intent.getSerializableExtra("nis");
                password = edtpass.getText().toString().trim();
                encrypt = md5Java(password);
                nama = edtnama.getText().toString().trim();
                jekel = rdbjekel.getText().toString().trim();
                ttl = edtttl.getText().toString().trim();
                agama = edtagama.getText().toString().trim();
                statuskeluarga = edtstatuskeluarga.getText().toString().trim();
                anakke = edtanakke.getText().toString().trim();
                alamat = edtalamat.getText().toString().trim();
                nohp = edtnohp.getText().toString().trim();
                tahunditerima = edttahunditerima.getText().toString().trim();
                semesterditerima = edtsemesterditerima.getText().toString().trim();
                asalsekolah = edtasalsekolah.getText().toString().trim();
                alamatasalsekolah = edtalamatasalsekolah.getText().toString().trim();
                tahun_ijazah_sebelumnya = edttahun_ijazah_sebelumnya.getText().toString().trim();
                nomor_ijazah_sebelumnya = edtnomor_ijazah_sebelumnya.getText().toString().trim();
                tahun_skhun_sebelumya = edttahun_skhun_sebelumya.getText().toString().trim();
                nomor_skhun_sebelumnya = edtnomor_skhun_sebelumnya.getText().toString().trim();
                nama_ayah = edtnama_ayah.getText().toString().trim();
                nama_ibu = edtnama_ibu.getText().toString().trim();
                alamat_ortu = edtalamat_ortu.getText().toString().trim();
                pekerjaan_ayah = edtpekerjaan_ayah.getText().toString().trim();
                pekerjaan_ibu = edtpekerjaan_ibu.getText().toString().trim();
                nama_wali = edtnama_wali.getText().toString().trim();
                alamat_wali = edtalamat_wali.getText().toString().trim();
                no_hp_wali = edtno_hp_wali.getText().toString().trim();
                pekerjaan_wali = edtpekerjaan_wali.getText().toString().trim();
                clearData();


                StringRequest stringRequest = new StringRequest
                        (Request.Method.POST, URL_UPDATE, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String ServerResponse) {
                                progressDialog.dismiss();
                                Toast.makeText(UpdateFragment.this, "Data berhasil di simpan.", Toast.LENGTH_LONG).show();


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                progressDialog.dismiss();
                                Toast.makeText(UpdateFragment.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                            }
                        }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("nama_sis", nama);
                        params.put("password", encrypt);
                        params.put("ttl", ttl);
                        params.put("jenis_kelamin", jekel);
                        params.put("agama", agama);
                        params.put("no_hp", nohp);
                        params.put("alamat", alamat);
                        params.put("anak_ke", anakke);
                        params.put("status_keluarga", statuskeluarga);
                        params.put("tahun_diterima", tahunditerima);
                        params.put("semester_diterima", semesterditerima);
                        params.put("nama_sekolah_asal", asalsekolah);
                        params.put("alamat_sekolah_asal", alamatasalsekolah);
                        params.put("tahun_ijazah_sebelumnya", tahun_ijazah_sebelumnya);
                        params.put("nomor_ijazah_sebelumnya", nomor_ijazah_sebelumnya);
                        params.put("tahun_skhun_sebelumnya", tahun_skhun_sebelumya);
                        params.put("nomor_skhun_sebelumnya", nomor_skhun_sebelumnya);
                        params.put("nama_ayah", nama_ayah);
                        params.put("nama_ibu", nama_ibu);
                        params.put("alamat_ortu", alamat_ortu);
                        params.put("pekerjaan_ayah", pekerjaan_ayah);
                        params.put("pekerjaan_ibu", pekerjaan_ibu);
                        params.put("nama_wali", nama_wali);
                        params.put("alamat_wali", alamat_wali);
                        params.put("no_hp_wali", no_hp_wali);
                        params.put("pekerjaan_wali", pekerjaan_wali);
                        params.put("nis", nis);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    private void clearData() {
        edtpass.setText("");
        edtnama.setText("");
        edtttl.setText("");
        edtagama.setText("");
        edtstatuskeluarga.setText("");
        edtanakke.setText("");
        edtalamat.setText("");
        edtnohp.setText("");
        edttahunditerima.setText("");
        edtsemesterditerima.setText("");
        edtasalsekolah.setText("");
        edtalamatasalsekolah.setText("");
        edttahun_ijazah_sebelumnya.setText("");
        edtnomor_ijazah_sebelumnya.setText("");
        edttahun_skhun_sebelumya.setText("");
        edtnomor_skhun_sebelumnya.setText("");
        edtnama_ayah.setText("");
        edtnama_ibu.setText("");
        edtalamat_ortu.setText("");
        edtpekerjaan_ayah.setText("");
        edtpekerjaan_ibu.setText("");
        edtnama_wali.setText("");
        edtalamat_wali.setText("");
        edtno_hp_wali.setText("");
        edtpekerjaan_wali.setText("");

    }

    private void Jekel() {
        int select = rdgjekel.getCheckedRadioButtonId();
        rdbjekel=findViewById(select);
    }

    private void TglLahir() {
        edtttl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionDate();
            }
        });
    }

    private void ActionDate() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, month, dayOfMonth);
                edtttl.setText(dateFormatter.format(date.getTime()));

            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void ListSiswa() {
        Intent intentdata = getIntent();
        edtpass.setText((String)intentdata.getSerializableExtra("password"));
        edtnama.setText((String)intentdata.getSerializableExtra("nama_sis"));
//        (String)intentdata.getSerializableExtra("foto_sis"));
        edtttl.setText((String)intentdata.getSerializableExtra("ttl"));
//        (String)intentdata.getSerializableExtra("jenis_kelamin"));
        edtagama.setText((String)intentdata.getSerializableExtra("agama"));
        edtstatuskeluarga.setText((String)intentdata.getSerializableExtra("status_keluarga"));
        edtanakke.setText((String)intentdata.getSerializableExtra("anak_ke"));
        edtalamat.setText((String)intentdata.getSerializableExtra("alamat"));
        edtnohp.setText((String)intentdata.getSerializableExtra("no_hp"));
        edttahunditerima.setText((String)intentdata.getSerializableExtra("tahun_diterima"));
        edtsemesterditerima.setText((String)intentdata.getSerializableExtra("semester_diterima"));
        edtasalsekolah.setText((String)intentdata.getSerializableExtra("nama_sekolah_asal"));
        edtalamatasalsekolah.setText((String)intentdata.getSerializableExtra("alamat_sekolah_asal"));
        edttahun_ijazah_sebelumnya.setText((String)intentdata.getSerializableExtra("tahun_ijazah_sebelumnya"));
        edtnomor_ijazah_sebelumnya.setText((String)intentdata.getSerializableExtra("nomor_ijazah_sebelumnya"));
        edttahun_skhun_sebelumya.setText((String)intentdata.getSerializableExtra("tahun_skhun_sebelumya"));
        edtnomor_skhun_sebelumnya.setText((String)intentdata.getSerializableExtra("nomor_skhun_sebelumnya"));
        edtnama_ayah.setText((String)intentdata.getSerializableExtra("nama_ayah"));
        edtnama_ibu.setText((String)intentdata.getSerializableExtra("nama_ibu"));
        edtalamat_ortu.setText((String)intentdata.getSerializableExtra("alamat_ortu"));
        edtpekerjaan_ayah.setText((String)intentdata.getSerializableExtra("pekerjaan_ayah"));
        edtpekerjaan_ibu.setText((String)intentdata.getSerializableExtra("pekerjaan_ibu"));
        edtnama_wali.setText((String)intentdata.getSerializableExtra("nama_wali"));
        edtalamat_wali.setText((String)intentdata.getSerializableExtra("alamat_wali"));
        edtno_hp_wali.setText((String)intentdata.getSerializableExtra("no_hp_wali"));
        edtpekerjaan_wali.setText((String)intentdata.getSerializableExtra("pekerjaan_wali"));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static String md5Java(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            //merubah byte array ke dalam String Hexadecimal
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash)
            {
                sb.append(String.format("%02x", b&0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex)
        {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;
    }
}