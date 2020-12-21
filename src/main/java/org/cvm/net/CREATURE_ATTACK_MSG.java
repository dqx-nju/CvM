package org.cvm.net;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class CREATURE_ATTACK_MSG implements Msg {
    private int MSGType = Msg.CREATURE_ATTACK_MSG;
    private int id;
    private int attack;//技能 12

    public CREATURE_ATTACK_MSG(){
        this.id = -1;
        this.attack = -1;
    }
    public CREATURE_ATTACK_MSG(int id, int attack){
        this.id = id;
        this.attack = attack;
    }

    public int getAttack() {
        return attack;
    }

    public int getId() {
        return id;
    }

    @Override
    public void send(DatagramSocket ds, String IP, int UDP_Port) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(30);//指定大小, 免得字节数组扩容占用时间
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(MSGType);
            dos.writeInt(id);
            dos.writeInt(attack);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = baos.toByteArray();
        try{
            DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, UDP_Port));
            ds.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream dis) {
        try{
            int id = dis.readInt();
            int attack = dis.readInt();
            this.id = id;
            this.attack = attack;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
