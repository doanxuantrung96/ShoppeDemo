package com.example.shoppedemo.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoppedemo.R;
import com.example.shoppedemo.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import de.greenrobot.event.EventBus;

public class BlankFragment extends Fragment implements FirebaseAuth.AuthStateListener {
    EditText edtEmail,edtMatKhau1,edtMatKhau2;
    Button btnDangNhap,btnDangKi;
    FirebaseAuth firebaseAuth;
    private BlankViewModel mViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(BlankViewModel.class);
        View root = inflater.inflate(R.layout.blank_fragment, container, false);
        edtEmail=(EditText)root.findViewById(R.id.edtEmail);
        edtMatKhau1=(EditText)root.findViewById(R.id.edtMatKhau1);
        edtMatKhau2=(EditText)root.findViewById(R.id.edtMatKhau2);
        btnDangKi=(Button)root.findViewById(R.id.btnDangKi);
        btnDangNhap=(Button)root.findViewById(R.id.btnDangNhap);
        firebaseAuth=FirebaseAuth.getInstance();
        addEvents();
        return root;
    }

    private void addEvents() {
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((edtEmail.getText().toString().equals(""))||(edtMatKhau1.getText().toString().equals(""))||(edtMatKhau2.getText().toString().equals(""))) {
                    Toast.makeText(getContext(), "Điền đầy đủ thông tin!", Toast.LENGTH_LONG).show();
                }else {
                    //nếu mật khẩu lần 1,2 không  khớp hiển thị thông báo
                    if (edtMatKhau1.getText().toString().equals(edtMatKhau2.getText().toString())) {
                        Toast.makeText(getContext(), "Mật khẩu khớp!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Mật khẩu không khớp, nhập lại!", Toast.LENGTH_SHORT).show();
                    }
                    if (edtMatKhau1.getText().toString().equals(edtMatKhau2.getText().toString())) {
                        firebaseAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtMatKhau1.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //nếu dăng nhập thành công
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Tạo tài khoản thành công!", Toast.LENGTH_LONG).show();
                                }else{
                                    // đăng nhập thất bại tại vì email đã tồn tại
                                    Toast.makeText(getContext(), "Email đã tồn tại! ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nếu mật khẩu hoặc email chưa có hiển thị thông báo
                if (edtEmail.getText().toString().equals("") || edtMatKhau1.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Điền đầy đủ thông tin!", Toast.LENGTH_LONG).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtMatKhau1.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // nếu đăng nhập thành công
                            if (task.isSuccessful()) {
                                EventBus.getDefault().postSticky(edtEmail.getText().toString());
                                Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

    }
}
