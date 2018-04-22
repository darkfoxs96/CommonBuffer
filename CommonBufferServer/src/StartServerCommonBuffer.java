import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class StartServerCommonBuffer extends Thread{
    static private String ip ="";
    static private int port=0;
    static public boolean flagStartServerCommonBuffer = true;

    public static void SetIpPort(String ip, int port){
        StartServerCommonBuffer.ip = ip;
        StartServerCommonBuffer.port = port;
    }

    public StartServerCommonBuffer(String s) {
        start();
    }

    public StartServerCommonBuffer() {}

    public void run() {
        if(ip.equals("") || port == 0) {
            System.out.println("Error: port || ip empaty");
            Descktop.flagBufferStart();
            return;
        }
        System.out.println(ip + " " + port);
        ServerSocket server = null;
        while (flagStartServerCommonBuffer) {
            boolean norml = false;
            try {
                server = new ServerSocket(port, 0, InetAddress.getByName(ip));
                norml = true;
                System.out.println("Server start...");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Exception created server: " + e);
            }
            try {
                while (norml) {
                    new ServerCommonBuffer(server.accept());
                    System.out.println("Client accept");
                }
            } catch (Exception e) {
                System.out.println("Exception server accept: " + e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Descktop.flagBufferStart();
        return;
    }

    public String IsBuffReader(String previous){
        if(!previous.equals(new StartServerCommonBuffer().getClipBoard())){
            return new StartServerCommonBuffer().getClipBoard();
        } else {
            return "null&&&";
        }
    }

    private String getClipBoard(){
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
