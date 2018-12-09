package com.example.administrator.i;

public class Algorithm {
    sma sma1;

    int x;
    int y;

    Algorithm(sma sma) {
      sma1=sma;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double algorithm(iBeacon now_ibeacon) {
       sma1.run(now_ibeacon.bluetoothAddress,now_ibeacon.rssi);//入队
       double buff=0;
       int temp=0;
       for(int i=0;i<5;i++)//循环遍历5个队列
       {
          if(sma1.arr_queue.get(i).full())//如果大于5)
          {
              for (int j = i + 1; j < 5; j++)
                  if (sma1.arr_queue.get(j).full())//如果大于5
                  {
                      buff+=twopoint(sma1.arr_ineacon_device.get(i),sma1.arr_ineacon_device.get(j),sma1.arr_queue.get(i).getRelute(),sma1.arr_queue.get(j).getRelute());
                      temp++;
                  }
          }

       }
        buff=buff/temp;
      return buff;

        }

        public  double twopoint(IbeaconDevice now_ibeacon,IbeaconDevice last_ibeacon,double now_dis,double last_dis)
        {
            double relute = 0;
            double buf1, buf2;
            if (now_dis + last_dis > Math.abs(now_ibeacon.y - last_ibeacon.y))//如果是在两点外
            {
                if (now_ibeacon.y > last_ibeacon.y)//now是距离更远的信标
                {
                    buf1 = now_ibeacon.y + now_dis;
                    buf2 = last_ibeacon.y + last_dis;
                    relute = (buf1 + buf2) / 2;
                } else//now是距离更近的信标
                {
                    buf1 = now_ibeacon.y - now_dis;
                    buf2 = last_ibeacon.y - last_dis;
                    relute = (buf1 + buf2) / 2;
                }

            } else//在两点内
            {
                if (now_ibeacon.y > last_ibeacon.y)//now是距离更远的信标
                {
                    buf1 = now_ibeacon.y - now_dis;
                    buf2 = last_ibeacon.y + last_dis;
                    relute = (buf1 + buf2) / 2;
                } else//last是距离更远的信标
                {
                    buf1 = now_ibeacon.y + now_dis;
                    buf2 = last_ibeacon.y - last_dis;
                    relute = (buf1 + buf2) / 2;
                }
            }
            return relute;

        }



}

