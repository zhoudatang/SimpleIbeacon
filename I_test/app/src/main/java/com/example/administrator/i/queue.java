package com.example.administrator.i;

public class queue {
    private float[] data ;// 队列
    private int front;// 队列头，允许删除
    private int rear;// 队列尾，允许插入
    private int LENGTH=5;
    private int full=0;
    private int t=0;//判断队列是否装满
    private  int relute=0;

    public queue() {
        data = new float[LENGTH];
        front = rear = 0;
    }

    // 入队
    public void offer(float date) {
        if (rear>=LENGTH)
        {
            rear=0;
        }
        data[rear++] = date;

        if (t++>=LENGTH)
        {
            full=1;
            //relute=(int)(data[0]+data[1]+data[2]+data[3]+data[4])/5;
            for (int i=0;i<LENGTH;i++)
            {
                relute=0;
                relute += data[i];
            }
        }

    }
    // 出队
    public float poll() {

        if (front<LENGTH)
        {
            float value = data[front];// 保留队列的front端的元素的值

            front++;
            return value;

        }
        else
        {
            front=0;
            float value = data[front];
            return  value;
        }

    }

    public boolean full()
    {
        if (full==1)
            return  true;
        else
            return false;
    }

    public int getRelute() {
        return relute;
    }
}
