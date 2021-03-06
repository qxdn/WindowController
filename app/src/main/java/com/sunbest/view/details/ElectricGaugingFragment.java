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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.sunbest.R;
import com.sunbest.databinding.ElectricGaugingFragmentBinding;
import com.sunbest.model.ElectricState;
import com.sunbest.service.MqttClientService;
import com.sunbest.service.impl.MqttClientServiceImpl;
import com.sunbest.util.DateUtil;
import com.sunbest.viewmodel.ElectricGaugingViewModel;

import java.util.ArrayList;
import java.util.List;

public class ElectricGaugingFragment extends Fragment {
    private static final String TAG="ElectricGaugingFragment";

    private ElectricGaugingViewModel mViewModel;

    private Handler handler;
    private Runnable runnable;
    private long delayTime=60*60*1000;
    private MqttClientService client= MqttClientServiceImpl.getInstance();

    public static ElectricGaugingFragment newInstance() {
        return new ElectricGaugingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(getActivity()).get(ElectricGaugingViewModel.class);
        final ElectricGaugingFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.electric_gauging_fragment, container, false);
        LineChart lineChart= binding.lineChart;
        Description description = new Description();
        description.setText("日期");
        description.setTextSize(10);
        lineChart.setDescription(description);
        //不显示边界
        lineChart.setDrawBorders(false);
        //设置数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            entries.add(new Entry(i, (float) (Math.random()) * 80));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "发电量(kW/h)");
        lineDataSet.setDrawCircles(true);
        lineDataSet.setValueTextSize(10f);
        LineData data = new LineData(lineDataSet);
        lineChart.setData(data);
        //设置X轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                List<String> list = DateUtil.getWeek();
                return list.get((int) value);
            }
        });
        //设置Y轴
        YAxis leftYAxis = lineChart.getAxisLeft();
        YAxis rightYAxis = lineChart.getAxisRight();
        rightYAxis.setEnabled(false);
        leftYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMinimum(0f);
        leftYAxis.enableGridDashedLine(10f, 10f, 0f);
        rightYAxis.setDrawGridLines(false);

        mViewModel.getElectricState().observe(requireActivity(), new Observer<ElectricState>() {
            @Override
            public void onChanged(ElectricState electricState) {
                Log.d(TAG,"electricState Change");
                //TODO:
                binding.textView14.setText(electricState.getAllDayElectric()+"KW/h");
                binding.textView15.setText(electricState.getWeeklyElectric()+"kW/h");
                binding.textView16.setText(electricState.getAverageDayElectric()+"KW/h");
                binding.textView17.setText(electricState.getLastHourElectric()+"KW/h");
                lineChart.setData(array2LineData(electricState.getaWeekElectrics()));
                //通知数据改变
                lineChart.notifyDataSetChanged();
                //更新数据
                lineChart.invalidate();
            }
        });

        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                if(client.isConnected()){
                    client.toGetElectricState();
                }
                //持续进行
                handler.postDelayed(this, delayTime);
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
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    private LineData array2LineData(double[] array){
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            entries.add(new Entry(i, (float) (array[i])));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "发电量(kW/h)");
        lineDataSet.setDrawCircles(true);
        lineDataSet.setValueTextSize(10f);
        LineData data = new LineData(lineDataSet);
        return data;
    }
}
