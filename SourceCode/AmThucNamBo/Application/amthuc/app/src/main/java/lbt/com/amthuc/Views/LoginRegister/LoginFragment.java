package lbt.com.amthuc.Views.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;

import lbt.com.amthuc.Presenters.LoginRegister.ilogin;
import lbt.com.amthuc.Presenters.LoginRegister.llogin;
import lbt.com.amthuc.R;
import lbt.com.amthuc.utils.CountryData;
import lbt.com.amthuc.utils.CustomDialogLoading;
import lbt.com.amthuc.models.objectClass.app.objnguoidung_app;

import static android.support.constraint.Constraints.TAG;


public class LoginFragment<val> extends Fragment implements ilogin{

    Button btnLogin,btnverry,btnloginsdt, btnLoginWithAccount,btnLoginWithPhone, btnguilai;
    TextInputLayout tilEmail,tilPwd, tilsdt,tilcode;

    LinearLayout lnlVerryCode,lnlLoginAccount,lnlLoginPhone, lnlnhapsdt;



    String codeSent;

    TextView tvCodePhone;
    SignInButton btnSignInGoogle;

    llogin mLogin;

    FirebaseAuth mAuth;


    CustomDialogLoading mDialogLoading;

    //
    GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient mGoogleApiClient;
    public static int REQUESTCODE_DANGNHAP_GOOGLE = 99;
    public static int KIEMTRA_PROVIDER_DANGNHAP = 0;
    int RC_SIGN_IN = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);


        initView(v);
        actionLogin();


        return v;
    }

    /////////////////
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }





    private void actionLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = tilEmail.getEditText().getText().toString();
                String pwd = tilPwd.getEditText().getText().toString();

                if(mLogin.checkLogic(tilEmail,tilPwd)){
                    mDialogLoading.showDialog(getText(R.string.dangdangnhap).toString());
                    tilEmail.setEnabled(false);
                    tilPwd.setEnabled(false);
                    mLogin.loginWithEmailPassword(email,pwd);
                }else{
                    Toast.makeText(getContext(), getText(R.string.dulieukhonghople), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }







    private void initView(View v) {

        btnLogin = v.findViewById(R.id.btnlogin_login);
        btnguilai = v.findViewById(R.id.btnguilaima_login);
        btnloginsdt = v.findViewById(R.id.btnloginsdt_login);
        btnLoginWithAccount = v.findViewById(R.id.btnLoginWithAccount);
        btnverry = v.findViewById(R.id.btnloginsdtverycode_login);
        tilEmail = v.findViewById(R.id.tilemail_login);
        tilPwd = v.findViewById(R.id.tilpwd_login);
        tilcode = v.findViewById(R.id.tilcode_login);
        tilsdt = v.findViewById(R.id.tilsdt_login);
        lnlVerryCode = v.findViewById(R.id.lnlverrycode);
        lnlLoginAccount = v.findViewById(R.id.lnldangnhapemail);
        lnlLoginPhone = v.findViewById(R.id.lnldangnhapsdt);
        lnlnhapsdt = v.findViewById(R.id.lnlnhapsdt);
        tvCodePhone = v.findViewById(R.id.tvcodePhone);


        mDialogLoading = new CustomDialogLoading(getContext());

        mLogin = new llogin(this,getContext());
        mAuth = FirebaseAuth.getInstance();



    }

    @Override
    public void ResultLogin(boolean isSuccess) {
        tilEmail.setEnabled(true);
        tilPwd.setEnabled(true);
        if(isSuccess){
            getActivity().finish();
        }else
            Toast.makeText(getContext(), getText(R.string.dangnhapthatbai), Toast.LENGTH_SHORT).show();
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
