package com.pnp.siasismobile.ui.raport;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.pnp.siasismobile.LoginActivity;
import com.pnp.siasismobile.MainActivity;
import com.pnp.siasismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class RaportFragment extends Fragment {
    final String URL_RAPORT = "http://192.168.56.1/adm_siasis/backend/raport_siswa.php";
    private static int REQUEST_CODE = 100;
    Context context;
    String user,idsiswa,semester;
    int cek;
    RequestQueue requestQueue;
    private StringRequest stringRequest;
    ArrayList<HashMap<String,String>> list_data,list_data2;
    Spinner spsemester;
    ImageButton btnsearch;
    ImageView imgraport;
    CardView btnsave;
    OutputStream outputStream;




    public RaportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_raport, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        Intent intentraport = mainActivity.getIntent();

        context = getActivity();
        requestQueue = Volley.newRequestQueue(context);
        list_data = new ArrayList<HashMap<String, String>>();
        list_data2 = new ArrayList<HashMap<String, String>>();
        spsemester = view.findViewById(R.id.spsemester);
        btnsearch = view.findViewById(R.id.btnsearch);
        imgraport = view.findViewById(R.id.imgraport);
        btnsave = view.findViewById(R.id.btnsave);

        idsiswa=(String)intentraport.getSerializableExtra("nis");
//        Select id_siswa,nis,nama_sis,rapor,rapor.semester from siswa join rapor using(id_siswa);
        rapot();


        // Inflate the layout for this fragment
        return view;
    }

    private void rapot() {
        stringRequest = new StringRequest(Request.Method.GET, URL_RAPORT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("raport");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("nis", json.getString("nis"));
                        map.put("rapor", json.getString("rapor"));
                        map.put("semester", json.getString("semester"));
                        list_data.add(map);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list_data.size(); i++) {
                    user = list_data.get(i).get("nis");
                    if (idsiswa.equals(user)) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("nis",list_data.get(i).get("nis"));
                        map.put("rapor",list_data.get(i).get("rapor"));
                        map.put("semester",list_data.get(i).get("semester"));
                        list_data2.add(map);
                    }
                }
                for (int s = 0; s < list_data2.size(); s++){
                    semester = list_data2.get(s).get("semester");
                    if (spsemester.getSelectedItem().equals(semester)){
                        cek = s;
                    }
                }

                String txtconten = list_data2.get(cek).get("rapor");
                String txtsemester = list_data2.get(cek).get("semester");
                String urlGambar = "http://192.168.56.1/adm_siasis/guru/raport/"+txtconten;
                if (spsemester.getSelectedItem().equals(txtsemester)){
                    Glide.with(getActivity())
                            .load(urlGambar)
                            .into(imgraport);
                }else {
                    imgraport.setImageResource(R.drawable.siasis);
                }


            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    saveImage();
                }else {
                    askPermission();
                }
            }
        });

    }

    private void askPermission() {
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                saveImage();
            }else {
                Toast.makeText(getActivity(),"Please provide the required permissions",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void saveImage() {
        File dir = new File(Environment.getExternalStorageDirectory(),"Siasis Mobile");

        if (!dir.exists()){
            dir.mkdir();
        }

        BitmapDrawable drawable = (BitmapDrawable) imgraport.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File file = new File(dir,System.currentTimeMillis()+".jpg");
        try {
            outputStream = new FileOutputStream(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();

        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        Toast.makeText(getActivity(),"Successfuly Save",Toast.LENGTH_SHORT).show();

        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}