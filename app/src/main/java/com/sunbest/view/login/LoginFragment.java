package com.sunbest.view.login;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sunbest.R;
import com.sunbest.databinding.LoginFragmentBinding;
import com.sunbest.viewmodel.LoginViewModel;
import com.sunbest.viewmodel.UserCenterViewModel;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private LoginFragmentBinding binding;
    private static final String TAG="LoginFragment";
    private String url;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final SharedPreferences pref =requireActivity().getSharedPreferences("userinfo",requireActivity().MODE_PRIVATE);
        url=getString(R.string.remoteServer);
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false);
        binding.editTextUserName.setText(pref.getString("email",""));
        binding.setData(mViewModel);
        binding.setLifecycleOwner(this);
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断用户名和密码待实现。。。
                String email=binding.editTextUserName.getText().toString();
                String pass=binding.editTextPassword.getText().toString();

                OkHttpClient client=new OkHttpClient();

                RequestBody requestBody=new FormBody.Builder()
                        .add("email",email)
                        .add("password",pass)
                        .build();

                Request request=new Request.Builder()
                        .url(url+"/api/v1/androidLogin")
                        .post(requestBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i(TAG,"failure");
                        Looper.prepare();
                        showCancelableDialog("错误","登录失败",R.drawable.nofig2);
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i(TAG,"success");
                        String json=response.body().string();
                        Log.i(TAG, "onResponse: "+json);
                        if(json.equals("true")){
                            SharedPreferences.Editor editor=pref.edit();
                            editor.putString("email",email);
                            editor.apply();
                            NavController controller = Navigation.findNavController(v);
                            controller.navigate(R.id.action_loginFragment_to_homeFragment);
                        }else {
                            Looper.prepare();
                            showCancelableDialog("错误","用户名或密码错误",R.drawable.nofig2);
                            Looper.loop();
                        }
                    }
                });

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

    }


    private void showCancelableDialog(String title,String msg,int iconId){
        new AlertDialog.Builder(requireContext()).setTitle(title)
                .setMessage(msg)
                .setIcon(iconId)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
