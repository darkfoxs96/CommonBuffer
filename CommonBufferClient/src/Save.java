import java.io.*;

public class Save {
    public static void ToFileSave(String ip, String port) {
        String s = new File("").getAbsolutePath().toString();
        s += "/src/ip.txt";
        File readIp = new File(s);
        s = new File("").getAbsolutePath().toString();
        s += "/src/port.txt";
        File readPort = new File(s);
        try {
            FileWriter writer = new FileWriter(readIp);
            writer.flush();
            writer.write(ip);
            writer.close();
            writer = new FileWriter(readPort);
            writer.flush();
            writer.write(port);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Exception: not File patch");
        } catch (IOException e) {
            System.out.println("Exception: readLine");
            e.printStackTrace();
        }
    }
}
