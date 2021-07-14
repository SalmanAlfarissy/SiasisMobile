package com.pnp.siasismobile.ui.spp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pnp.siasismobile.MainActivity;
import com.pnp.siasismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class SppFragment extends Fragment {
    TextView txtbulan,txtpbm,txtpenunjang,txtno,txtjml1,txtjml2,txttotal,norek,statuspembayaran;
    Button btnbayar;
    RequestQueue requestQueue;
    String nis;
    MainActivity mainActivity;
    Intent data;
    private StringRequest stringRequest;
    ArrayList<HashMap<String,String>> list_data;

    final String URL_PEMBAYARAN = "https://siasis-mobile.000webhostapp.com/pembayaran_siswa.php";

    public SppFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_spp, container, false);
        txtno=view.findViewById(R.id.txtno);
        statuspembayaran=view.findViewById(R.id.status);
        txtbulan=view.findViewById(R.id.txtbln);
        txtpbm = view.findViewById(R.id.txtpbm);
        txtpenunjang=view.findViewById(R.id.txtpenunjang);
        txtjml1=view.findViewById(R.id.txtjml1);
        txtjml2=view.findViewById(R.id.txtjml2);
        txttotal=view.findViewById(R.id.txttotal);
        btnbayar=view.findViewById(R.id.btnbayar);
        norek = view.findViewById(R.id.norek);

        requestQueue = Volley.newRequestQueue(getActivity());
        list_data = new ArrayList<HashMap<String, String>>();

        mainActivity = (MainActivity) getActivity();
        data = mainActivity.getIntent();
        nis = (String)data.getSerializableExtra("nis");

        status();
        listpembayaran();

        // Inflate the layout for this fragment
        return view;
    }

    private void status() {
        stringRequest = new StringRequest(Request.Method.GET, URL_PEMBAYARAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("pembayaran");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("nis", json.getString("nis"));
                        map.put("status", json.getString("status"));

                        list_data.add(map);

                        if (list_data.get(a).get("nis").equals(nis)){
                            statuspembayaran.setText(list_data.get(a).get("status"));
                            if (list_data.get(a).get("status").equalsIgnoreCase("Lunas")){
                                btnbayar.setEnabled(false);

                            }else{
                                bayar();
                            }

                            break;

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }

    private void bayar() {
        btnbayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = (String)data.getSerializableExtra("nama_sis");
                String kelas = (String)data.getSerializableExtra("kelas");
                String semester = (String)data.getSerializableExtra("semester");

                String pesan = "Nama : "+nama+"\nNIS : "+nis+"\nKelas : "+kelas+"\nSemester : "+semester;

                pesan.replace(" ", "%20");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://wa.me/6282285032741?text="+pesan));
                startActivity(intent);
            }
        });
    }

    private void listpembayaran() {
        txtno.setText("1.\n2.\n3.\n4.\n5.\n6.\n7.\n8.\n9.\n10.\n11.\n12.\n");
        txtbulan.setText("Juli\nAgustus\nSeptember\nOktober\nNovember\nDesember\nJanuari\nFebruari\nMaret\nApril\nMei\nJuni\n");
        txtpbm.setText("85.000\n85.000\n85.000\n85.000\n85.000\n85.000\n85.000\n85.000\n85.000\n85.000\n85.000\n85.000\n");
        txtpenunjang.setText("\n30.000\n\n\n110.000\n\n60.000\n\n\n\n\n\n");
        txtjml1.setText("1.020.000");
        txtjml2.setText("200.000");
        txttotal.setText("1.220.000");
        norek.setText("NoRek(BNI) : xxx-xxx-x367-7\nNoRek(BRI) : xxx-xxx-x399-8" +
                "\nNoRek(Nagari) : xxx-xxx-x387-7\nNoRek(Mandiri) : xxx-xxx-x357-6");



    }
}