package org.cvm.view;

import javafx.scene.control.Label;
import org.cvm.app.View;
import org.cvm.net.*;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ServerView extends View{

    public static int ID = 0;//id号的初始序列
    public static final int TCP_PORT = 5555;//TCP端口号
    public static final int UDP_PORT = 5556;//转发客户端数据的UDP端口号
    public static final int TANK_DEAD_UDP_PORT = 5557;//接收客户端坦克死亡的端口号
    private List<Client> clients = new ArrayList<>();//客户端集合
    private Image offScreenImage = null;//服务器画布
    private static final int SERVER_HEIGHT = 500;
    private static final int SERVER_WIDTH = 300;
    private DatagramSocket ds = null;

    @Override
    public void onLaunch() {
        Label label = new Label("正在等待连接...");
        getChildren().add(label);
    }

    @Override
    public void onEnter() {
        new Thread(new UDPThread()).start();
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(TCP_PORT);//在TCP欢迎套接字上监听客户端连接
            System.out.println("Server has started...");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(ID<=1){
            Socket s = null;
            try {
                s = ss.accept();//给客户但分配专属TCP套接字
                if (ID == 0) {
                    System.out.println("One client has connected...");
                }
                else {
                    System.out.println("Another client has connected...");
                }
                DataInputStream dis = new DataInputStream(s.getInputStream());
                int UDP_PORT = dis.readInt();//记录客户端UDP端口
                Client client = new Client(s.getInetAddress().getHostAddress(), UDP_PORT, ID);//创建Client对象
                clients.add(client);//添加进客户端容器

                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(ID++);//向客户端分配id号
                dos.writeInt(ServerView.UDP_PORT);//告诉客户端自己的UDP端口号
                dos.writeInt(ServerView.TANK_DEAD_UDP_PORT);
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(s != null) s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        START_MSG start_msg = new START_MSG(1);
        send(start_msg,1);
    }

    public void send(Msg msg){
        send(msg, 1);
        send(msg, 2);
    }

    public void send(Msg msg, int team){
        msg.send(ds, clients.get(team-1).IP, clients.get(team-1).UDP_PORT);
    }

    private class UDPThread implements Runnable{

        byte[] buf = new byte[1024];

        @Override
        public void run() {
            try{
                ds = new DatagramSocket(ServerView.UDP_PORT);
            }catch (SocketException e) {
                e.printStackTrace();
            }

            while (null != ds){
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                try {
                    ds.receive(dp);
                    parse(dp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void parse(DatagramPacket dp) {
            ByteArrayInputStream bais = new ByteArrayInputStream(buf, 0, dp.getLength());
            DataInputStream dis = new DataInputStream(bais);
            int msgType = 0;
            try {
                msgType = dis.readInt();//获得消息类型
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (msgType){//根据消息的类型调用对应消息的解析方法
                case Msg.MOVE_MSG :
                    MOVE_MSG msg1 = new MOVE_MSG();
                    msg1.parse(dis);
                    break;
                case Msg.ATTACK_MSG:
                    ATTACK_MSG msg2 = new ATTACK_MSG();
                    msg2.parse(dis);
                    break;
                case Msg.FINISH_MSG:
                    FINISH_MSG msg3 = new FINISH_MSG();
                    msg3.parse(dis);
                    break;
                default:
                    System.out.println("TODO");
            }
        }
    }


    public class Client{
        String IP;
        int UDP_PORT;
        int id;

        public Client(String ipAddr, int UDP_PORT, int id) {
            this.IP = ipAddr;
            this.UDP_PORT = UDP_PORT;
            this.id = id;
        }
    }
}
