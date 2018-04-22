import java.io.*;
import java.net.Socket;

public class ServerCommonBuffer extends Thread{
    Socket socket;

    public ServerCommonBuffer(Socket server){
        socket = server;
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    public void run() {
        String previous ="";
        ObjectInput in = null;
        ObjectOutput out = null;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                String Clip = (String) in.readObject();
                if (!Clip.equals("null&&&")) {
                    new StartServerCommonBuffer().setClipBoard(Clip);
                }
                Clip = new StartServerCommonBuffer().IsBuffReader(previous);
                System.out.println(Clip);
                if (!Clip.equals("null&&&")) {
                    out.flush();
                    out.writeObject(Clip);
                    previous = Clip;
                } else {
                    out.flush();
                    out.writeObject("null&&&");
                }
            } catch (IOException e) {
                System.out.println("Exception: stream");
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                System.out.println("Exception: conversion to string");
                e.printStackTrace();
                break;
            }
        }
    }
}
