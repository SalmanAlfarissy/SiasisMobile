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
    final String URL_SIGNIN = "http://192.168.56.1/adm_siasis/backend/login_siswa.php";
    EditText txtuserid;
    EditText txtpasswd;
    CheckBox psshow;
    Context context;
    CardView btnLogin;
    String idsiswa,passwd;
    String user,pass;
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

        stringRequest = new StringRequest(Request.Method.GET, URL_SIGNIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("siswa");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("nis_siswa", json.getString("nis"));
                        map.put("pass", json.getString("password"));
                        list_data.add(map);
                    }
                    user = list_data.get(0).get("nis_siswa");
                    pass = list_data.get(0).get("pass");


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
                String encrypt = md5Java(passwd);

                if (idsiswa.equals(user)&&encrypt.equals(pass)){
                    Intent intenmenu = new Intent(LoginActivity.this,MainActivity.class);
//                    intenmenu.putExtra("name",list_data.get(0).get("nama"));
//                    intenmenu.putExtra("iduser",list_data.get(0).get("id"));
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
