package com.example.administrator.i;


public class IbeaconDevice {


        public int major;
        public int minor;
        public String proximityUuid;
        public String bluetoothAddress;
        int x;
        int y;

        public  IbeaconDevice(String bluetoothAddress,int x,int y)
        {
              this.bluetoothAddress=bluetoothAddress;
              this.x=x;
              this.y=y;
              major=10;
              minor=7;
              proximityUuid="fda50693-a4e2-4fb1-afcf-c6eb07647825";
        }

        public int getMajor() {
            return major;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getMinor() {
            return minor;
        }

        public String getBluetoothAddress() {
            return bluetoothAddress;
        }

        public String getProximityUuid() {
            return proximityUuid;
        }





}
