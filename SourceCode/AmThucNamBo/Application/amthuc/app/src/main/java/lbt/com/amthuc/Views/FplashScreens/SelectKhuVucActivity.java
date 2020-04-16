package lbt.com.amthuc.Views.FplashScreens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import lbt.com.amthuc.Presenters.FplashScreens.igetdataapp;
import lbt.com.amthuc.Presenters.FplashScreens.lgetdataapp;
import lbt.com.amthuc.R;
import lbt.com.amthuc.customAdapter.aRclvChonKhuVuc;
import lbt.com.amthuc.models.objectClass.app.objkhuvuc_app;

public class SelectKhuVucActivity extends AppCompatActivity implements igetdataapp {

    RecyclerView rclvKhuVuc;
    aRclvChonKhuVuc adapter;
    ArrayList<objkhuvuc_app> mList;

    lgetdataapp mKV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_khu_vuc);
        initView();
    }



    private void initView(){
        //Ánh xạ biến RecylerView sang giao diện
        rclvKhuVuc = findViewById(R.id.rcrkhuvuc);
        // khởi tạo đối tượng
        mKV = new lgetdataapp(this,this);
        // khởi tạo đối tượng lưu trữ dữ liệu
        SharedPreferences preferences = getSharedPreferences("data_app",MODE_PRIVATE);
        // khởi tạo biến string và gán các giá trị với key khuvuc
        String jsonkhuvuc = preferences.getString("khuvuc",null);
        // nếu biến jsonkhuvuc không rỗng
        if(jsonkhuvuc!=null){
            //tạo đối tượng gson
            Gson gson = new Gson();
            // khởi tạo một type kiểu token cho mản danh sách các đối tượng khu vực
            Type listtype = new TypeToken<ArrayList<objkhuvuc_app>>(){}.getType();
            // gán cho mlist thành json( truyền vào text và kiểu danh sách)
            mList = gson.fromJson(jsonkhuvuc,listtype);
            //goi hàm
            setupRecyclerView();
        }else{
            Toast.makeText(this, getText(R.string.loikhuvuc), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupRecyclerView() {
        // khởi tạo adapter
        adapter = new aRclvChonKhuVuc(this,mList);
        // hởi tạo đối tượng linearlayoutmanager
        LinearLayoutManager manager = new LinearLayoutManager((Context) this,LinearLayoutManager.VERTICAL,false);
        // tạo size tương đối
        rclvKhuVuc.hasFixedSize();
        // set recylerView kiểu Linear layout
        rclvKhuVuc.setLayoutManager(manager);
        // truyền adapter vào recyclerView
        rclvKhuVuc.setAdapter(adapter);
        // lắng nghe sự kiện click cho từng item
        adapter.setOnClickListener(new aRclvChonKhuVuc.OnClickListener() {
            @Override
            public void OnClick(View itemView, int position) {
                // lưu dữ liệu vào danh sách có vị trí
                mKV.savemykhuvuc(mList.get(position));
            }
        });
    }

    @Override
    public void loadkhuvuc(boolean isSuccess) {

    }

    @Override
    public void saveMyData(boolean isSuccess, objkhuvuc_app mdata) {
        if(isSuccess) {
            // tạo đối tượng intent để chuyển sang giao diện indexactivity
            Intent intent = new Intent(this, IndexActivity.class);
            // gán cờ cho intent và xóa các activity trong stack, để nó không quay về activity cũ
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // khởi tạo bundle
            Bundle bundle = new Bundle();
            // thêm tên khu vực vào bundle với key là tenkhuvuc
            bundle.putString("tenkhuvuc",mdata.getChitietkhuvuc().getTentinh());
            // thêm dữ liệu vào itent vớ key là data
            intent.putExtra("data",bundle);
            // chạy itent để chuyển trang
            startActivity(intent);
            finish();
        }else
            Toast.makeText(this, getText(R.string.loikhuvuc), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loitaidulieu_getDataApp() {

    }


    @Override
    public void taidulieuthanhcong(boolean i) {

    }
}
