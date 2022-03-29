package de.can.sockenschach.net;

import de.can.sockenschach.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPEndpoint {

    public ServerSocket serverSocket;
    public Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public UDPEndpoint.State state;

    private int port;

    public String dataArr = "";
    public String lastSent = "";

    private Thread readerThread;

    int timeout = 0;
    final int maxtimeout = 5;


    private boolean receiving = false;

    String ip;

    public TCPEndpoint(UDPEndpoint.State state, int port, String ip){

        this.state = state;
        this.port = port;
        this.ip = ip;

    }

    public void init(){
        try {
            if (this.state == UDPEndpoint.State.RECEIVE) {
                waitForCon();
            } else if (this.state == UDPEndpoint.State.SEND) {
                beginCon();
            } else {
                Logger.error("Error, TCP state undecided");
            }


            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            receiving = true;

            this.readerThread = new Thread(() -> {
                while(receiving){
                    try {
                        if(timeout < maxtimeout){
                            lastSent = in.readLine();
                            dataArr += "\n" + lastSent;
                        }else{
                            Logger.netLog("timeout close");
                        }

                    } catch (IOException  e) {
                        if(e instanceof SocketException){
                            Logger.netLog("receiving not possible; partner disconnected");
                            timeout++;
                        }else{
                            e.printStackTrace();
                        }

                    }
                }
            });

            this.readerThread.start();

        }catch(Exception e){
            e.printStackTrace();
        }

        Logger.netLog("TCP Init done");


    }

    private void waitForCon() throws IOException {
        Logger.netLog("Waiting for incoming req...");

        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();

        Logger.netLog("connection established!");


    }


    private boolean forceConnection = true;
    private void beginCon() throws IOException, InterruptedException {
        while(forceConnection){
            try{
                clientSocket = new Socket(ip, port);

                forceConnection = false;
            }catch(ConnectException e){
                Thread.sleep(500);
            }
        }
        Logger.netLog("connection established!");
    }

    public void stop(){
        this.receiving = false;

        try {
            Logger.netLog("closing TCPEndpoint");
            //in.close();
            //out.close();
            if(clientSocket != null){
                if(!clientSocket.isClosed()){
                    clientSocket.close();
                }

                if(state == UDPEndpoint.State.RECEIVE){
                    serverSocket.close();
                }
            }




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendln(String str){
        Logger.netLog("sending " + str + " via tcp");
        this.out.println(str);
    }



}
