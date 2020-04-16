package lbt.com.amthuc.Views.Main;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

import lbt.com.amthuc.R;

public class SettingActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnEn,btnVi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        actionSetingLanguage();
    }

    private void actionSetingLanguage() {
        btnVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ganngonngu("vi");
            }
        });
        btnEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ganngonngu("en");
            }
        });
    }

    private void initView() {
        //Ánh xã button sang giao diện
        btnEn = findViewById(R.id.btnEN);
        btnVi = findViewById(R.id.btnVI);
        toolbar = findViewById(R.id.toolbarsetting);
        //biến toolbar thành actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //lắng nghe sự kiện click vào actionbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chuyển về giao diện trước
                onBackPressed();
                finish();
            }
        });
    }

    //GÁN NGÔN NGỮ
    public void ganngonngu(String language){
        // khởi tạo đối tượng đa ngôn ngữ
        Locale myLocale = new Locale(language);
        // cấu tạo lại hệ thống
        Configuration config = new Configuration();
        // cấu hình lại ngôn ngữ
        config.locale = myLocale;
        // cập nhật lại resources
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        //load lại màn hình
        Intent intent = new Intent(SettingActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Toast.makeText(this,getText(R.string.thanhcong),Toast.LENGTH_SHORT).show();
        finish();


    }
}
