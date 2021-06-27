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

import androidx.fragment.app.Fragment;

import com.pnp.siasismobile.MainActivity;
import com.pnp.siasismobile.R;


public class SppFragment extends Fragment {
    TextView txtbulan,txtpbm,txtpenunjang,txtno,txtjml1,txtjml2,txttotal,norek,statuspembayaran;
    Button btnbayar;

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

        MainActivity mainActivity = (MainActivity) getActivity();
        Intent data = mainActivity.getIntent();
        String status = (String)data.getSerializableExtra("status");
        statuspembayaran.setText(status);
        if (status.equalsIgnoreCase("Lunas")){
            btnbayar.setEnabled(false);

        }else{
            btnbayar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nama = (String)data.getSerializableExtra("nama_sis");
                    String kelas = (String)data.getSerializableExtra("kelas");
                    String nis = (String)data.getSerializableExtra("nis");
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

        listpembayaran();

        // Inflate the layout for this fragment
        return view;
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