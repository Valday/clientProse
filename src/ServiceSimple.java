import java.net.Socket;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Service de communication de message.
 *
 * @author Guy Gnôle
 *
 * @see MessageExchange
 * @see ServeurSimple
 */
public class ServiceSimple extends Thread
{
	/** Socket de communication avec le client */
	private Socket socketClient;

	/** Flux d'écriture sur la socket client */
	private OutputStream out;

	/** Message communiqué */
	private MessageEchange message;

	/**
	 * Constructeur à partir d'une socket et d'un message à destination du client.
	 *
	 * @param socket la socket de communication avec le client
	 * @param message le message à transmettre au client
	 *
	 */
	public ServiceSimple(Socket socket, MessageEchange message) throws Exception
	{
		this.message = message;

		this.socketClient = socket;

		this.out = this.socketClient.getOutputStream();
		
		this.start();
	}

	/**
	 * Méthode d'exécution du service.
	 *
	 * @see Thread
	 */
	public void run()
	{
		try {
			byte[] message = this.message.formatMessageEnvoi();

			this.out.write(message, 0, message.length);
			this.out.flush();
		} catch (IOException e) {}
	
		this.message.incrementeAgeCapitaine();
	
		try {
			this.socketClient.close();
		} catch (IOException e) {}
	}
	
}

