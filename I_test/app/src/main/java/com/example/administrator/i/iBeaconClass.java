package com.example.administrator.i;

import android.bluetooth.BluetoothDevice;

class iBeaconClass {

    static Kalman kalman1=new Kalman(16,100);//5个卡尔曼滤波器分别对5个ibeacon滤波
    static Kalman kalman2=new Kalman(16,100);
    static Kalman kalman3=new Kalman(16,100);
    static Kalman kalman4=new Kalman(16,100);
    static Kalman kalman5=new Kalman(16,100);
    static IbeaconDevice dev1 = new IbeaconDevice("88:3F:4A:EA:50:8E", 0, 0);
    static IbeaconDevice dev2 = new IbeaconDevice("88:3F:4A:EA:4D:CE", 0, 10);
    static IbeaconDevice dev3 = new IbeaconDevice("88:3F:4A:EA:4D:D6", 0, 20);
    static IbeaconDevice dev4 = new IbeaconDevice("88:3F:4A:EA:4D:B3", 0, 30);
    static IbeaconDevice dev5 = new IbeaconDevice("88:3F:4A:EA:50:85", 0, 40);

    //解析数据
    static iBeacon fromScanData(BluetoothDevice device, int rssi, byte[] scanData,sma sma1) {
      // rssi=sma1.run(device.getAddress(),rssi);//平滑数据算法
        //卡尔曼滤波
        if (device.getAddress().equals(iBeaconClass.dev1.bluetoothAddress))
         rssi=(int)iBeaconClass.kalman1.KalmanFilter((double) rssi);
        if (device.getAddress().equals(iBeaconClass.dev2.bluetoothAddress))
            rssi=(int)iBeaconClass.kalman2.KalmanFilter((double) rssi);
        if (device.getAddress().equals(iBeaconClass.dev3.bluetoothAddress))
            rssi=(int)iBeaconClass.kalman3.KalmanFilter((double) rssi);
        if (device.getAddress().equals(iBeaconClass.dev4.bluetoothAddress))
            rssi=(int)iBeaconClass.kalman4.KalmanFilter((double) rssi);
        if (device.getAddress().equals(iBeaconClass.dev5.bluetoothAddress))
            rssi=(int)iBeaconClass.kalman5.KalmanFilter((double) rssi);

        int startByte = 2;
        boolean patternFound = false;
        while (startByte <= 5) {
            if (((int) scanData[startByte + 2] & 0xff) == 0x02 &&
                    ((int) scanData[startByte + 3] & 0xff) == 0x15) {
                // yes!  This is an iBeacon
                patternFound = true;

                System.out.println("from scandata =============0");
                break;
            } else if (((int) scanData[startByte] & 0xff) == 0x2d &&
                    ((int) scanData[startByte + 1] & 0xff) == 0x24 &&
                    ((int) scanData[startByte + 2] & 0xff) == 0xbf &&
                    ((int) scanData[startByte + 3] & 0xff) == 0x16) {
                iBeacon iBeacon = new iBeacon();
                iBeacon.major = 0;
                iBeacon.minor = 0;
                iBeacon.proximityUuid = "00000000-0000-0000-0000-000000000000";
                iBeacon.txPower = -55;
                iBeacon.distance = getDistance(iBeacon.txPower, rssi);

                System.out.println("from scan data0: distance = " + iBeacon.distance
                        + " rssi= " + rssi);
                return iBeacon;
            } else if (((int) scanData[startByte] & 0xff) == 0xad &&
                    ((int) scanData[startByte + 1] & 0xff) == 0x77 &&
                    ((int) scanData[startByte + 2] & 0xff) == 0x00 &&
                    ((int) scanData[startByte + 3] & 0xff) == 0xc6) {

                iBeacon iBeacon = new iBeacon();
                iBeacon.major = 0;
                iBeacon.minor = 0;
                iBeacon.proximityUuid = "00000000-0000-0000-0000-000000000000";
                iBeacon.txPower = -55;
                iBeacon.distance = getDistance(iBeacon.txPower, rssi);
                System.out.println("from scan data1: distance = " + iBeacon.distance
                        + " rssi= " + rssi);
                return iBeacon;
            }
            startByte++;
        }


        if (patternFound == false) {
            // This is not an iBeacon
            return null;
        }

        iBeacon iBeacon = new iBeacon();
        iBeacon.major = (scanData[startByte + 20] & 0xff) * 0x100 + (scanData[startByte + 21] & 0xff);
        iBeacon.minor = (scanData[startByte + 22] & 0xff) * 0x100 + (scanData[startByte + 23] & 0xff);
        iBeacon.txPower = (int) scanData[startByte + 24]; // this one is signed
        iBeacon.rssi = rssi;
        iBeacon.distance = getDistance(iBeacon.txPower, rssi);
        System.out.println("from scan data2: distance = " + iBeacon.distance
                + " rssi= " + rssi);

        // AirLocate:
        // 02 01 1a 1a ff 4c 00 02 15  # Apple's fixed iBeacon advertising prefix
        // e2 c5 6d b5 df fb 48 d2 b0 60 d0 f5 a7 10 96 e0 # iBeacon profile uuid
        // 00 00 # major
        // 00 00 # minor
        // c5 # The 2's complement of the calibrated Tx Power

        // Estimote:
        // 02 01 1a 11 07 2d 24 bf 16
        // 394b31ba3f486415ab376e5c0f09457374696d6f7465426561636f6e00000000000000000000000000000000000000000000000000

        byte[] proximityUuidBytes = new byte[16];
        System.arraycopy(scanData, startByte + 4, proximityUuidBytes, 0, 16);
        String hexString = bytesToHexString(proximityUuidBytes);
        StringBuilder sb = new StringBuilder();
        sb.append(hexString.substring(0, 8));
        sb.append("-");
        sb.append(hexString.substring(8, 12));
        sb.append("-");
        sb.append(hexString.substring(12, 16));
        sb.append("-");
        sb.append(hexString.substring(16, 20));
        sb.append("-");
        sb.append(hexString.substring(20, 32));
        iBeacon.proximityUuid = sb.toString();

        if (device != null) {
            iBeacon.bluetoothAddress = device.getAddress();
            iBeacon.name = device.getName();
        }

        return iBeacon;
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    //计算距离
    private static double getDistance(int txPower, int rssi) {
        if (0 == rssi) {
            return -1.00;
        }

        double ratio = (double) (rssi * 1.0) / (double) (txPower-3);
        System.out.println("txPower = " + (double) txPower
                + " rssi= " + (double) rssi
                + "ratio= " + ratio);

        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double distance = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return distance;
        }
    }


}
