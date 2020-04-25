package com.sunbest.view.home;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunbest.R;
import com.sunbest.databinding.HomeFragmentBinding;
import com.sunbest.model.HardwareState;
import com.sunbest.service.MqttClientService;
import com.sunbest.service.impl.MqttClientServiceImpl;
import com.sunbest.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private static final String TAG="HomeFragment";
    private HomeViewModel mViewModel;
    private NavController controller;
    private MqttClientService client= MqttClientServiceImpl.getInstance();

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        HomeFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        mViewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel.class);
        binding.setData(mViewModel);
        binding.setLifecycleOwner(this);
        mViewModel.getHardwareState().observe(requireActivity(), new Observer<HardwareState>() {
            @Override
            public void onChanged(HardwareState hardwareState) {
                Log.d(TAG, "hardwareState changed"+hardwareState.toString());
                if(hardwareState!=null){
                    binding.switch1.setChecked(hardwareState.isSmarted());
                    if(hardwareState.isWorked()){
                        binding.imageView.setImageResource(R.drawable.okfig1);
                    }else {
                        binding.imageView.setImageResource(R.drawable.nofig2);
                    }
                }
            }
        });
        binding.switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked=binding.switch1.isChecked();
                client.setSmartControl(isChecked);
            }
        });
        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(client.isConnected()){
                    client.emergency();
                }
            }
        });
        binding.switch1.setChecked(true);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_homeFragment_to_roofInfoFragment);
            }
        });
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_homeFragment_to_workStateFragment);
            }
        });
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_homeFragment_to_electricGaugingFragment);
            }
        });
        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_homeFragment_to_userCenterFragment);
            }
        });
        return binding.getRoot();
    }
}
