package com.example.administrator.i;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.litesuits.bluetooth.LiteBluetooth;
import com.litesuits.bluetooth.scan.PeriodScanCallback;

public class MainActivity extends AppCompatActivity {


    private BluetoothAdapter mBluetoothAdapter;

    private TextView Textview1;
    private PointImageView mPointView;
    private Algorithm mtwoPointAlgorithm;
  //  private ArrayList<IbeaconDevice> mIbeaconDevice;
    private LiteBluetooth mBLE = null;
    private static int TIME_OUT_SCAN = 60000;
    private sma sma1;
    private PeriodScanCallback mScanCallback = new PeriodScanCallback(TIME_OUT_SCAN) {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            logDevice(device, rssi, scanRecord);
        }

        @Override
        public void onScanTimeout() {
            mBLE.startLeScan(mScanCallback);
            toast("扫描超时");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBLE = new LiteBluetooth(this);
        sma1=new sma();
        Textview1 = findViewById(R.id.textview1);
        mPointView = findViewById(R.id.imageview);

        //画一个点
        findViewById(R.id.mDrawBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPointView.drawPoint(new Point(30, 30));
            }
        });

        mtwoPointAlgorithm=new Algorithm(sma1);

        //开启蓝牙扫描
        findViewById(R.id.mStartBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mBLE.startLeScan(mScanCallback);
            }
        });

        //获取本机蓝牙
        getBlueToothDetail();
    }

    //获取蓝牙详情
    private void getBlueToothDetail() {
        if (mBLE == null) return;

        mBluetoothAdapter = mBLE.getBluetoothAdapter();
        if (mBluetoothAdapter == null) return;

        Textview1.setText("我的蓝牙：" + mBluetoothAdapter.getName() + "\n地址：" + mBluetoothAdapter.getAddress());
    }


    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("on resume====");

    }



    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private iBeacon ibeacon = null;

    private void logDevice(BluetoothDevice device, int rssi, byte[] scanRecord) {
      //  Textview1.append("\n"+device.getAddress());
      //  Textview1.append("getget");
        ibeacon = iBeaconClass.fromScanData(device, rssi, scanRecord,sma1);//先进行卡尔曼滤波
     //   Textview1.append("overover");
        if (ibeacon == null) return;

       // Textview1.append("\n蓝牙名称" + ibeacon.name);
        Textview1.setText("\n" +"蓝牙地址"+ibeacon.bluetoothAddress);
       // Textview1.append("\n" + "uuid" + ibeacon.proximityUuid);
      //  Textview1.append("\n" + "major" + ibeacon.major);
      // Textview1.append("\n" + "minor" + ibeacon.minor);
        Textview1.append("\n" + "rssi" + ibeacon.rssi);
        Textview1.append("\n" + "distance" + ibeacon.distance);
     //   Textview1.append("\n" + "power" + ibeacon.txPower);

        double t=mtwoPointAlgorithm.algorithm(ibeacon);
        Textview1.append("\nt=="+t);
        mPointView.drawPoint(new Point(mPointView.getWidth()/2,(int)((1-t/50)*mPointView.getHeight())));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mBLE.disableBluetooth();

        if (mBLE.isConnectingOrConnected()) {
            mBLE.closeBluetoothGatt();
        }

        if (mBLE.isInScanning()){
            mBLE.stopScan(mScanCallback);
        }
    }

}




