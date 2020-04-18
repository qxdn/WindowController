package com.sunbest.view.details;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunbest.R;
import com.sunbest.databinding.RoofInfoFragmentBinding;
import com.sunbest.listener.AirListener;
import com.sunbest.listener.WeatherListener;
import com.sunbest.model.Air;
import com.sunbest.model.Weather;
import com.sunbest.service.WeatherService;
import com.sunbest.service.impl.WeatherServiceImpl;
import com.sunbest.viewmodel.RoofInfoViewModel;
import java.util.Objects;


public class RoofInfoFragment extends Fragment {
    private static final String TAG="RoofInfoFragment";

    private RoofInfoViewModel mViewModel;

    private Handler handler;

    private Runnable runnable;

    private WeatherService weatherService= WeatherServiceImpl.getInstance();

    //1分钟
    private long weatherDelay=5000;

    public static RoofInfoFragment newInstance() {
        return new RoofInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(getActivity()).get(RoofInfoViewModel.class);
        final RoofInfoFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.roof_info_fragment, container, false);
        mViewModel.getWeather().observe(requireActivity(), new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                Log.d(TAG, "on Weather Changed");
                // 更新ui
                // 温度
                binding.textView5.setText(weather.getTemp());
                // 天气状态
                binding.textView19.setText(weather.getState());
                // 风向
                binding.textView28.setText(weather.getWind_dir());
                // 风速
                binding.textView27.setText(weather.getWind_sc());
            }
        });
        mViewModel.getAir().observe(requireActivity(), new Observer<Air>() {
            @Override
            public void onChanged(Air air) {
                // 更新ui
                // PM2.5
                binding.textView23.setText(air.getPm25());
                // 空气质量指数
                binding.textView29.setText(air.getAqi());
            }
        });
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                weatherService.getWeather(requireContext(), new WeatherListener() {
                    @Override
                    public void onGetWeather(Weather weather) {
                        mViewModel.getWeather().postValue(weather);
                    }
                });
                weatherService.getAir(requireContext(), new AirListener() {
                    @Override
                    public void onGetAir(Air air) {
                        mViewModel.getAir().postValue(air);
                    }
                });
                //持续进行
                handler.postDelayed(this, weatherDelay);
            }
        };
        //10ms后启动
        handler.postDelayed(runnable, 10);
        binding.setData(mViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,TAG+ "onDestroy");
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
