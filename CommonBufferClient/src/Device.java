public class Device {
    private static Go freame = null;

    public static void main(String[] args){
        freame = new Go("Client");
    }

    public static void flagBufferStart(){
        freame.flagStart = true;
    }
}
