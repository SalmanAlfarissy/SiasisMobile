package com.pnp.siasismobile.ui.sekolah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.pnp.siasismobile.R;


public class SekolahFragment extends Fragment {

    public SekolahFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sekolah, container, false);
        TextView vimi = view.findViewById(R.id.vimi);
        TextView judul = view.findViewById(R.id.judul);
        judul.setText("Sistem Akademik Siswa\n\n");
        vimi.setText("VISI DAN MISI\n\n" +
                "VISI :\n" +
                "\t\t\tBERIMAN, CERDAS, KOMPETITIF, BERBUDAYA SERTA BERWAWASAN LINGKUNGAN\n\n" +
                "MISI :\n"+
                "1.MENGHAYATI AJARAN AGAMA SECARA " +
                "  MENDALAM MELALUI PEMBELAJARAN DAN " +
                "  PEMBIASAAN SERTA MENJAGA KELESTARIAN " +
                "  LINGKUNGAN CIPTAAN TUHAN.\n\n" +
                "2.MENGEMBANGKAN POTENSI KECERDASAN " +
                "  INTELEKTUAL, EMOSIONAL DAN SPRITUAL YANG " +
                "  RAMAH LINGKUNGAN\n\n" +
                "3.MAMPU BERKOMPETENSI DIBIDANG " +
                "  AKADEMIK DAN NON AKADEMIK DITINGKAT " +
                "  DAERAH MAUPUN NASIONAL\n\n" +
                "4.MENJUNJUNG TINGGI NLAI - NILAI " +
                "  KEMANUSIAN, TEGUH DALAM PENDIRIAN, " +
                "  PEDULI LINGKUNGAN DAN MENGHARGAI " +
                "  PERBEDAAN DENGAN JIWA\n\n" +
                "5.MEWUJUDKAN SEKOLAH BERWAWASAN " +
                "  LINGKUNGAN HIDUP\n\n");

        // Inflate the layout for this fragment
        return view;
    }
}