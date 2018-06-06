import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Facteur  extends Thread
{
    private Socket socket;

    private OutputStream out;

    private InputStream in;

    private static Data message;

    public Facteur(Socket socket, String name) throws Exception
    {
        super(name);

        this.socket = socket;

        this.out = this.socket.getOutputStream();

        this.in = this.socket.getInputStream();

        this.start();
    }

    public static void set_messageTosend(Data message)
    {
        Facteur.message = message;
    }


    /**
     * Méthode d'exécution du service.
     *
     * @see Thread
     */
    public void run()
    {
        while (isAlive())
        {

            try
            {
                if(message != null)
                {

                    System.out.println(" => Essai envoi");
                    byte[] outputData = this.message.formatMessageEnvoi();

                    this.out.write(outputData,0,outputData.length);
                    this.out.flush();

                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                System.out.println(" => Essai lecture");
                byte[] inputData = new byte[Data.TAILLE_MESSAGE_ECHANGE];

                this.in.read(inputData);

                Data data = new Data(inputData);

                System.out.println(data.toString());

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            System.out.println(" => Fermeture socket");
            this.socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
