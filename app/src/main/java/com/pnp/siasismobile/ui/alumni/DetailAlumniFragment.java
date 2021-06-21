package com.pnp.siasismobile.ui.alumni;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.pnp.siasismobile.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class DetailAlumniFragment extends AppCompatActivity {
    CircleImageView foto_alumni;
    TextView alumni_name,alumni_angkatan,alumni_pekerjaan,alumni_alamat;
    Toolbar alumni_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_alumni);

        foto_alumni = findViewById(R.id.foto_alumni);
        alumni_name = findViewById(R.id.alumni_name);
        alumni_angkatan = findViewById(R.id.alumni_angkata);
        alumni_pekerjaan = findViewById(R.id.alumni_pekerjaan);
        alumni_alamat = findViewById(R.id.alumni_alamat);
        alumni_toolbar = findViewById(R.id.alumni_toolbar);
        setSupportActionBar(alumni_toolbar);
        getSupportActionBar().setTitle("Data Alumni");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            alumni_name.setText(bundle.getString("nama"));
            alumni_angkatan.setText(bundle.getString("angkatan"));
            alumni_pekerjaan.setText(bundle.getString("pekerjaan"));
            alumni_alamat.setText(bundle.getString("alamat"));
            Glide.with(this)
                    .load("http://192.168.56.1/adm_siasis/admin/alumni/"+bundle.getString("foto"))
                    .into(foto_alumni);
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