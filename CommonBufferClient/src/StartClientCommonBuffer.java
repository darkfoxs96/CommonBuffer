import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class StartClientCommonBuffer extends Thread{
    static public String previous ="";
    static public String ip ="";
    static public int port=0;
    static public boolean flagStartClientCommonBuffer = true;
    static public ObjectOutput out = null;

    public StartClientCommonBuffer(String s) {
        start();
    }

    public StartClientCommonBuffer() {}

    public static void setIpPort(String ip, int port){
        StartClientCommonBuffer.ip = ip;
        StartClientCommonBuffer.port = port;
    }

    public void run() {
        Socket socket;
        ObjectInput in = null;
        if(ip.equals("") || port == 0) {
            System.out.println("Error: port || ip empaty");
            Device.flagBufferStart();
            return;
        }
        while (flagStartClientCommonBuffer) {
            boolean norml = false;
            try {
                socket = new Socket(ip, port);
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                norml = true;
            } catch (UnknownHostException e) {
                System.out.println("Exception: Host");
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (norml) {
                try {
                    String Clip = new StartClientCommonBuffer().IsBuffReader();
                    if (!Clip.equals("null&&&")) {
                        out.flush();
                        out.writeObject(Clip);
                    } else {
                        out.flush();
                        out.writeObject("null&&&");
                    }
                    Clip = (String) in.readObject();
                    if (!Clip.equals("null&&&")) {
                        new StartClientCommonBuffer().setClipBoard(Clip);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Device.flagBufferStart();
        return;
    }

    public String IsBuffReader(){
        if(!previous.equals(new StartClientCommonBuffer().getClipBoard())){
            System.out.println(previous + " " + new StartClientCommonBuffer().getClipBoard());
            previous = new StartClientCommonBuffer().getClipBoard();
            return new StartClientCommonBuffer().getClipBoard();
        } else {
            return "null&&&";
        }
    }

    public String getClipBoard(){
        try {
            return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (HeadlessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedFlavorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public void setClipBoard(String in){
        Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection s = new StringSelection(in);
        cp.setContents(s,s);
    }

}
