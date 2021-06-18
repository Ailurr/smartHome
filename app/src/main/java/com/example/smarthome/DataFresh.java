package com.example.smarthome;

import android.util.Log;

import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 获取Socket通信数据 的单例类
 *
 * @auther: ChenYiXing
 * @date 2021/6/18/018 8:53
 **/
public class DataFresh {
    public Socket socket;
    public InputStream inputStream;

    public float temperature;
    public float humility;
    public float light;
    public float electricity;
    public float machine;
    public float xAxis;
    public float yAxis;
    public float zAxis;

    private DataFresh() {
    }

    //静态内部类在使用时加载,并且是线程安全的
    private static class DataFreshInstance {
        private static final DataFresh INSTANCE = new DataFresh();
    }

    /**
     * 获取单例类
     *
     * @return com.example.smarthome.DataFresh
     * @Author: ChenYiXing
     * @date 2021/6/18/018 9:07
     **/
    public static synchronized DataFresh getInstance() {

        return DataFreshInstance.INSTANCE;
    }

    /**
     * 获取Socket连接
     *
     * @param ip   IP地址
     * @param port 端口号
     * @author: ChenYiXing
     * @date 2021/6/18/018 8:58
     **/
    public void getConnect(final String ip, final int port) throws Exception {

        socket = new Socket(ip, port);
        inputStream = socket.getInputStream();
        byte[] bytes = new byte[30];

        while (true) {
            //读取服务器数据
            inputStream.read(bytes);
            //准备一个结构体类
            ConverEnvInfo cei = new ConverEnvInfo();
            //把b转换为cei的数据
            cei.setByteBuffer(ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN), 0);
            //拿到八个数据
            temperature = cei.temperature.get();
            humility = cei.humidity.get();
            light = cei.ill.get();
            electricity = cei.bet.get();
            machine = cei.adc.get();
            xAxis = cei.x.get();
            yAxis = cei.y.get();
            zAxis = cei.z.get();
            Log.d("MainActivity", "温度：" + temperature);
            Log.d("MainActivity", "湿度：" + humility);
            Log.d("MainActivity", "光照：" + light);
            Log.d("MainActivity", "电压：" + electricity);
            Log.d("MainActivity", "电位器：" + machine);
            Log.d("MainActivity", "x：" + xAxis);
            Log.d("MainActivity", "y：" + yAxis);
            Log.d("MainActivity", "z：" + zAxis);
        }

    }

}
