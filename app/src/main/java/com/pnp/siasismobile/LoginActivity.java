package com.pnp.siasismobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginActivity extends AppCompatActivity {
    final String URL_SIGNIN = "https://siasis-mobile.000webhostapp.com/login_siswa.php";
    EditText txtuserid;
    EditText txtpasswd;
    CheckBox psshow;
    Context context;
    CardView btnLogin;
    String idsiswa,passwd,encrypt;
    String user,pass;
    int cek;
    RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList<HashMap<String,String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;

        //Initializing views
        txtuserid =findViewById(R.id.txtuserid);
        txtpasswd =findViewById(R.id.txtpasswd);
        psshow =findViewById(R.id.psshow);

        btnLogin =findViewById(R.id.btnlogin);
        requestQueue = Volley.newRequestQueue(LoginActivity.this);

        list_data = new ArrayList<HashMap<String, String>>();

        psshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (psshow.isChecked()){
                    txtpasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    txtpasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        login();


    }


    private void login() {
        stringRequest = new StringRequest(Request.Method.GET, URL_SIGNIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("siswa");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id_siswa", json.getString("id_siswa"));
                        map.put("id_guru", json.getString("id_guru"));
                        map.put("id_adm", json.getString("id_adm"));
                        map.put("password", json.getString("password"));
                        map.put("nisn", json.getString("nisn"));
                        map.put("nis", json.getString("nis"));
                        map.put("nama_sis", json.getString("nama_sis"));
                        map.put("foto_sis", json.getString("foto_sis"));
                        map.put("ttl", json.getString("ttl"));
                        map.put("jenis_kelamin", json.getString("jenis_kelamin"));
                        map.put("agama", json.getString("agama"));
                        map.put("status_keluarga", json.getString("status_keluarga"));
                        map.put("anak_ke", json.getString("anak_ke"));
                        map.put("alamat", json.getString("alamat"));
                        map.put("no_hp", json.getString("no_hp"));
                        map.put("kelas", json.getString("kelas"));
                        map.put("semester", json.getString("semester"));
                        map.put("tahun_diterima", json.getString("tahun_diterima"));
                        map.put("semester_diterima", json.getString("semester_diterima"));
                        map.put("nama_sekolah_asal", json.getString("nama_sekolah_asal"));
                        map.put("alamat_sekolah_asal", json.getString("alamat_sekolah_asal"));
                        map.put("tahun_ijazah_sebelumnya", json.getString("tahun_ijazah_sebelumnya"));
                        map.put("nomor_ijazah_sebelumnya", json.getString("nomor_ijazah_sebelumnya"));
                        map.put("tahun_skhun_sebelumya", json.getString("tahun_skhun_sebelumya"));
                        map.put("nomor_skhun_sebelumnya", json.getString("nomor_skhun_sebelumnya"));
                        map.put("nama_ayah", json.getString("nama_ayah"));
                        map.put("nama_ibu", json.getString("nama_ibu"));
                        map.put("alamat_ortu", json.getString("alamat_ortu"));
                        map.put("pekerjaan_ayah", json.getString("pekerjaan_ayah"));
                        map.put("pekerjaan_ibu", json.getString("pekerjaan_ibu"));
                        map.put("nama_wali", json.getString("nama_wali"));
                        map.put("alamat_wali", json.getString("alamat_wali"));
                        map.put("no_hp_wali", json.getString("no_hp_wali"));
                        map.put("pekerjaan_wali", json.getString("pekerjaan_wali"));
                        map.put("cover", json.getString("cover"));
                        list_data.add(map);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idsiswa = txtuserid.getText().toString();
                passwd = txtpasswd.getText().toString();
                encrypt = md5Java(passwd);

                for (int i = 0; i < list_data.size(); i++) {
                    user = list_data.get(i).get("nis");
                    if (idsiswa.equals(user)) {
                        cek = i;
                        break;
                    }
                }

                user = list_data.get(cek).get("nis");
                pass = list_data.get(cek).get("password");


                if (idsiswa.equals(user)&&encrypt.equals(pass)){
                    Intent intenmenu = new Intent(LoginActivity.this,MainActivity.class);
                    intenmenu.putExtra("id_siswa",list_data.get(cek).get("id_siswa"));
                    intenmenu.putExtra("id_guru",list_data.get(cek).get("id_guru"));
                    intenmenu.putExtra("id_adm",list_data.get(cek).get("id_adm"));
                    intenmenu.putExtra("password",list_data.get(cek).get("password"));
                    intenmenu.putExtra("nisn",list_data.get(cek).get("nisn"));
                    intenmenu.putExtra("nis",list_data.get(cek).get("nis"));
                    intenmenu.putExtra("nama_sis",list_data.get(cek).get("nama_sis"));
                    intenmenu.putExtra("foto_sis",list_data.get(cek).get("foto_sis"));
                    intenmenu.putExtra("ttl",list_data.get(cek).get("ttl"));
                    intenmenu.putExtra("jenis_kelamin",list_data.get(cek).get("jenis_kelamin"));
                    intenmenu.putExtra("agama",list_data.get(cek).get("agama"));
                    intenmenu.putExtra("status_keluarga",list_data.get(cek).get("status_keluarga"));
                    intenmenu.putExtra("anak_ke",list_data.get(cek).get("anak_ke"));
                    intenmenu.putExtra("alamat",list_data.get(cek).get("alamat"));
                    intenmenu.putExtra("no_hp",list_data.get(cek).get("no_hp"));
                    intenmenu.putExtra("kelas",list_data.get(cek).get("kelas"));
                    intenmenu.putExtra("semester",list_data.get(cek).get("semester"));
                    intenmenu.putExtra("tahun_diterima",list_data.get(cek).get("tahun_diterima"));
                    intenmenu.putExtra("semester_diterima",list_data.get(cek).get("semester_diterima"));
                    intenmenu.putExtra("nama_sekolah_asal",list_data.get(cek).get("nama_sekolah_asal"));
                    intenmenu.putExtra("alamat_sekolah_asal",list_data.get(cek).get("alamat_sekolah_asal"));
                    intenmenu.putExtra("tahun_ijazah_sebelumnya",list_data.get(cek).get("tahun_ijazah_sebelumnya"));
                    intenmenu.putExtra("nomor_ijazah_sebelumnya",list_data.get(cek).get("nomor_ijazah_sebelumnya"));
                    intenmenu.putExtra("tahun_skhun_sebelumya",list_data.get(cek).get("tahun_skhun_sebelumya"));
                    intenmenu.putExtra("nomor_skhun_sebelumnya",list_data.get(cek).get("nomor_skhun_sebelumnya"));
                    intenmenu.putExtra("nama_ayah",list_data.get(cek).get("nama_ayah"));
                    intenmenu.putExtra("nama_ibu",list_data.get(cek).get("nama_ibu"));
                    intenmenu.putExtra("alamat_ortu",list_data.get(cek).get("alamat_ortu"));
                    intenmenu.putExtra("pekerjaan_ayah",list_data.get(cek).get("pekerjaan_ayah"));
                    intenmenu.putExtra("pekerjaan_ibu",list_data.get(cek).get("pekerjaan_ibu"));
                    intenmenu.putExtra("nama_wali",list_data.get(cek).get("nama_wali"));
                    intenmenu.putExtra("alamat_wali",list_data.get(cek).get("alamat_wali"));
                    intenmenu.putExtra("no_hp_wali",list_data.get(cek).get("no_hp_wali"));
                    intenmenu.putExtra("pekerjaan_wali",list_data.get(cek).get("pekerjaan_wali"));
                    intenmenu.putExtra("cover",list_data.get(cek).get("cover"));

                    Toast.makeText(LoginActivity.this,"Login Sukses",Toast.LENGTH_LONG).show();
                    startActivity(intenmenu);
                    ClearText();

                }else{
                    Toast.makeText(LoginActivity.this,"Login Gagal\n Please Try Again!!",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void ClearText() {
        txtuserid.setText("");
        txtpasswd.setText("");
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
