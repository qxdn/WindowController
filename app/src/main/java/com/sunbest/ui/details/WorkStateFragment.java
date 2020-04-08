package com.sunbest.ui.details;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.sunbest.R;
import com.sunbest.databinding.FragmentWorkStateBinding;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkStateFragment extends Fragment {

    public WorkStateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentWorkStateBinding binding= DataBindingUtil.inflate(inflater,R.layout.fragment_work_state, container, false);
        NiceSpinner niceSpinner1=binding.windowsSpinner1;
        NiceSpinner niceSpinner2=binding.windowsSpinner2;

        Button sendButton=binding.sendAngle;
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(),"发送成功",Toast.LENGTH_LONG).show();
            }
        });

        List<String> datasets=new ArrayList<>();
        for (int i=0;i<4;i++){
            datasets.add("天窗"+i);
        }
        niceSpinner1.attachDataSource(datasets);
        niceSpinner2.attachDataSource(datasets);

        return binding.getRoot();
    }
}
