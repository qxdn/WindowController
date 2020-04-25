package com.sunbest.view.details;

import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sunbest.R;
import com.sunbest.viewmodel.ChangePasswordViewModel;

public class ChangePasswordFragment extends Fragment {

    private ChangePasswordViewModel mViewModel;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final SharedPreferences pref =requireActivity().getSharedPreferences("userinfo",requireActivity().MODE_PRIVATE);
        mViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        return inflater.inflate(R.layout.change_password_fragment, container, false);
    }


}
