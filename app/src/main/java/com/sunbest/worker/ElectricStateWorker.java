package com.sunbest.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.sunbest.service.MqttClientService;
import com.sunbest.service.impl.MqttClientServiceImpl;

public class ElectricStateWorker extends Worker {

    private MqttClientService client= MqttClientServiceImpl.getInstance();

    public ElectricStateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        if(client.isConnected()){
            client.toGetElectricState();
            return Result.success();
        }
        return Result.failure();
    }


}
