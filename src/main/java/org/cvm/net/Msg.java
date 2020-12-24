package org.cvm.net;

import java.io.DataInputStream;
import java.net.DatagramSocket;

/**
 * 应用层协议接口
 */
public interface Msg {
    public static final int MOVE_MSG= 1;
    public static final int ATTACK_MSG = 2;
    public static final int DEAD_MSG = 3;
    public static final int BLOOD_MSG = 4;

    public void send(DatagramSocket ds, String IP, int UDP_Port);
    public void parse(DataInputStream dis);
}