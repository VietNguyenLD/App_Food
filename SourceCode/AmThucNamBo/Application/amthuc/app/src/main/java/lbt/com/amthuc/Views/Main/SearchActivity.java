package lbt.com.amthuc.Views.Main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import lbt.com.amthuc.Presenters.Main.ifilter;
import lbt.com.amthuc.Presenters.Main.lfilter;
import lbt.com.amthuc.R;
import lbt.com.amthuc.Views.ChiTietBaiViet.ChiTietBaiVietActivity;
import lbt.com.amthuc.customAdapter.aRclvDacSan;
import lbt.com.amthuc.customAdapter.aSnThanhPhan;
import lbt.com.amthuc.utils.CustomDialogLoading;
import lbt.com.amthuc.models.objectClass.app.objbaiviet_app;
import lbt.com.amthuc.models.objectClass.app.objkhuvuc_app;
import lbt.com.amthuc.models.objectClass.firebase.objthanhphan;
import lbt.com.amthuc.utils.getDataApp;

public class SearchActivity extends AppCompatActivity implements ifilter {

    Toolbar toolbar;

    TextView tvTien;
    SeekBar sbTien;
    RelativeLayout rltlHien;
    LinearLayout lnlLoc;
    ImageView imvAn;
    EditText edtTenMon;

    Button btnLoc,btnChonLaiKV;

    getDataApp mGetDataApp;

    objkhuvuc_app mObjKhuVuc;

    Spinner spinnerThanhPhan;

    ArrayList<objthanhphan> mlistThanhPhan;
    aSnThanhPhan adapterThanhPhan;

    CustomDialogLoading mDialogLoading;

    ArrayList<objbaiviet_app> mListResultSearch;

    objthanhphan mThanhPhan;

    aRclvDacSan adapterSearch;
    RecyclerView rclvSearch;

    RadioButton rdoMonAn,rdoNuocUong;

    lfilter mFilter;


    String[] listNameKhuVuc;
    ArrayList<objkhuvuc_app> listKhuVuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();

        actionTien();
        actionAnHienLoc();
        actionChonLaiKhuVuc();
        actionLoc();
        actionLoaiTimKiem();
        setupSpinnerThanhPhan("thanhphanmonan");
    }

    private void actionLoaiTimKiem() {
        rdoMonAn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    // gọi hàm setup và truyền vào thanhphanmonan
                    setupSpinnerThanhPhan("thanhphanmonan");
                }else{
                    // gọi hàm setup và truyền vào thanhphannuocuong
                    setupSpinnerThanhPhan("thanhphannuocuong");
                }
            }
        });
    }

    private void actionLoc() {
        // lắng nghe sự kiện lick cho button Loc
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!btnChonLaiKV.getText().toString().matches(getText(R.string.chonkhuvuc).toString())){
                    mDialogLoading.showDialog("Loading..");
                    String tenmonan = edtTenMon.getText().toString();
                    long giatien = sbTien.getProgress();
                    String mathanhphan = mThanhPhan.getMathanhphan();
                    mFilter.timkiem(rdoMonAn.isChecked(),mObjKhuVuc.getMakhuvuc(), tenmonan, mathanhphan, giatien);
                }
                else
                    Toast.makeText(SearchActivity.this, getText(R.string.loichonkhuvucbaiviet), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setupSpinnerThanhPhan(String loai) {
        // khởi tạo một list thành phần, và truền vào loại thành phần
        mlistThanhPhan = mGetDataApp.getListThanhPhan(loai);
        // nếu list thanh phần  ko rỗng
        if(mlistThanhPhan!=null){
            // khởi tạo adapter và truyền vào màn hình hiện tại và danh sách thành phần
            adapterThanhPhan = new aSnThanhPhan(this,mlistThanhPhan);
            // truyền dữ liêu vào spiner thành phần
            spinnerThanhPhan.setAdapter(adapterThanhPhan);
            //lăng nghe sự kiện được chọn item của spinner
            spinnerThanhPhan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    // lấy vị trí được chọn của item gán cho danh sách thành phần
                    mThanhPhan = mlistThanhPhan.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }else{
            // xuất ra cái text cho người dùng biết là lỗi tải dữ liệu
            Toast.makeText(this, getText(R.string.loitaidulieu).toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void dialogChonKhuVuc(final Button btnChonKhuVuc){
        // gán danh sach khu vực cho listKhuVuc
        listKhuVuc = mGetDataApp.getListKhuVuc();
        // nếu tồn tại giá trị trong list khu vực
        if(listKhuVuc!=null){
            //SETUP LIST NAME KHU VUC có độ lớn bằng độ lớn list khu vực
            int size = listKhuVuc.size();
            listNameKhuVuc = new String[size];
            // duyệt hết listkhuvu và lấy tên tỉnh gán vào listNameKhuVuc
            for (int i=0; i<size; i++){
                listNameKhuVuc[i]=listKhuVuc.get(i).getChitietkhuvuc().getTentinh();
            }
            // khởi tạo một dialog và truyền vào màn hình hiện tại
            android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(this);
            // khi click bên ngoài dialog thì dialog ko biến mất
            mBuilder.setCancelable(false);
            // set Tiêu đề cho dialog
            mBuilder.setTitle(getText(R.string.chonkhuvuc));
            // set từng iteam
            mBuilder.setItems(listNameKhuVuc, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // lấy vị trí item đã chọn gán cho objkhuvuc_app
                    mObjKhuVuc = listKhuVuc.get(i);
                    // lấy text tên tỉnh đã chọn gán cho phần text button
                    btnChonKhuVuc.setText(mObjKhuVuc.getChitietkhuvuc().getTentinh());
                }
            });
            //tạo một button hủy
            mBuilder.setPositiveButton(getText(R.string.huy), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //nếu click vào thì bỏ qua dialog
                    dialogInterface.dismiss();
                }
            });
            // tạo dialog
            Dialog dialog = mBuilder.create();
            // show dialog
            dialog.show();

        }else
            // xuất hiện đoạn text báo lỗi khu vực cho người dùng biết
            Toast.makeText(this, getText(R.string.loikhuvuc), Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        // ánh xạ tới dao diện
        toolbar = findViewById(R.id.toolbarSearch);
        mGetDataApp = new getDataApp(this);
        tvTien = findViewById(R.id.tvgiatientoida);
        sbTien = findViewById(R.id.seekbartien);
        rltlHien = findViewById(R.id.rltlHienLoc);
        lnlLoc = findViewById(R.id.lnlLoc);
        imvAn = findViewById(R.id.imvan);
        btnChonLaiKV   = findViewById(R.id.btnchonlaikhuvuc);
        btnLoc = findViewById(R.id.btnloc);
        edtTenMon = findViewById(R.id.edtTenMonAn);
        spinnerThanhPhan = findViewById(R.id.spinnerthanhphan);
        rclvSearch = findViewById(R.id.rclvSearch);
        mDialogLoading = new CustomDialogLoading(this);
        rdoMonAn = findViewById(R.id.rdothucan);
        rdoNuocUong = findViewById(R.id.rdonuocuong);
        // khởi tạo đối tượng Filter
        mFilter = new lfilter(this);
        // setup toolbar thành actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void actionTien() {
        // lắng nghe sự kiện thay đổi giá trị của SeeBar
        sbTien.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // tạo đôi tượng decimalFormat định dạng theo pattern
                DecimalFormat decimalFormat = new DecimalFormat("###,###");
                // lấy nội dung truyền vào TextView vs định dạng
                tvTien.setText(decimalFormat.format(i) +" đ");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void actionAnHienLoc() {
        //lắng nghe sự kiện cick
        rltlHien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hiện linearlayout lọc
                lnlLoc.setVisibility(View.VISIBLE);
                // ẩn relativelayout lọc
                rltlHien.setVisibility(View.GONE);
            }
        });

        imvAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ẩn linearlayout Lọc
                lnlLoc.setVisibility(View.GONE);
                //hiện reativelayout
                rltlHien.setVisibility(View.VISIBLE);
            }
        });


    }

    private void actionChonLaiKhuVuc() {
        // lắng nghe sự kiện click cho button chọn khu vực
        btnChonLaiKV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gọi hàm dialogChonKhuVuc và truyền vào button
                dialogChonKhuVuc(btnChonLaiKV);
            }
        });
    }



    @Override
    public void loitaidulieu_filter() {
        // chỉ xuất ra đoạn text lỗi tải khu vực cho app
        Toast.makeText(this, getText(R.string.loitaidulieu), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ketquatimkiem(ArrayList<objbaiviet_app> baiviet, boolean coketqua) {

        //TẢI XONG DỮ LIỆU BỎ QUA DIALOG
        mDialogLoading.dismissDialog();
        // NẾU có kết quả
        if(coketqua) {
            // hiện recyclerview
            rclvSearch.setVisibility(View.VISIBLE);
            // ẩn linearlayout
            lnlLoc.setVisibility(View.GONE);
            //hiện reativelayout
            rltlHien.setVisibility(View.VISIBLE);
            // gán danh sách bài viết của app cho danh sách kết quả tìm kiếm
            mListResultSearch = baiviet;
            // khởi tạo adapter Search truyền vào màn hình hiện tại, bào danh sách bài viết
            adapterSearch = new aRclvDacSan(this, baiviet);
            // khởi tạo một Linearlayout
            LinearLayoutManager manager = new LinearLayoutManager((Context) this, LinearLayoutManager.VERTICAL, false);
           // cân đối dao diện
            rclvSearch.setHasFixedSize(true);
            // gán linearlayout cho recyclerview
            rclvSearch.setLayoutManager(manager);
            // đỗ dữ liệu vào dao diện
            rclvSearch.setAdapter(adapterSearch);
            // cập nhật dữ liệu lại
            adapterSearch.notifyDataSetChanged();
            // lắng nghe sự kiện click cho adaptersearch
            adapterSearch.setOnItemClickListener(new aRclvDacSan.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    // khởi tạo bundle
                    Bundle bundle = new Bundle();
                    //put dữ liệu vào mListReasultSearch với key baiviet
                    bundle.putSerializable("baiviet",mListResultSearch.get(position));
                    if(rdoMonAn.isChecked())
                        // nếu radiobutton monan đk check thì put dữ liệu vào với key "loai" và "monan"
                        bundle.putString("loai","monan");
                    else
                        //put dữ liệu vào với key "loai" và "nuocuong"
                        bundle.putString("loai","nuocuong");
                    //chuyển giao diện sang giao diện chi tiết bài viết
                    Intent intent = new Intent(SearchActivity.this,ChiTietBaiVietActivity.class);
                    intent.putExtra("data",bundle);
                    startActivity(intent);
                }
            });


        }else{
            // ẩn recyclerView
            rclvSearch.setVisibility(View.GONE);
            //xuất đoạn text cho người dùng biết
            Toast.makeText(this, getText(R.string.khongcodulieu), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
