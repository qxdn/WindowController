package com.sunbest.view.details;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.sunbest.R;
import com.sunbest.databinding.UserCenterFragmentBinding;
import com.sunbest.util.IDUtil;
import com.sunbest.viewmodel.UserCenterViewModel;

public class UserCenterFragment extends Fragment {

    private UserCenterViewModel mViewModel;

    public static UserCenterFragment newInstance() {
        return new UserCenterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final SharedPreferences pref =requireActivity().getSharedPreferences("userinfo",requireActivity().MODE_PRIVATE);
        mViewModel= ViewModelProviders.of(getActivity()).get(UserCenterViewModel.class);
        UserCenterFragmentBinding binding= DataBindingUtil.inflate(inflater,R.layout.user_center_fragment,container,false);
        mViewModel.setDeviceId(new MutableLiveData<String>(IDUtil.getDeviceID(requireContext())));
        mViewModel.setVersion(new MutableLiveData<String>(IDUtil.getAppVersion(requireContext())));
        mViewModel.setEmail(new MutableLiveData<String>(pref.getString("email","")));
        mViewModel.getDeviceId().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.userCenterDeviceID.setText(s);
            }
        });
        mViewModel.getEmail().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.userCenterUserName.setText(s);
            }
        });
        mViewModel.getVersion().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.userCenterPackageVersion.setText(s);
            }
        });
        //重启
        binding.userCenterQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        binding.changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_userCenterFragment_to_changePasswordFragment);
            }
        });
        binding.setData(mViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }


}
