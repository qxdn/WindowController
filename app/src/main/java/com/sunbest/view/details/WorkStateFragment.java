package com.sunbest.view.details;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunbest.R;
import com.sunbest.databinding.WorkStateFragmentBinding;
import com.sunbest.model.RoofState;
import com.sunbest.model.WindowsState;
import com.sunbest.service.MqttClientService;
import com.sunbest.service.impl.MqttClientServiceImpl;
import com.sunbest.viewmodel.WorkStateViewModel;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

public class WorkStateFragment extends Fragment {

    private static final String TAG="WorkStateFragment";

    private WorkStateViewModel mViewModel;

    private MqttClientService client= MqttClientServiceImpl.getInstance();

    public static WorkStateFragment newInstance() {
        return new WorkStateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel= ViewModelProviders.of(getActivity()).get(WorkStateViewModel.class);
        final WorkStateFragmentBinding binding= DataBindingUtil.inflate(inflater,R.layout.work_state_fragment,container,false);
        mViewModel.getRoofState().observe(requireActivity(), new Observer<RoofState>() {
            @Override
            public void onChanged(RoofState roofState) {
                Log.d(TAG,"on roofState Change");
                binding.textView44.setText(roofState.getRuntime());
                binding.textView45.setText(roofState.getElectricState());
                //TODO:完善
                if(roofState.getWindowsStates().size()>0) {
                    List<String> datasets = new ArrayList<>();
                    for (WindowsState windowsState : roofState.getWindowsStates()) {
                        datasets.add("天窗" + roofState.getWindowsStates().indexOf(windowsState));
                    }
                    binding.windowsSpinner1.attachDataSource(datasets);
                    binding.windowsSpinner2.attachDataSource(datasets);
                }else {

                }
            }
        });
        binding.sendAngle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String angle=binding.angle.getText().toString();
                //TODO:验证
                if(client.isConnected()){
                    client.controlAngle(Double.parseDouble(angle));
                }
            }
        });

        binding.setData(mViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }



}
