package com.pnp.siasismobile.ui.home;

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
import com.pnp.siasismobile.R;
import com.pnp.siasismobile.adapter.EventAdapter;
import com.pnp.siasismobile.model.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment{


    RecyclerView recyclerview;
    List<Event> eventList;
    ImageSlider imageSlider;
    RequestQueue requestQueue;
    private StringRequest stringRequest1;
    String slide1,slide2,slide3,title1,title2,title3;

    ArrayList<HashMap<String,String>> list_data;

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
        requestQueue = Volley.newRequestQueue(getActivity());
        list_data = new ArrayList<HashMap<String, String>>();
        eventList = new ArrayList<>();

        EventSlider();
        event();



        // Inflate the layout for this fragment
        return view;
    }

    private void EventSlider() {
        final String URL_SLIDER = "https://siasis-mobile.000webhostapp.com/event_slider.php";
        stringRequest1 = new StringRequest(Request.Method.GET, URL_SLIDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<SlideModel> slideModels = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("slider");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id_info", json.getString("id_info"));
                        map.put("id_adm", json.getString("id_adm"));
                        map.put("nama_event", json.getString("nama_event"));
                        map.put("gambar_event", json.getString("gambar_event"));
                        map.put("tgl_post", json.getString("tgl_post"));
                        map.put("deskripsi", json.getString("deskripsi"));
                        list_data.add(map);

                        slideModels.add(new SlideModel("https://siasis-mobile.000webhostapp.com/admin/event/"+list_data.get(a).get("gambar_event"),""+list_data.get(a).get("nama_event")));
                        imageSlider.setImageList(slideModels,true);

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
        requestQueue.add(stringRequest1);
    }


    private void event() {
        final String URL_EVENT = "https://siasis-mobile.000webhostapp.com/event_siswa.php";
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