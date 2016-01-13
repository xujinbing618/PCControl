package com.pccontrol.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by 张建浩 on 2015/6/18.
 */
public class UdpSender {
    public static void  sendOrder(String ip, int orderCode) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte [] buf = (orderCode+"#").getBytes();
        DatagramPacket packet = new DatagramPacket(buf,buf.length,InetAddress.getByName(ip),10010);
        System.out.println("发送了");
        socket.send(packet);
        packet = null;
        socket.close();

    }
    public static void  sendOrder(String ip, int orderCode, String info) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte [] buf = (orderCode+"#"+info).getBytes("utf-8");
        DatagramPacket packet = new DatagramPacket(buf,buf.length,InetAddress.getByName(ip),10010);
        System.out.println("发送了");
        socket.send(packet);
        packet = null;
        socket.close();
    }

}
