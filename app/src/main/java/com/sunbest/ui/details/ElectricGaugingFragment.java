package com.sunbest.ui.details;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.sunbest.R;
import com.sunbest.databinding.FragmentElectricGaugingBinding;
import com.sunbest.domain.VtDateValueBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ElectricGaugingFragment extends Fragment {

    public ElectricGaugingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentElectricGaugingBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_electric_gauging, container, false);
        LineChart lineChart = binding.lineChart;
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
                List<String> list = new ArrayList<>();
                list.add("4月1日");
                list.add("4月2日");
                list.add("4月3日");
                list.add("4月4日");
                list.add("4月5日");
                list.add("4月6日");
                list.add("4月7日");
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
        return binding.getRoot();
    }
}