package com.example.administrator.i;

import java.util.ArrayList;

public class sma {
    IbeaconDevice dev1, dev2, dev3, dev4, dev5;

    //private queue queue_x;
    public ArrayList<queue> arr_queue;//存放 5个ibeacon的动态数组 每个队列有5个值
   public  ArrayList<IbeaconDevice> arr_ineacon_device;
    //private  int LENGTH=5;

    sma()
    {
        arr_queue=new ArrayList<>();
        arr_ineacon_device=new ArrayList<>();
        dev1 = new IbeaconDevice("88:3F:4A:EA:50:8E", 0, 0);
        dev2 = new IbeaconDevice("88:3F:4A:EA:4D:CE", 0, 10);
        dev3 = new IbeaconDevice("88:3F:4A:EA:4D:D6", 0, 20);
        dev4 = new IbeaconDevice("88:3F:4A:EA:4D:B3", 0, 30);
        dev5 = new IbeaconDevice("88:3F:4A:EA:50:85", 0, 40);
        arr_queue.add(new queue());
        arr_queue.add(new queue());
        arr_queue.add(new queue());
        arr_queue.add(new queue());
        arr_queue.add(new queue());
        arr_ineacon_device.add(dev1);
        arr_ineacon_device.add(dev2);
        arr_ineacon_device.add(dev3);
        arr_ineacon_device.add(dev4);
        arr_ineacon_device.add(dev5);

    }

    public  int run(String address,int rssi)
    {


        if (address.equals(dev1.bluetoothAddress))
        {
            arr_queue.get(0).offer(rssi);
           /* if (arr_queue.get(0).full())
            {
                return arr_queue.get(0).getRelute();
            }*/
           // else
            {
                return 0;
            }
        }
        else if (address.equals(dev2.bluetoothAddress))
        {
            arr_queue.get(1).offer(rssi);
            /*if (arr_queue.get(1).full())
            {
                return arr_queue.get(1).getRelute();
            }
            else*/
            {
                return 0;
            }
        }
        else if (address.equals(dev3.bluetoothAddress))
        {
            arr_queue.get(2).offer(rssi);
           /* if (arr_queue.get(2).full())
            {
                return arr_queue.get(2).getRelute();
            }
            else*/
            {
                return 0;
            }
        }
        else if (address.equals(dev4.bluetoothAddress))
        {
            arr_queue.get(3).offer(rssi);
           /* if (arr_queue.get(3).full())
            {
                return arr_queue.get(3).getRelute();
            }
            else*/
            {
                return 0;
            }
        }
        else if (address.equals(dev5.bluetoothAddress))
        {
            arr_queue.get(4).offer(rssi);
           /* if (arr_queue.get(4).full())
            {
                return arr_queue.get(4).getRelute();
            }
            else*/
            {
                return 0;
            }
        }
        else {
            return 0;
        }

    }



}
