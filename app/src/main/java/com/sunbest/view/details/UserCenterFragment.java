package com.sunbest.view.details;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunbest.R;
import com.sunbest.viewmodel.UserCenterViewModel;

public class UserCenterFragment extends Fragment {

    private UserCenterViewModel mViewModel;

    public static UserCenterFragment newInstance() {
        return new UserCenterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_center_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserCenterViewModel.class);
        // TODO: Use the ViewModel
    }

}
