public class Descktop {
    private static Go freame = null;

    public static void main(String[] args){
        freame = new Go("Server");
    }

    public static void flagBufferStart(){
        freame.flagStart = true;
    }
}
