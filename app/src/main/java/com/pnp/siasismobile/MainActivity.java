package com.pnp.siasismobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_bio, R.id.nav_jadwal,R.id.nav_spp,R.id.nav_raport,R.id.nav_sekolah,R.id.nav_alumni,R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        menggganti tampilan header navigation drawer
        View hView =  navigationView.inflateHeaderView(R.layout.nav_header_main);
        CircleImageView foto = (CircleImageView)hView.findViewById(R.id.foto);
        TextView txtnama = (TextView)hView.findViewById(R.id.txtnama);
        TextView txtnis = (TextView)hView.findViewById(R.id.txtnis);

        Intent intentlogin = getIntent();
        txtnama.setText((String)intentlogin.getSerializableExtra("nama_sis"));
        txtnis.setText((String)intentlogin.getSerializableExtra("nis"));
        String txtconten = (String)intentlogin.getSerializableExtra("foto_sis");

        String urlGambar = "https://siasis-mobile.000webhostapp.com/admin/siswa/"+txtconten;
        if (txtconten.equals("")){
            foto.setImageResource(R.drawable.user_account);
        }else {
            Glide.with(MainActivity.this)
                    .load(urlGambar)
                    .into(foto);
        }

//        send data to fragment
//        String nis = txtnis.getText().toString();
//        Intent intentjadwal = new Intent(MainActivity.this, JadwalFragment.class);
//        intentjadwal.putExtra("nis",nis);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}