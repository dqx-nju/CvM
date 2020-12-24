package org.cvm.net;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import javafx.application.Platform;

import static org.cvm.Framework.*;

public class MOVE_MSG implements Msg {
    private int MSGType = Msg.MOVE_MSG;
    private int team; // 12
    private int id;
    private int dir;//上下左右 1234

    public MOVE_MSG(){
        this.team = -1;
        this.id = -1;
        this.dir = -1;
    }
    public MOVE_MSG(int team, int id, int dir){
        this.team = team;
        this.id = id;
        this.dir = dir;
    }

    public int getTeam() {
        return team;
    }

    public int getDir() {
        return dir;
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
            dos.writeInt(dir);
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
            int dir = dis.readInt();
            this.team = team;
            this.id = id;
            this.dir = dir;
            System.out.println("aa d");
            if (dir == 4) {
                int[] s = calabashbrotherTeam.moveright(id);
                if (s[0] != -1) {
                    Platform.runLater(() -> {
                        playView.swap_block(10, 11);
                        System.out.println("lalalalallala d");
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
