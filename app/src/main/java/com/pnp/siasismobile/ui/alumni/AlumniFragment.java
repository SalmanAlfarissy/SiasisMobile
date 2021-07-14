package com.pnp.siasismobile.ui.alumni;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pnp.siasismobile.R;
import com.pnp.siasismobile.adapter.AlumniAdapter;
import com.pnp.siasismobile.model.Alumni;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AlumniFragment extends Fragment {
    final String URL_ALUMNI = "https://siasis-mobile.000webhostapp.com/alumni_siswa.php";
    RecyclerView rvalumni;
    List<Alumni> alumniList;


    public AlumniFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alumni, container, false);
        rvalumni = view.findViewById(R.id.rvalumni);
        rvalumni.setHasFixedSize(true);
        rvalumni.setLayoutManager(new LinearLayoutManager(getActivity()));
        alumniList = new ArrayList<>();
        alumni();


        // Inflate the layout for this fragment
        return view;
    }

    private void alumni() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_ALUMNI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject alumni = array.getJSONObject(i);
                        alumniList.add(new Alumni(
                                alumni.getString("id_alumni"),
                                alumni.getString("nama"),
                                alumni.getString("angkatan"),
                                alumni.getString("pekerjaan"),
                                alumni.getString("alamat"),
                                alumni.getString("foto")
                        ));
                    }
                    AlumniAdapter adapter =
                            new AlumniAdapter(getActivity(), alumniList);
                    rvalumni.setAdapter(adapter);
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }
}