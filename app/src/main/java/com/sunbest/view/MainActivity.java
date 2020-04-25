package com.sunbest.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.sunbest.R;
import com.sunbest.listener.MqttMessageListener;
import com.sunbest.model.ElectricState;
import com.sunbest.model.MqttSetting;
import com.sunbest.model.RoofState;
import com.sunbest.service.MqttClientService;
import com.sunbest.service.impl.MqttClientServiceImpl;
import com.sunbest.util.IDUtil;
import com.sunbest.viewmodel.ElectricGaugingViewModel;
import com.sunbest.viewmodel.WorkStateViewModel;

public class MainActivity extends AppCompatActivity {

    private static final int BAIDU_READ_PHONE_STATE = 100;//定位权限请求
    private static final int PRIVATE_CODE = 1315;//开启GPS权限

    private WorkStateViewModel workStateViewModel;
    private ElectricGaugingViewModel electricGaugingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED) {
            //请求权限
            String[] permissions = new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_PHONE_STATE
            };
            ActivityCompat.requestPermissions(this, permissions, PRIVATE_CODE);
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                finish();
            }
        }

        workStateViewModel= ViewModelProviders.of(this).get(WorkStateViewModel.class);
        electricGaugingViewModel=ViewModelProviders.of(this).get(ElectricGaugingViewModel.class);

        MqttClientService client= MqttClientServiceImpl.getInstance();
        MqttSetting setting=new MqttSetting();
        setting.setUsername("androidClient");
        setting.setPassword("123456");
        setting.setClientId(IDUtil.getDeviceID(getApplicationContext()));
        client.init(getApplicationContext(), setting, new MqttMessageListener() {
            @Override
            public void onRoofStateArrived(RoofState roofState) {
                workStateViewModel.getRoofState().postValue(roofState);
            }

            @Override
            public void onElectricStateArrived(ElectricState electricState) {
                electricGaugingViewModel.getElectricState().postValue(electricState);
            }
        });
    }
}
