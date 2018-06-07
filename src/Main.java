import java.net.Socket;

public class Main
{
    private static final int PORTSERVEUR = 54321;
    private static final String IPSERVEUR = "169.254.101.114";

    public static void main(String[] args) throws Exception
    {
        System.out.println("Hello World!");

        Socket un_socket = new Socket(IPSERVEUR, PORTSERVEUR);

        Facteur facteur = new Facteur(un_socket, "test");

        Byte type = 2;
        Byte x = 42;
        Byte y = 45;
        Short dist = 10;

        DataOut initTrame = new DataOut((byte)0,(byte)42,(byte)0,(short)0);

        Proxy.Instance().sendMessage(facteur, initTrame);

        DataOut message = new DataOut((byte)2,(byte)42, (byte)45,(byte)10);

        Proxy.Instance().sendMessage(facteur, message);
    }
}
