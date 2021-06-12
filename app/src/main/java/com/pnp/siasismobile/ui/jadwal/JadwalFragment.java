package com.pnp.siasismobile.ui.jadwal;

import android.content.Context;
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
import com.pnp.siasismobile.LoginActivity;
import com.pnp.siasismobile.MainActivity;
import com.pnp.siasismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;

public class JadwalFragment extends Fragment {
    public JadwalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jadwal, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        Intent intentjadwal = mainActivity.getIntent();
        TextView txtkelas = view.findViewById(R.id.txtkelas);
        TextView txtsemester = view.findViewById(R.id.txtsemester);
        ImageView imgjadwal = view.findViewById(R.id.imgjadwal);

        txtkelas.setText((String)intentjadwal.getSerializableExtra("kelas"));
        txtsemester.setText((String)intentjadwal.getSerializableExtra("semester"));
        String txtconten = (String)intentjadwal.getSerializableExtra("jadwal");

        String urlGambar = "http://192.168.56.1/adm_siasis/admin/jadwal/"+txtconten;
        if (txtconten.equals("")){
            imgjadwal.setImageResource(R.drawable.jadwal);
        }else {
            Glide.with(getActivity())
                    .load(urlGambar)
                    .into(imgjadwal);
        }

        // Inflate the layout for this fragment
        return view;
    }
}