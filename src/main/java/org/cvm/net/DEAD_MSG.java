package org.cvm.net;


import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class DEAD_MSG implements Msg {
    private int MSGType = Msg.DEAD_MSG;
    private int team;
    private int id;

    public DEAD_MSG(){
        this.team = -1;
        this.id = -1;
    }
    public DEAD_MSG(int team, int id){
        this.team = team;
        this.id = id;
    }

    public int getTeam() {
        return team;
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
            dos.writeInt(team);
            dos.writeInt(id);
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
            int team = dis.readInt();
            int id = dis.readInt();
            this.team = team;
            this.id = id;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
