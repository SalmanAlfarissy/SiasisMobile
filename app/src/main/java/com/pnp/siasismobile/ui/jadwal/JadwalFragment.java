package com.pnp.siasismobile.ui.jadwal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.pnp.siasismobile.MainActivity;
import com.pnp.siasismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JadwalFragment extends Fragment {
    final String URL_JADWAL = "https://siasis-mobile.000webhostapp.com/jadwal_siswa.php";
    String semester,kelas,nis,urlGambar,txtconten;
    int cek;
    TextView txtkelas,txtsemester;
    ImageView imgjadwal;
    RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList<HashMap<String,String>> list_data;

    public JadwalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jadwal, container, false);

        txtkelas = view.findViewById(R.id.txtkelas);
        txtsemester = view.findViewById(R.id.txtsemester);
        imgjadwal = view.findViewById(R.id.imgjadwal);
        requestQueue = Volley.newRequestQueue(getActivity());
        list_data = new ArrayList<HashMap<String, String>>();
        MainActivity mainActivity = (MainActivity) getActivity();
        Intent tekedata = mainActivity.getIntent();
        nis = (String) tekedata.getSerializableExtra("nis");

        jadwal();

        // Inflate the layout for this fragment
        return view;
    }

    private void jadwal() {
        stringRequest = new StringRequest(Request.Method.GET, URL_JADWAL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("kelas");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("nama_sis", json.getString("nama_sis"));
                        map.put("nis", json.getString("nis"));
                        map.put("kelas", json.getString("kelas"));
                        map.put("semester", json.getString("semester"));
                        map.put("jadwal", json.getString("jadwal"));
                        list_data.add(map);

                        if (list_data.get(a).get("nis").equals(nis)){
                            txtkelas.setText(list_data.get(a).get("kelas"));
                            txtsemester.setText(list_data.get(a).get("semester"));

                            txtconten = list_data.get(a).get("jadwal");
                            urlGambar = "https://siasis-mobile.000webhostapp.com/admin/jadwal/"+txtconten;
                            if (txtconten.equals("")){
                                imgjadwal.setImageResource(R.drawable.jadwal);
                            }else {
                                Glide.with(getActivity())
                                        .load(urlGambar)
                                        .into(imgjadwal);
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
}