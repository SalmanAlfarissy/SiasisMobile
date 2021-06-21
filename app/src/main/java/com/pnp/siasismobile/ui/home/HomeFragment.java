package com.pnp.siasismobile.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.pnp.siasismobile.MainActivity;
import com.pnp.siasismobile.R;
import com.pnp.siasismobile.adapter.EventAdapter;
import com.pnp.siasismobile.model.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    final String URL_EVENT = "http://192.168.56.1/adm_siasis/backend/event_siswa.php";
    RecyclerView recyclerview;
    List<Event> eventList;
    ImageSlider imageSlider;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = view.findViewById(R.id.slider);
        recyclerview = view.findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventList = new ArrayList<>();
        event();

        MainActivity mainActivity = (MainActivity) getActivity();
        Intent intentcontent = mainActivity.getIntent();

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("http://192.168.56.1/adm_siasis/admin/event/"+(String)intentcontent.getSerializableExtra("content1"),""+(String)intentcontent.getSerializableExtra("title1")));
        slideModels.add(new SlideModel("http://192.168.56.1/adm_siasis/admin/event/"+(String)intentcontent.getSerializableExtra("content2"),""+(String)intentcontent.getSerializableExtra("title2")));
        slideModels.add(new SlideModel("http://192.168.56.1/adm_siasis/admin/event/"+(String)intentcontent.getSerializableExtra("content3"),""+(String)intentcontent.getSerializableExtra("title3")));
        imageSlider.setImageList(slideModels,true);
        // Inflate the layout for this fragment
        return view;
    }

    private void event() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_EVENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject event = array.getJSONObject(i);
                        eventList.add(new Event(
                                event.getString("id_info"),
                                event.getString("nama_event"),
                                event.getString("gambar_event"),
                                event.getString("deskripsi")
                        ));
                    }
                    EventAdapter adapter =
                            new EventAdapter(getActivity(), eventList);
                    recyclerview.setAdapter(adapter);
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