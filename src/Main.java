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

    }
}
