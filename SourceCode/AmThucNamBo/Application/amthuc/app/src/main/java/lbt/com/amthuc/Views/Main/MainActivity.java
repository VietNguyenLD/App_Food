package lbt.com.amthuc.Views.Main;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lbt.com.amthuc.Presenters.LoginRegister.ilogin;
import lbt.com.amthuc.Presenters.LoginRegister.llogin;
import lbt.com.amthuc.R;
import lbt.com.amthuc.Views.LoginRegister.MainLoginActivity;
import lbt.com.amthuc.Views.TaiKhoan.admin.AdminActivity;
import lbt.com.amthuc.Views.TaiKhoan.TaiKhoanMainActivity;
import lbt.com.amthuc.customAdapter.aViewPagerMenu;
import lbt.com.amthuc.models.objectClass.app.objnguoidung_app;

public class MainActivity extends AppCompatActivity implements ilogin {

    aViewPagerMenu adapter;
    ViewPager vpMain;
    RadioButton rdoangi,rdouonggi;
    ImageView imvSearch,imvLogin,imvUser;

    llogin mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        phanquyen();
    }



    private void phanquyen() {
        // khởi tạo và kết nối tới firebase uer
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // nếu tồn tại uer
        if(user!=null){
            // ẩn image đi
            imvLogin.setVisibility(View.GONE);
            // hiện image
            imvUser.setVisibility(View.VISIBLE);
            //nếu tồn tại ảnh người dùng
            if(mLogin.getImage(mLogin.getDataNguoiDung().getAnhdaidien())!=null)
                // set ảnh đó làm ảnh của user đó lên app
                imvUser.setImageDrawable(mLogin.getImage(mLogin.getDataNguoiDung().getAnhdaidien()));
            else
                // nếu ko có ảnh đại diện thì set ảnh mặt định
                imvUser.setImageResource(R.drawable.defaultuser);
            // lắng nghe sự kiện click vào ảnh user
            imvUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // nếu là không pai là admin
                    if(!mLogin.getDataNguoiDung().getNguoidung().isQuanly())
                        // chuyển sang dao diện của taikhoanmainActivity
                        startActivity(new Intent(MainActivity.this,TaiKhoanMainActivity.class));
                    else
                        // chuyển sang dao diện của AdminActivity
                        startActivity(new Intent(MainActivity.this,AdminActivity.class));
                }
            });
        }else{
            // hiện imageview
            imvLogin.setVisibility(View.VISIBLE);
            // ẩn imageview user
            imvUser.setVisibility(View.GONE);
            // lắng nghe sự kiện click cho imageview
            imvLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // chuyển sang giao diện LoginActivity
                    startActivity(new Intent(MainActivity.this,MainLoginActivity.class));
                }
            });
        }
    }


    private void initView() {
        // khởi tạo dội tượng login
        mLogin = new llogin(this,this);
        // ánh xạ sang dao diện
        imvLogin = findViewById(R.id.imvLogin);
        imvUser = findViewById(R.id.imvUser);




        //ánh xạ radiobutton sang radio giao diện
        rdoangi = findViewById(R.id.rdoangi);
        rdouonggi = findViewById(R.id.rdouonggi);
        // lắng nghe sự kiện click cho radiobutton
        rdoangi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //set dao diên ăn gì là item ví trí 0
                    vpMain.setCurrentItem(0);
                }else
                    // set dao diện uống gì item vị trí 1
                    vpMain.setCurrentItem(1);
            }
        });
        // ánh xạ ViewPage sang giao diện
        this.vpMain = findViewById(R.id.vpMain);
        // khởi tạo đối tượng adapter để truyền dữ liệu
        adapter = new aViewPagerMenu(getSupportFragmentManager());
        // truyền dữ liệu vào ViewPage  bên ma giao diện
        vpMain.setAdapter(adapter);
        // lắng nghe sự kiện thay đổi cho ViewPage
        vpMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            //hàm xem page nào đã được chọn
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        // page dồ ăn gì được chọn, set checked
                        rdoangi.setChecked(true);
                        break;
                    case 1:
                        // page dồ uống gì được chọn, set checked
                        rdouonggi.setChecked(true);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        // ánh xã imageView sang giao diện
        imvSearch = findViewById(R.id.imvSearch);
        // lắng nghe sự kiện click vào biểu tượng tìm kiếm
        imvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chuyển sang dao diện tìm kiếm searchActivity.class
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });
    }


    @Override
    public void ResultLogin(boolean isSuccess) {

    }

    @Override
    public void result_listenner_values_user(objnguoidung_app muser) {

    }

    @Override
    public void result_dangnhap_sdt(boolean isSuccess) {

    }

    @Override
    public void code(String code) {

    }
}
