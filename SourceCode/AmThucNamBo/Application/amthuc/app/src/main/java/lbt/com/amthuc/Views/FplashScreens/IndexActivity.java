package lbt.com.amthuc.Views.FplashScreens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import lbt.com.amthuc.Presenters.FplashScreens.igetdataapp;
import lbt.com.amthuc.Presenters.FplashScreens.lgetdataapp;
import lbt.com.amthuc.R;
import lbt.com.amthuc.Views.Main.MainActivity;
import lbt.com.amthuc.utils.CustomDialogLoading;
import lbt.com.amthuc.models.objectClass.app.objkhuvuc_app;

public class IndexActivity extends AppCompatActivity implements igetdataapp {

    Button btnbatdau,btnchonkhuvuc;
    lgetdataapp mKhuVuc;
    CustomDialogLoading mDialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        initView();

        actionChonKhuVuc();
        actionStart();
        checkDataOld();
    }

    //KIỂM TRA NẾU CÓ DỮ LIỆU CŨ THÌ ÁP DỤNG DỮ LIỆU ĐÓ
    private void checkDataOld() {
        // khởi tạo đối tượng objkv v
        objkhuvuc_app objkv = mKhuVuc.getmykhuvuc();
        // nếu đối tượng có giá trị
        if(objkv!=null){
            // sẽ set đoạn text cho button là tên tỉnh đã chọn
            btnchonkhuvuc.setText(objkv.getChitietkhuvuc().getTentinh());
            // có thể click vào button
            btnbatdau.setEnabled(true);
            // hiển thị button
            btnbatdau.setVisibility(View.VISIBLE);
        }
    }

    private void actionStart() {
        btnbatdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void actionChonKhuVuc() {
        btnchonkhuvuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show dialoag cho người dùng
                mDialogLoading.showDialog(getText(R.string.dangtaidulieu).toString());
                // lấy khu vực với key"KVMN"
                mKhuVuc.getKhuVuc("KVMN");
            }
        });
    }



    private void initView() {
        // khởi tạo đội tượng  mDialogLoading
        mDialogLoading = new CustomDialogLoading(this);
        // khởi tạo biến mKhuVuc
        mKhuVuc = new lgetdataapp(this,this);
        // ánh xạ Button sang button Bat dau giao diện
        btnbatdau = findViewById(R.id.btnbatdau);
        // ánh xạ button sang button chontinhthanh
        btnchonkhuvuc = findViewById(R.id.btnchontinhthanh);
        // khởi tạo đối tượng Bundle và lấy dữ liệu
        Bundle bundle = getIntent().getBundleExtra("data");
        // nếu mà buble khác rỗng thì sẽ set btnkhuvuc = tenkhuvuc
        if(bundle!=null){
            btnchonkhuvuc.setText(bundle.getString("tenkhuvuc"));
        }
        //nếu phần text của button trùng với khu vực
        if(btnchonkhuvuc.getText().toString().matches(getText(R.string.chonvitri).toString())){
            // biến mất button
            btnbatdau.setVisibility(View.INVISIBLE);
            // không cho phép click vào button
            btnbatdau.setEnabled(false);
        }else{
            // có thể click vào button
            btnbatdau.setEnabled(true);
            // hiển thị button
            btnbatdau.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadkhuvuc(boolean isSuccess) {
        // bỏ qua dialog
        mDialogLoading.dismissDialog();
        // nếu biến isSuccess = true thì sẽ chuyển dai diện sang SelectKhuVucActivity
       if(isSuccess){
           Intent intent = new Intent(this,SelectKhuVucActivity.class);
           startActivity(intent);

       }else{
           // xuất xòng text cho người dùng xem để biết load khu vực failse
           Toast.makeText(this, getText(R.string.taidulieukhongthanhcong), Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    public void saveMyData(boolean isSuccess, objkhuvuc_app mdata) {

    }

    @Override
    public void loitaidulieu_getDataApp() {

    }

    @Override
    public void taidulieuthanhcong( boolean i) {

    }


}
