package com.sunbest.view.details;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.sunbest.R;
import com.sunbest.databinding.ChangePasswordFragmentBinding;
import com.sunbest.util.AppUtil;
import com.sunbest.viewmodel.ChangePasswordViewModel;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangePasswordFragment extends Fragment {

    private static final String TAG="ChangePasswordFragment";

    private ChangePasswordViewModel mViewModel;

    private String url;

    private String email;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final SharedPreferences pref =requireActivity().getSharedPreferences("userinfo",requireActivity().MODE_PRIVATE);
        email=pref.getString("email","");
        url=getString(R.string.remoteServer);
        mViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        ChangePasswordFragmentBinding binding= DataBindingUtil.inflate(inflater,R.layout.change_password_fragment,container,false);
        binding.showChangePasswordSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    binding.newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    binding.confirmChangePass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {
                    binding.newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                    binding.confirmChangePass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                }
            }
        });
        binding.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass=binding.newPassword.getText().toString();
                String confirmPass=binding.confirmChangePass.getText().toString();
                if(newPass==null||confirmPass==null){
                    return;
                }
                if(!newPass.equals(confirmPass)){
                    showCancelableDialog("错误","两次密码不一样",R.drawable.nofig2);
                    return;
                }
                if(newPass.length()<6||newPass.length()>12){
                    showCancelableDialog("错误","新密码长度应该在6~12之间",R.drawable.nofig2);
                    return;
                }
                OkHttpClient client=new OkHttpClient();
                RequestBody requestBody=new FormBody.Builder()
                        .add("email",email)
                        .add("password",newPass)
                        .build();
                Request request=new Request.Builder()
                        .url(url+"/api/v1/androidChangePassword")
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i(TAG,"failure");
                        Looper.prepare();
                        showCancelableDialog("修改失败",null,R.drawable.nofig2);
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i(TAG,"success");
                        String json=response.body().string();
                        Log.i(TAG, "onResponse: "+json);
                        if(json.equals("true")){
                            Looper.prepare();
                            new AlertDialog.Builder(requireContext()).setTitle("修改成功")
                                    .setMessage("修改密码成功，需要重启")
                                    .setIcon(R.drawable.okfig1)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            AppUtil.reboot(requireContext());
                                        }
                                    })
                                    .setCancelable(false)
                                    .show();
                            Looper.loop();
                        }else {
                            Looper.prepare();
                            showCancelableDialog("修改失败",null,R.drawable.nofig2);
                            Looper.loop();
                        }
                    }
                });
            }
        });
        binding.setData(mViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
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
