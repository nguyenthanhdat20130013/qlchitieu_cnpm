package com.example.quanlychitieu.Adapter;

import static java.security.AccessController.getContext;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.DAO.KhoanThuDAO;
import com.example.quanlychitieu.DAO.LoaiThuDAO;
import com.example.quanlychitieu.DTO.KhoanThu;
import com.example.quanlychitieu.DTO.LoaiThu;
import com.example.quanlychitieu.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class KhoanThuAdapter extends RecyclerView.Adapter<KhoanThuAdapter.KhoanThuViewHoldel> {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Context context;
    private ArrayList<KhoanThu> arrayListKhoanThu;
    LoaiThuDAO loaiThuDAO;
    KhoanThuDAO khoanThuDAO;
    DatePickerDialog datePickerDialog;
    LoaiThu loaiThu;
    ArrayList<LoaiThu> list = new ArrayList<>();

    public KhoanThuAdapter(Context context, ArrayList<KhoanThu> arrayListKhoanThu) {
        this.context = context;
        this.arrayListKhoanThu = arrayListKhoanThu;
        loaiThuDAO = new LoaiThuDAO(context);
        khoanThuDAO = new KhoanThuDAO(context);
    }

    @NonNull
    //ham tra ve layout hien thi
    @Override
    public KhoanThuViewHoldel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.khoan_thu_view_item, null);
        return new KhoanThuViewHoldel(view);
    }

    //ham set du lieu de hien thi len
    @Override
    public void onBindViewHolder(@NonNull KhoanThuViewHoldel holder, int position) {
        KhoanThu khoanThu = khoanThuDAO.getAll().get(position);
        holder.tvTenKhoanThu.setText(khoanThu.getTenKhoanThu());
        holder.tvSoTienKhoanThu.setText("Số Tiền: " + khoanThu.getSoTien() + " vnđ");
        holder.tvNgayThuKhoanThu.setText("Ngày Thu: " + simpleDateFormat.format(khoanThu.getNgayThu()));
        holder.tvNoiDungKhoanThu.setText("Nội Dung: " + khoanThu.getNoiDung());
        holder.tvLoaiThuKhoanThu.setText("Loại Thu: " + loaiThuDAO.getTenLoaiThu(khoanThu.getIdTenLoaiThu()));
    }

    @Override
    public int getItemCount() {
        return arrayListKhoanThu.size();
    }

    public class KhoanThuViewHoldel extends RecyclerView.ViewHolder {
        TextView tvTenKhoanThu;
        TextView tvSoTienKhoanThu;
        TextView tvNgayThuKhoanThu;
        TextView tvNoiDungKhoanThu;
        TextView tvLoaiThuKhoanThu;
        ImageView ivEdit;
        ImageView ivDel;

        public KhoanThuViewHoldel(View itemView) {
            super(itemView);
            tvTenKhoanThu = itemView.findViewById(R.id.tv_tenkhoanthu);
            tvSoTienKhoanThu = itemView.findViewById(R.id.tv_sotien_khoanthu);
            tvNgayThuKhoanThu = itemView.findViewById(R.id.tv_ngaythu_khoanthu);
            tvNoiDungKhoanThu = itemView.findViewById(R.id.tv_noidung_khoanthu);
            tvLoaiThuKhoanThu = itemView.findViewById(R.id.tv_loaithu_khoanthu);
            // tìm kiếm giao diện sửa khoản thi
            ivEdit = itemView.findViewById(R.id.iv_edit);
            ivDel = itemView.findViewById(R.id.iv_delete);
        }
    }

    //để cập nhật các thành phần giao diện người dùng trong đối tượng holder
    // với dữ liệu mới từ đối tượng KhoanThu tại vị trí position.
    @Override
    public void onBindViewHolder(@NonNull KhoanThuViewHoldel holder, int position, @NonNull List<Object> payloads) {
        //
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setMonetaryDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0 ₫", symbols);
        //
        KhoanThu khoanThu = khoanThuDAO.getAll().get(position);
        holder.tvTenKhoanThu.setText(khoanThu.getTenKhoanThu());
        holder.tvSoTienKhoanThu.setText("Số Tiền: " + decimalFormat.format(khoanThu.getSoTien()));
        holder.tvNgayThuKhoanThu.setText("Ngày Thu: " + simpleDateFormat.format(khoanThu.getNgayThu()));
        holder.tvNoiDungKhoanThu.setText("Nội Dung: " + khoanThu.getNoiDung());
        holder.tvLoaiThuKhoanThu.setText("Loại Thu: " + loaiThuDAO.getTenLoaiThu(khoanThu.getIdTenLoaiThu()));
// 2.Khi click vào biểu tưởng cây bút sẽ hiển thị tất cả thông tin của khoản thu đó
// vào giao diện sửa khoản thu
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_khoanthu, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                //ẩn background của view-item
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                EditText edSuaTenKhoanThu = view.findViewById(R.id.ed_sua_tenkhoanthu);
                EditText edSuaNgayThuKhoanThu = view.findViewById(R.id.ed_sua_ngaythukhoanthu);
                EditText edSuaNoiDungKhoanThu = view.findViewById(R.id.ed_sua_noidungkhoanthu);
                EditText edSuaSoTienKhoanThu = view.findViewById(R.id.ed_sua_sotienkhoanthu);
                Spinner spinner = view.findViewById(R.id.sp_sua_loaithu);
                //đổ dữu liệu vào cho spinner
                list = loaiThuDAO.getAll();
                ArrayAdapter adapter_sp = new ArrayAdapter(context, android.R.layout.simple_list_item_1, list);
                spinner.setAdapter(adapter_sp);
                //chon ngay
                edSuaNgayThuKhoanThu.setOnClickListener(new View.OnClickListener() {
                    //                    @RequiresApi(api = Build.VERSION_CODES.N)
//                    @Override
//                    public void onClick(View v) {
//                        Calendar calendar = Calendar.getInstance();
//                        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                                edSuaNgayThuKhoanThu.setText(dayOfMonth+"-"+(month+1)+"-"+year);
//                            }
//                        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.MONTH));
//                        datePickerDialog.show();
//                    }
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();

                        // Lấy giá trị từ EditText edSuaNgayThuKhoanThu
                        String dateString = edSuaNgayThuKhoanThu.getText().toString();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        try {
                            Date date = dateFormat.parse(dateString);

                            // Thiết lập giá trị ngày cho Calendar
                            calendar.setTime(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Thiết lập giá trị ngày cho EditText edSuaNgayThuKhoanThu
                                edSuaNgayThuKhoanThu.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                        datePickerDialog.show();
                    }

                });
                // 2. cập nhật dữ liệu mới vào đối tượng tại vị trí đã sửa.
                edSuaTenKhoanThu.setText(khoanThu.getTenKhoanThu());
                edSuaNgayThuKhoanThu.setText(simpleDateFormat.format(khoanThu.getNgayThu()));
                edSuaNoiDungKhoanThu.setText(khoanThu.getNoiDung());
                edSuaSoTienKhoanThu.setText(khoanThu.getSoTien() + "");
                int vitri = -1;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTenLoaiThu().equalsIgnoreCase(loaiThuDAO.getTenLoaiThu(khoanThu.getIdTenLoaiThu()))) {
                        vitri = i;
                        break;
                    }
                }
                spinner.setSelection(vitri);

                Button btnSua = view.findViewById(R.id.btn_sua_khoanthu);
                Button btnHuy = view.findViewById(R.id.btn_huy_khoanthu);

                //3.1 click chọn button sửa
                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    //3.1.1 Nếu người dùng không bỏ trống mục nào khi sửa và sẽ cập nhật dữ liệu mới vào đối tượng
                    // tại vị trí đã sửa. và thông báo sửa thành công.
                    public void onClick(View v) {
                        if (edSuaTenKhoanThu.getText().toString().isEmpty() ||
                                edSuaNoiDungKhoanThu.getText().toString().isEmpty() ||
                                edSuaNgayThuKhoanThu.getText().toString().isEmpty() ||
                                edSuaSoTienKhoanThu.getText().toString().isEmpty()) {
                            Toast.makeText(v.getContext(), "Các Trường Không được để trống", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            khoanThu.setTenKhoanThu(edSuaTenKhoanThu.getText().toString());

                            khoanThu.setNoiDung(edSuaNoiDungKhoanThu.getText().toString());
                            khoanThu.setSoTien(Float.parseFloat(edSuaSoTienKhoanThu.getText().toString()));
                            try {
                                khoanThu.setNgayThu(simpleDateFormat.parse(edSuaNgayThuKhoanThu.getText().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            //set spiner
                            loaiThu = (LoaiThu) spinner.getSelectedItem();
                            khoanThu.setIdTenLoaiThu(loaiThu.getIdTenLoaiThu());
                        }

                        long result = khoanThuDAO.update(khoanThu);
                        if (result > 0) {

                            arrayListKhoanThu.clear();
                            arrayListKhoanThu.addAll(khoanThuDAO.getAll());
                            notifyDataSetChanged();

                            Toast.makeText(v.getContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        } else {
                            //3.1.2  Nếu người dùng bỏ trống bất cứ mục nào thì sẽ không được
                            // cập nhập và sẽ nhận thông báo sửa thất bại.
                            Toast.makeText(v.getContext(), "Sửa Thất Bại", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    }
                });
                //3.2  click chọn hủy thì sẽ trở về trang khoản thu
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Button btn_delete = view.findViewById(R.id.btn_ok_delete);
                Button btn_huy_delete = view.findViewById(R.id.btn_no_delete);
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long result = khoanThuDAO.delete(khoanThu.getIdKhoanThu());
                        if (result > 0) {
                            //buoc cap nhap lai du lieu
                            arrayListKhoanThu.clear();
                            arrayListKhoanThu.addAll(khoanThuDAO.getAll());
                            notifyDataSetChanged();
                            Toast.makeText(v.getContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(v.getContext(), "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    }
                });

                btn_huy_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }
}