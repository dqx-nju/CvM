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
    public static final int BUFF_MSG = 5;
    public static final int S_MOVE_MSG = 6;
    public static final int INFORM_MSG = 7;
    public static final int START_MSG = 8;
    public static final int FINISH_MSG = 9;

    public void send(DatagramSocket ds, String IP, int UDP_Port);
    public void parse(DataInputStream dis);
}