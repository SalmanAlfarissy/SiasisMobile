package com.pnp.siasismobile.ui.biodata;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.pnp.siasismobile.MainActivity;
import com.pnp.siasismobile.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class BiodataFragment extends Fragment {

    TextView nama,nis,nisn,ttl,jekel,agama,kelas,nohp,
            alamat,anakke,statuskeluarga,tahunditerima
            ,semesterditerima,asalsekolah,alamatasalsekolah,semester
            ,tahun_ijazah_sebelumnya,nomor_ijazah_sebelumnya,
            tahun_skhun_sebelumya,nomor_skhun_sebelumnya,nama_ayah,
            nama_ibu,alamat_ortu,pekerjaan_ayah,pekerjaan_ibu,nama_wali,
            alamat_wali,no_hp_wali,pekerjaan_wali;
    CircleImageView imageView;
    ImageView imgbio;
    CardView btnUpdate,btnSave;
    private static int REQUEST_CODE = 100;
    OutputStream outputStream;
    int cek;
    RequestQueue requestQueue;
    private StringRequest stringRequest;
    ArrayList<HashMap<String,String>> list_data;

    public BiodataFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_biodata, container, false);
        nama = view.findViewById(R.id.nama);
        semester = view.findViewById(R.id.semester);
        nis = view.findViewById(R.id.nis);
        nisn = view.findViewById(R.id.nisn);
        ttl = view.findViewById(R.id.ttl);
        jekel = view.findViewById(R.id.jekel);
        agama = view.findViewById(R.id.agama);
        kelas = view.findViewById(R.id.kelas);
        nohp = view.findViewById(R.id.nohp);
        alamat = view.findViewById(R.id.alamat);
        anakke = view.findViewById(R.id.anakke);
        statuskeluarga = view.findViewById(R.id.statuskeluarga);
        tahunditerima = view.findViewById(R.id.tahunditerima);
        semesterditerima = view.findViewById(R.id.semesterditerima);
        asalsekolah = view.findViewById(R.id.asalsekolah);
        alamatasalsekolah = view.findViewById(R.id.alamatasalsekolah);

        tahun_ijazah_sebelumnya = view.findViewById(R.id.tahun_ijazah_sebelumnya);
        nomor_ijazah_sebelumnya = view.findViewById(R.id.nomor_ijazah_sebelumnya);
        tahun_skhun_sebelumya = view.findViewById(R.id.tahun_skhun_sebelumya);
        nomor_skhun_sebelumnya = view.findViewById(R.id.nomor_skhun_sebelumnya);
        nama_ayah = view.findViewById(R.id.nama_ayah);
        nama_ibu = view.findViewById(R.id.nama_ibu);
        alamat_ortu = view.findViewById(R.id.alamat_ortu);
        pekerjaan_ayah = view.findViewById(R.id.pekerjaan_ayah);
        pekerjaan_ibu = view.findViewById(R.id.pekerjaan_ibu);
        nama_wali = view.findViewById(R.id.nama_wali);
        alamat_wali = view.findViewById(R.id.alamat_wali);
        no_hp_wali = view.findViewById(R.id.no_hp_wali);
        pekerjaan_wali = view.findViewById(R.id.pekerjaan_wali);

        imageView = view.findViewById(R.id.imageView);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnSave = view.findViewById(R.id.btnSave1);
        imgbio = view.findViewById(R.id.imgbio);

        requestQueue = Volley.newRequestQueue(getActivity());
        list_data = new ArrayList<HashMap<String, String>>();



        simpan();
        btnDetail();
        listdata();

        // Inflate the layout for this fragment
        return view;
    }


    private void btnDetail() {
        MainActivity mainActivity = (MainActivity) getActivity();
        Intent intentbio = mainActivity.getIntent();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UpdateFragment.class);
                intent.putExtra("nis",(String)intentbio.getSerializableExtra("nis"));
                intent.putExtra("password",(String)intentbio.getSerializableExtra("password"));
                intent.putExtra("nama_sis",(String)intentbio.getSerializableExtra("nama_sis"));
                intent.putExtra("foto_sis",(String)intentbio.getSerializableExtra("foto_sis"));
                intent.putExtra("ttl",(String)intentbio.getSerializableExtra("ttl"));
                intent.putExtra("jenis_kelamin",(String)intentbio.getSerializableExtra("jenis_kelamin"));
                intent.putExtra("agama",(String)intentbio.getSerializableExtra("agama"));
                intent.putExtra("status_keluarga",(String)intentbio.getSerializableExtra("status_keluarga"));
                intent.putExtra("anak_ke",(String)intentbio.getSerializableExtra("anak_ke"));
                intent.putExtra("alamat",(String)intentbio.getSerializableExtra("alamat"));
                intent.putExtra("no_hp",(String)intentbio.getSerializableExtra("no_hp"));
                intent.putExtra("tahun_diterima",(String)intentbio.getSerializableExtra("tahun_diterima"));
                intent.putExtra("semester_diterima",(String)intentbio.getSerializableExtra("semester_diterima"));
                intent.putExtra("nama_sekolah_asal",(String)intentbio.getSerializableExtra("nama_sekolah_asal"));
                intent.putExtra("alamat_sekolah_asal",(String)intentbio.getSerializableExtra("alamat_sekolah_asal"));
                intent.putExtra("tahun_ijazah_sebelumnya",(String)intentbio.getSerializableExtra("tahun_ijazah_sebelumnya"));
                intent.putExtra("nomor_ijazah_sebelumnya",(String)intentbio.getSerializableExtra("nomor_ijazah_sebelumnya"));
                intent.putExtra("tahun_skhun_sebelumya",(String)intentbio.getSerializableExtra("tahun_skhun_sebelumya"));
                intent.putExtra("nomor_skhun_sebelumnya",(String)intentbio.getSerializableExtra("nomor_skhun_sebelumnya"));
                intent.putExtra("nama_ayah",(String)intentbio.getSerializableExtra("nama_ayah"));
                intent.putExtra("nama_ibu",(String)intentbio.getSerializableExtra("nama_ibu"));
                intent.putExtra("alamat_ortu",(String)intentbio.getSerializableExtra("alamat_ortu"));
                intent.putExtra("pekerjaan_ayah",(String)intentbio.getSerializableExtra("pekerjaan_ayah"));
                intent.putExtra("pekerjaan_ibu",(String)intentbio.getSerializableExtra("pekerjaan_ibu"));
                intent.putExtra("nama_wali",(String)intentbio.getSerializableExtra("nama_wali"));
                intent.putExtra("alamat_wali",(String)intentbio.getSerializableExtra("alamat_wali"));
                intent.putExtra("no_hp_wali",(String)intentbio.getSerializableExtra("no_hp_wali"));
                intent.putExtra("pekerjaan_wali",(String)intentbio.getSerializableExtra("pekerjaan_wali"));
                startActivity(intent);
            }
        });
    }

    private void listdata() {
        MainActivity mainActivity = (MainActivity) getActivity();
        Intent intentbio = mainActivity.getIntent();

        nama.setText((String)intentbio.getSerializableExtra("nama_sis"));
        semester.setText((String)intentbio.getSerializableExtra("semester"));
        nis.setText((String)intentbio.getSerializableExtra("nis"));
        nisn.setText((String)intentbio.getSerializableExtra("nisn"));
        ttl.setText((String)intentbio.getSerializableExtra("ttl"));
        jekel.setText((String)intentbio.getSerializableExtra("jenis_kelamin"));
        agama.setText((String)intentbio.getSerializableExtra("agama"));
        kelas.setText((String)intentbio.getSerializableExtra("kelas"));
        nohp.setText((String)intentbio.getSerializableExtra("no_hp"));
        alamat.setText((String)intentbio.getSerializableExtra("alamat"));
        anakke.setText((String)intentbio.getSerializableExtra("anak_ke"));
        statuskeluarga.setText((String)intentbio.getSerializableExtra("status_keluarga"));
        tahunditerima.setText((String)intentbio.getSerializableExtra("tahun_diterima"));
        semesterditerima.setText((String)intentbio.getSerializableExtra("semester_diterima"));
        asalsekolah.setText((String)intentbio.getSerializableExtra("nama_sekolah_asal"));
        alamatasalsekolah.setText((String)intentbio.getSerializableExtra("alamat_sekolah_asal"));

        tahun_ijazah_sebelumnya.setText((String)intentbio.getSerializableExtra("tahun_ijazah_sebelumnya"));
        nomor_ijazah_sebelumnya.setText((String)intentbio.getSerializableExtra("nomor_ijazah_sebelumnya"));
        tahun_skhun_sebelumya.setText((String)intentbio.getSerializableExtra("tahun_skhun_sebelumya"));
        nomor_skhun_sebelumnya.setText((String)intentbio.getSerializableExtra("nomor_skhun_sebelumnya"));
        nama_ayah.setText((String)intentbio.getSerializableExtra("nama_ayah"));
        nama_ibu.setText((String)intentbio.getSerializableExtra("nama_ibu"));
        alamat_ortu.setText((String)intentbio.getSerializableExtra("alamat_ortu"));
        pekerjaan_ayah.setText((String)intentbio.getSerializableExtra("pekerjaan_ayah"));
        pekerjaan_ibu.setText((String)intentbio.getSerializableExtra("pekerjaan_ibu"));
        nama_wali.setText((String)intentbio.getSerializableExtra("nama_wali"));
        alamat_wali.setText((String)intentbio.getSerializableExtra("alamat_wali"));
        no_hp_wali.setText((String)intentbio.getSerializableExtra("no_hp_wali"));
        pekerjaan_wali.setText((String)intentbio.getSerializableExtra("pekerjaan_wali"));

        String txtconten = (String)intentbio.getSerializableExtra("foto_sis");
        String urlGambar = "https://siasis-mobile.000webhostapp.com/admin/siswa/"+txtconten;
        if (txtconten.equals("")){
            imageView.setImageResource(R.drawable.user_account);
        }else {
            Glide.with(getActivity())
                    .load(urlGambar)
                    .into(imageView);
        }
        String txtbio = (String)intentbio.getSerializableExtra("cover");
        String urlBio = "https://siasis-mobile.000webhostapp.com/admin/cover/"+txtbio;
        Glide.with(getActivity())
                .load(urlBio)
                .into(imgbio);
    }

    private void simpan() {
        btnSave.setOnClickListener(new View.OnClickListener() {
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

        BitmapDrawable drawable = (BitmapDrawable) imgbio.getDrawable();
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