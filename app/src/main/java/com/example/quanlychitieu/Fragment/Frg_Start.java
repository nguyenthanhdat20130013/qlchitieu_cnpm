package com.example.quanlychitieu.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.quanlychitieu.DAO.KhoanChiDAO;
import com.example.quanlychitieu.DAO.KhoanThuDAO;
import com.example.quanlychitieu.DTO.KhoanChi;
import com.example.quanlychitieu.DTO.KhoanThu;
import com.example.quanlychitieu.R;

import java.util.ArrayList;


public class Frg_Start extends Fragment {

    TextView tiendathu;
    TextView tiendachi;
    TextView tienconlai;
    KhoanChiDAO khoanChiDAO;
    KhoanThuDAO khoanThuDAO;
    float tongthu = 0, tongchi = 0, conlai = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main_start, container, false);

        tiendathu = (TextView) view.findViewById(R.id.TvTienMat);
        tiendachi = (TextView) view.findViewById(R.id.TvTinDung);
        tienconlai = (TextView) view.findViewById(R.id.tvSoDu);
        //Thống kê
        khoanChiDAO = new KhoanChiDAO(getContext());
        khoanThuDAO = new KhoanThuDAO(getContext());

        ArrayList<KhoanChi> listchi = khoanChiDAO.getAll();
        for (int i = 0; i < listchi.size(); i++) {
            tongchi = tongchi + listchi.get(i).getSoTien();
        }

        ArrayList<KhoanThu> listthu = khoanThuDAO.getAll();
        for (int i = 0; i < listthu.size(); i++) {
            tongthu = tongthu + listthu.get(i).getSoTien();
        }
        conlai = tongthu - tongchi;
        tiendathu.setText(tongthu + "");
        tiendachi.setText(tongchi + "");
        tienconlai.setText(conlai + "");
        return view;
    }
}
