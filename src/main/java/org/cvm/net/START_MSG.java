package org.cvm.net;


import javafx.application.Platform;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import static org.cvm.Framework.playView;

public class START_MSG implements Msg {
    private int MSGType = Msg.START_MSG;
    private int team;

    public START_MSG(){
        this.team = -1;
    }
    public START_MSG(int team){
        this.team = team;
    }

    public int getTeam() {
        return team;
    }

    @Override
    public void send(DatagramSocket ds, String IP, int UDP_Port) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(30);
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(MSGType);
            dos.writeInt(team);
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
            this.team = team;
            Platform.runLater(() -> {
                playView.start_turn(team);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
