package com.example.quanlychitieu.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Adapter.KhoanChiAdapter;
import com.example.quanlychitieu.DAO.KhoanChiDAO;
import com.example.quanlychitieu.DAO.LoaiChiDAO;
import com.example.quanlychitieu.DTO.KhoanChi;
import com.example.quanlychitieu.DTO.LoaiChi;
import com.example.quanlychitieu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Fragment_Khoan_Chi extends Fragment {
//Khai báo các thành phần cần thiết
    RecyclerView rvKhoanChi;
    ArrayList<KhoanChi> arrayListKhoanChi;
    KhoanChiDAO khoanChiDAO;
    KhoanChiAdapter adapter;
    FloatingActionButton fabKhoanChi;
    LoaiChiDAO loaiChiDAO;
    ArrayList<LoaiChi> list = new ArrayList<>();
    DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Hiển thị và lấy ra các thành phần cần thiết
        View view = inflater.inflate(R.layout.fragment_khoan_chi, container, false);

        rvKhoanChi = view.findViewById(R.id.recyclerView_khoanchi);
        fabKhoanChi = view.findViewById(R.id.fab_addkhoanchi);

        loaiChiDAO = new LoaiChiDAO(getContext());
        arrayListKhoanChi = new ArrayList<>();
        khoanChiDAO = new KhoanChiDAO(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvKhoanChi.setLayoutManager(layoutManager);
        arrayListKhoanChi = khoanChiDAO.getAll();
        adapter = new KhoanChiAdapter(getContext(), arrayListKhoanChi);
        rvKhoanChi.setAdapter(adapter);
        /**
         * 1. Xử lý sự kiện khi người dùng nhấn vào nút thêm khoản chi
         */
        //1.1 Hiển thị diaLogAddKhoanChi() bằng cách gọi phương thức view
        fabKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLogAddKhoanChi();
            }
        });

        return view;
    }

    //Hộp thoại thêm khoản chi
    public void diaLogAddKhoanChi() {
        //Khai báo các thành phần cần thiết để xử lý
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_khoanchi, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        EditText edThemTenKhoanChi = view.findViewById(R.id.ed_them_tenkhoanchi);
        EditText edThemNoiDungKhoanChi = view.findViewById(R.id.ed_them_noidungkhoanchi);
        EditText edThemNgayThuKhoanChi = view.findViewById(R.id.ed_them_ngaythukhoanchi);
        EditText edThemSoTienThuKhoanChi = view.findViewById(R.id.ed_them_sotienkhoanchi);
        Spinner spthemkhoanchi = view.findViewById(R.id.sp_them_loaichi);
        //Đưa dữ liệu các loại khoản chi vào Spinner và hiển thị lên
        list = loaiChiDAO.getAll();
        ArrayAdapter adapter_sp = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
        spthemkhoanchi.setAdapter(adapter_sp);
        //Gán sự kiện cho nút thêm ngày và sử dụng DatePicker để giúp người dùng chọn ngày và thực hiện chuyển đổi
        edThemNgayThuKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edThemNgayThuKhoanChi.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        /**
         * 2. Sau khi hiện hộp thoại thêm khoản chi, người dùng sẽ nhập dữ liệu vào các EditText
         */
        //Sau khi người dùng nhập xong sẽ tiến hành bấm nút thêm khoản chi, hoặc huỷ không thêm khoản chi
        Button btnThem = view.findViewById(R.id.btn_them_khoanchi);
        Button btnHuy = view.findViewById(R.id.btn_huy_khoanchi);
        //Gán sự kiện cho nút thêm khoản chi
        /**
         * 3. Người dùng nhấn vào nút thêm khoản chi, và bắt sự kiện onClick
         */
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //3.1. Sẽ kiểm tra các dữ liệu người dùng nhập vào xem có dữ liệu trống hay không
                if (edThemTenKhoanChi.getText().toString().isEmpty() ||
                        edThemNoiDungKhoanChi.getText().toString().isEmpty() ||
                        edThemNgayThuKhoanChi.getText().toString().isEmpty() ||
                        edThemSoTienThuKhoanChi.getText().toString().isEmpty()) {
                    //3.1.0. Nếu có dữ liệu để trống sẽ hiển thị thông báo và yêu cầu người dùng nhập lại
                    Toast.makeText(getContext(), " Các Trường Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                //3.2. Nếu dữ liệu được nhập đầy đủ, sẽ tạo mới đối tượng KhoanChi để lưu các giá trị người dùng
                //đã nhập vào
                KhoanChi khoanChi = new KhoanChi();
                khoanChi.setTenKhoanChi(edThemTenKhoanChi.getText().toString());
                khoanChi.setNoiDung(edThemNoiDungKhoanChi.getText().toString());
                try {
                    khoanChi.setNgayChi(simpleDateFormat.parse(edThemNgayThuKhoanChi.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Lấy ra số tiền, loại chi,
                khoanChi.setSoTien(Float.parseFloat(edThemSoTienThuKhoanChi.getText().toString()));
                LoaiChi loaiChi = (LoaiChi) spthemkhoanchi.getSelectedItem();
                khoanChi.setIdTenLoaiChi(loaiChi.getIdTenLoaiChi());
                //3.3. Tiến hành thêm mới một khoản chi vào cơ sở dữ liệu
                //3.4. Hàm insert sẽ trả về 1 giá trị giúp xác định khoản chi được thêm thành công hay không
                long result = khoanChiDAO.insert(khoanChi);
                //3.5.Nếu thêm thành công, sẽ trả về > 0 ( khác 0) và thông tin khoản chi mới được lưu vào database
                if (result > 0) {
                    //3.6
                    //Cập nhật lại danh sách khoản chi đang hiển thị ( xoá danh sách các khoản chi hiện tại và gọi hàm lấy
                    //lên tất cả khoản chi để hiển thị lại vào list
                    arrayListKhoanChi.clear();
                    arrayListKhoanChi.addAll(khoanChiDAO.getAll());
                    //Phương thức này thông báo cho Adapter biết rằng dữ liệu đã thay đổi và yêu cầu nó cập nhật lại hiển thị của danh sách.
                    // Sau khi phương thức này được gọi, Adapter sẽ chạy lại các phương thức getView() để lấy dữ liệu mới và cập nhật giao diện người dùng.
                    adapter.notifyDataSetChanged();
                    //Hiển thị thông báo thêm thành công
                    Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else {
                    //3.4.1 Thông báo thêm mới thất bại
                    Toast.makeText(getContext(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
        /**
         * 4. Người dùng bấm vào nút huỷ
         */
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Đóng hộp thoại và kết thúc.
                alertDialog.dismiss();
            }
        });

    }
}
