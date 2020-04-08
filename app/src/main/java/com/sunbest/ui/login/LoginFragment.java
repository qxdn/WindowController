package com.sunbest.ui.login;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sunbest.MainActivity;
import com.sunbest.R;
import com.sunbest.databinding.FragmentLoginBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final FragmentLoginBinding loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        loginBinding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(loginBinding.editTextUserName.getText().toString(), loginBinding.editTextPassword.getText().toString());
            }
        });

        return loginBinding.getRoot();
    }

    private void login(String username, String password) {
        //模拟登录
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(requireContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(requireContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.equals(username, "admin") && TextUtils.equals(password, "admin")) {
            Toast.makeText(requireContext(), "登录成功", Toast.LENGTH_LONG).show();
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_loginFragment_to_homeFragment);
        } else {
            Toast.makeText(requireContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}
