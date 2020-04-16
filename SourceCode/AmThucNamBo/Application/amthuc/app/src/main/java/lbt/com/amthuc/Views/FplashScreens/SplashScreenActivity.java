package lbt.com.amthuc.Views.FplashScreens;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ldoublem.loadingviewlib.view.LVCircularJump;

import lbt.com.amthuc.Presenters.FplashScreens.igetdataapp;
import lbt.com.amthuc.Presenters.FplashScreens.lgetdataapp;
import lbt.com.amthuc.R;
import lbt.com.amthuc.models.objectClass.app.objkhuvuc_app;

public class SplashScreenActivity extends AppCompatActivity implements igetdataapp {

    lgetdataapp mGetData;
    LVCircularJump mLVCircularJump;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splat_screen);
        //khởi tạo đối tượng mGetData
        mGetData = new lgetdataapp(this,this);
        // lấy hàm thành phần
        mGetData.getthanhphan();
        //ánh xạ đối tượng mLVCcircularJump sang giao diện activity_splat_screen
        mLVCircularJump =  findViewById(R.id.lv_CircularJump);
        // set màu cho đối tượng
        mLVCircularJump.setViewColor(Color.WHITE);
        // chạy hiệu ứng
        mLVCircularJump.startAnim();

    }


    private void showAlert(String title){
        //khởi tạo dialog, tạo giao diện cho dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set tiêu đề hiện thị trên dialog
        builder.setMessage(title);
        // khi người dùng click vào bên ngoài dialog thì dialog ko biến mất
        builder.setCancelable(false);
        // thêm vào dialog button Ok, khi click thì sẽ đóng dialog lại
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                System.exit(0);
            }
        });
        // gắn dao diện dialog bằng thằng builder
        Dialog dialog = builder.create();
        // show dialog
        dialog.show();
    }

    @Override
    public void loadkhuvuc(boolean isSuccess) {

    }

    @Override
    public void saveMyData(boolean isSuccess, objkhuvuc_app mdata) {

    }

    @Override
    public void loitaidulieu_getDataApp() {
        showAlert(getText(R.string.loitaidulieukhoidonglai).toString());
    }

    @Override
    public void taidulieuthanhcong(boolean isRealTime) {
        if(!isRealTime) {
            //khởi tạo đối tượng handler
            Handler handler = new Handler();
            // Sau 5 giây thì sẽ chuyển sang Activity Index
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, IndexActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 500);
        }
    }



}
