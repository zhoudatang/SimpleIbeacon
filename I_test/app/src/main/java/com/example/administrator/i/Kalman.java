package com.example.administrator.i;

public class Kalman {

    double startValue;  //k-1时刻的滤波值，即是k-1时刻的值
    double kalmanGain;   //   Kalamn增益
    double A;   // x(n)=A*x(n-1)+u(n),u(n)~N(0,Q)
    double H;   // z(n)=H*x(n)+w(n),w(n)~N(0,R)
    double Q;   //预测过程噪声偏差的方差
    double R;   //测量噪声偏差，(系统搭建好以后，通过测量统计实验获得)
    double P;   //估计误差协方差;
    public   Kalman(double Q1, double R1)//预测误差的方差,噪声误差的方差
    {
        A=1;
        H=1;
        P=10;//下一时刻的协方差，初始化随意
        Q=Q1;
        R=R1;
        startValue=50;
    }
    public double  KalmanFilter(double value)
    {
//预测下一时刻的值
        double predictValue = A* startValue;

        //求预测下一时刻的协方差
        P = A*A*P + Q;  //计算先验均方差 p(n|n-1)=A^2*p(n-1|n-1)+q
        //计算kalman增益
        kalmanGain = P*H / (P*H*H + R);  //Kg(k)= P(k|k-1) H’ / (H P(k|k-1) H’ + R)
        //修正结果，即计算滤波值
        startValue = predictValue + (value - predictValue)*kalmanGain;  //利用残余的信息改善对x(t)的估计，给出后验估计，这个值也就是输出  X(k|k)= X(k|k-1)+Kg(k) (Z(k)-H X(k|k-1))
        //更新后验估计
        P = (1 - kalmanGain*H)*P;//计算后验均方差  P[n|n]=(1-K[n]*H)*P[n|n-1]
        return  startValue;

    }

}

