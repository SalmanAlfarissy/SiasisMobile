package com.pnp.siasismobile.ui.home;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.pnp.siasismobile.R;

public class DetailHomeFragment extends AppCompatActivity {
    TextView title_event,isievent;
    ImageView imgevent;
    Toolbar home_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_home);

        imgevent = findViewById(R.id.imgevent);
        title_event = findViewById(R.id.title_event);
        isievent = findViewById(R.id.isievent);
        home_toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(home_toolbar);
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            title_event.setText(bundle.getString("nama_event"));
            isievent.setText(bundle.getString("deskripsi"));
            Glide.with(this)
                    .load("https://siasis-mobile.000webhostapp.com/admin/event/"+bundle.getString("gambar_event"))
                    .into(imgevent);
        }

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
}
