import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FilePatch {
    public static String ToFilePatch (String s){
        String  readString = "";
        FileInputStream inputstream;
        File readIp = new File("src/Ip.txt");
        File readPort = new File("src/Port.txt");
        try {
            if(s.equals("ip")) {
                inputstream = new FileInputStream(readIp);
            }else {
                inputstream = new FileInputStream(readPort);
            }
            BufferedReader bufferString = new BufferedReader(new InputStreamReader(inputstream));
            readString = bufferString.readLine();
            bufferString.close();
            inputstream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Exception: not File patch");
        } catch (IOException e) {
            System.out.println("Exception: readLine");
            e.printStackTrace();
        }
    return readString;
    }
}
