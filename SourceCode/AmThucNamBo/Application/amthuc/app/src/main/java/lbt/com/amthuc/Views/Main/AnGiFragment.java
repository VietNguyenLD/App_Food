package lbt.com.amthuc.Views.Main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import lbt.com.amthuc.Presenters.Main.iangi;
import lbt.com.amthuc.Presenters.FplashScreens.igetdataapp;
import lbt.com.amthuc.Presenters.Main.langi;
import lbt.com.amthuc.Presenters.FplashScreens.lgetdataapp;
import lbt.com.amthuc.R;
import lbt.com.amthuc.Views.ChiTietBaiViet.ChiTietBaiVietActivity;
import lbt.com.amthuc.Views.FplashScreens.IndexActivity;
import lbt.com.amthuc.customAdapter.aRclvChung;
import lbt.com.amthuc.customAdapter.aRclvDacSan;
import lbt.com.amthuc.utils.CustomDialogLoading;
import lbt.com.amthuc.models.objectClass.app.objbaiviet_app;
import lbt.com.amthuc.models.objectClass.app.objkhuvuc_app;


public class AnGiFragment extends Fragment implements igetdataapp, iangi {

    lgetdataapp mDataApp;

    objkhuvuc_app mObjKhuVuc;

    RecyclerView rclvChungAn,rclvDatBietAn;
    aRclvChung adapterChungAn;
    aRclvDacSan adapterDacSanAn;

    langi mAnGi;

    CustomDialogLoading mDialogLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //  tạo view và gán giao diện cho fragment_an_gi
        View v = inflater.inflate(R.layout.fragment_an_gi, container, false);

        initView(v);
        getData();
        mDialogLoading.showDialog("Loading..");

        // trả về view
        return v;
    }


    private void initView(View v) {
        // khởi tạo đối tuong igetdataapp để lấy dưc liệu
        mDataApp = new lgetdataapp(getContext(),this);
        // ánh xạ recyclerView sang giao diện
        rclvChungAn = v.findViewById(R.id.rclvAnChung);
        rclvDatBietAn = v.findViewById(R.id.rclvAnDatBiet);
        //  khởi tạo đối tượng ăn gì
        mAnGi = new langi(this);
        // khởi tạo một dialog và gán cho nó ở dao điện hiện tại
        mDialogLoading = new CustomDialogLoading(getContext());




    }

    private void getData(){
        // gán đối tượng khu vực bằng khu vực đã lấy hiện tại
        mObjKhuVuc = mDataApp.getmykhuvuc();
        // nếu mà chưa có khu vực thì nó sẽ chuyển về giao diện indexactivity
        if(mObjKhuVuc == null){
            Intent intent = new Intent(getActivity(),IndexActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        // nếu tồn tại thì nó đặc sản dựa trên mã khu vực
        mAnGi.getDacSan(mObjKhuVuc.getMakhuvuc());

    }

    @Override
    public void loadkhuvuc(boolean isSuccess) {

    }

    @Override
    public void saveMyData(boolean isSuccess, objkhuvuc_app mdata) {

    }

    @Override
    public void loitaidulieu_getDataApp() {

    }

    @Override
    public void loitaidulieu_AnGi() {
        // khởi tạo một dialoag
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //set nội dung của dialog
        builder.setMessage(getText(R.string.loitaidulieukhoidonglai));
        // tạo button bên trái ok và bỏ qua dialog
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                System.exit(0);
            }
        });
        // tạo button cancel bên pải cancel và bỏ qua dialog
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        // khi click vào vùng bên ngoài thì dialog ko biến mất
        builder.setCancelable(false);
        // khởi tạo dialog
        Dialog dialog = builder.create();
        // hiển thị dia log
        dialog.show();
    }

    @Override
    public void danhsachbaivietchung_AnGi(final ArrayList<objbaiviet_app> list) {


    }

    @Override
    public void danhsachbaivietdacsan_AnGi(final ArrayList<objbaiviet_app> list) {
        // tải xong dữ liệu thì bỏ qua dialog
        mDialogLoading.dismissDialog();
        // neus danh sách mảng objectbaiviet_app ko rổng
        if(list!=null) {
            // thì sẽ tạo một linearlayout
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            // set kich thước vừa phải
            rclvDatBietAn.setHasFixedSize(true);
            //set recyclerviewDaBietAn cái layout
            rclvDatBietAn.setLayoutManager(manager);
            // khởi tạo adapter để truyền dữ liệu
            adapterDacSanAn = new aRclvDacSan(getActivity(), list);
            // truyền dữ liệu vào recyclerview  qa dapter
            rclvDatBietAn.setAdapter(adapterDacSanAn);
            // lắng nghe sự kiên click từng item cho adapter
            adapterDacSanAn.setOnItemClickListener(new aRclvDacSan.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    // khởi tạo đối tượng bundle để lưu dữ liệu
                    Bundle bundle = new Bundle();
                    // put bài viết lên theo vị trí
                    bundle.putSerializable("baiviet", list.get(position));
                    bundle.putString("loai", "monan");
                    // chuyển sang giao diện ChiTietBaiVietActivity
                    Intent intent = new Intent(getContext(), ChiTietBaiVietActivity.class);
                    // truyền dữ liêu vào intent  với key "data"
                    intent.putExtra("data", bundle);
                    // chạy activity
                    getActivity().startActivity(intent);
                }
            });
        }

    }

    @Override
    public void danhsachbaivietphobien_AnGi(final ArrayList<objbaiviet_app> list) {
        //TẢI XONG DỮ LIỆU DISMISS DIALOG
        mDialogLoading.dismissDialog();
        //nếu danh sach không rỗng
        if(list!=null) {
            //khởi tạo một linerlayout
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            //set kich thuoc phù hợp
            rclvChungAn.setHasFixedSize(true);
            // truyền layout vào recyclerview
            rclvChungAn.setLayoutManager(manager);
            // khởi tạo adapter và truyền vào danh sách bài viết
            adapterChungAn = new aRclvChung(getActivity(), list);
            //truyền dữ liệu vào recyclerview bằng adapter
            rclvChungAn.setAdapter(adapterChungAn);
            // lắng nghe sự kiện click vào các item của recyclerview
            adapterChungAn.setOnClickListener(new aRclvChung.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    // khởi tạo đối tượng bundle để lưu dữ liệu
                    Bundle bundle = new Bundle();
                    // put dữ liệu vào danh sách
                    bundle.putSerializable("baiviet", list.get(position));
                    bundle.putString("loai", "monan");
                    // chuyên sang giao diện chitietactivity.class
                    Intent intent = new Intent(getContext(), ChiTietBaiVietActivity.class);
                    // truyền dữ liệu vào intent thông qua key dataapp
                    intent.putExtra("data", bundle);
                    // chay activity
                    getActivity().startActivity(intent);
                }
            });
        }

    }

    @Override
    public void taidulieuthanhcong(boolean i) {

    }
}
