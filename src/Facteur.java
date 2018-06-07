import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Facteur  extends Thread
{
    private Socket socket;

    private OutputStream out;

    private InputStream in;

    private DataOut message;

    public Facteur(Socket socket, String name) throws Exception
    {
        super(name);

        this.socket = socket;

        this.out = this.socket.getOutputStream();

        this.in = this.socket.getInputStream();

        this.start();
    }

    public void set_messageTosend(DataOut message)
    {
        this.message = message;
        System.out.println(" => Essai envoi");
        byte[] outputData = this.message.formatMessageEnvoi();

        try
        {
            this.out.write(outputData,0,outputData.length);
            this.out.flush();
            this.message = null;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

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
/*
            try
            {
                if(message != null)
                {

                    System.out.println(" => Essai envoi");
                    byte[] outputData = message.formatMessageEnvoi();

                    this.out.write(outputData,0,outputData.length);
                    this.out.flush();
                    message = null;
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            */

                try
                {
                    System.out.println(" => Essai lecture");
                    byte[] inputData = new byte[DataIn.TAILLE_DATA_ECHANGE];

                    this.in.read(inputData);

                    DataIn dataIn = new DataIn(inputData);

                    this.computeMessage(dataIn);

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

    private void computeMessage(DataIn dataIn)
    {
        switch (dataIn.get_type())
        {
            case 0:
                try
                {
                    Dispatcher.Instance().isInitOk((message.get_x() == dataIn.get_etat())? true : false);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case 1:
                Dispatcher.Instance().setEtatRobot(dataIn);
                break;

            case 2:
                Dispatcher.Instance().setError(dataIn);
                break;
            default:
                break;
        }
    }
}
