/**
 * Classe de gestion des messages transmis par le serveur simple.
 *
 * @author Guy Gnôle
 *
 * @see ServeurSimple
 * @see ClientSimple
 */

import java.nio.ByteBuffer;
import java.util.Arrays; 

public class MessageEchange
{
	/** Configuration du port du serveur sur lequel sont transmis les messages. */
	public static final int PORT_DU_SERVEUR = 54321;

	/** Taille maximum de la partie textuelle du message */
	public static final int TAILLE_MAX_MESSAGE_CAPITAINE = 32;

	/** Taille total du message (texte + entier) */
	public static final int TAILLE_MESSAGE_ECHANGE = (TAILLE_MAX_MESSAGE_CAPITAINE + 4);

	/** Partie textuelle du message. */
	private String messageCapitaine;
	
	/** Partie entière du message */
	private int ageCapitaine;


	/**
	 * Constructeur d'un message à partir d'un texte et d'un entier.
	 *
	 * @param message le texte du message
	 * @param age la valeur de l'entier dans le message
	 *
	 */
	public MessageEchange(String message, int age)
	{
		this.messageCapitaine = message;
		this.ageCapitaine = age;
	}

	/**
	 * Constructeur d'un message à partir d'un tableau d'octets.
	 * Le message est construit en interprétant les quatre derniers octets 
	 * comme un entier et les précédent comme du texte.
	 *
	 * @param donneeEchange le tableau d'octet qui compose le message.
	 *
	 */
	public MessageEchange(byte [] donneeEchange)
	{
		
		int tailleMessageCapitaine = 0;

		while (tailleMessageCapitaine < TAILLE_MAX_MESSAGE_CAPITAINE - 1 
			&& donneeEchange[tailleMessageCapitaine] != 0) {
			tailleMessageCapitaine++;
		}

		this.messageCapitaine = new String(donneeEchange).substring(0, tailleMessageCapitaine);
		
		/* 1ère approche avec gestion des bits */
		/*
		this.ageCapitaine = 0;
	        for (int i = 0 ; i < 4 ; i++)
        		this.ageCapitaine |= 
				donneeEchange[ i + TAILLE_MAX_MESSAGE_CAPITAINE ] << ((3 - i) * 8)
				& 0xFF << ((3 - i) * 8);
		*/
		/* Remarque : l'instruction " & 0xFF << ((3 - i) * 8) " évite la promotion du bit de signe. */

		/* 2ème approche avec utilisation d'un ByteBuffer. */
	
		/* 2.0 */
		/*
		this.ageCapitaine = ByteBuffer.allocate(4).put(donneeEchange, TAILLE_MAX_MESSAGE_CAPITAINE, 4).getInt(0);
		*/

		/* 2.1 */
		this.ageCapitaine = ByteBuffer.wrap(donneeEchange).getInt(TAILLE_MAX_MESSAGE_CAPITAINE);
	}

	/**
	 * Incrémente la valeur entière du message.
	 *
	 */
	public void incrementeAgeCapitaine()
	{
		this.ageCapitaine++;
	}


	/**
	 * Formate un message en un tableau d'octet interprétable par un client. 
	 *
	 * @return un tableau d'octet.
	 *
	 */
	public byte [] formatMessageEnvoi()
	{
		byte [] formatMessage = new byte [TAILLE_MESSAGE_ECHANGE];
       	
		int tailleMessageCapitaine = 
			( this.messageCapitaine.length() < TAILLE_MAX_MESSAGE_CAPITAINE )? 
				this.messageCapitaine.length() : TAILLE_MAX_MESSAGE_CAPITAINE;

		/* 1ère approche avec la gestion des bits */
		/*
		for (int i = 0 ; i < tailleMessageCapitaine - 1; i++) 
		{
			formatMessage[i] = this.messageCapitaine.getBytes()[i];
		}
		formatMessage[tailleMessageCapitaine - 1] = 0;
		
		for (int i = 0 ; i < 4 ; i++) 
		{
			formatMessage[ i + TAILLE_MAX_MESSAGE_CAPITAINE ] = 
				(byte)(this.ageCapitaine >> ((3 - i) * 8));
		}
		*/

		/* 2ème approche avec l'utilisation d'un ByteBuffer */

		/* 2.0 */
		/*
		byte[] age = ByteBuffer.allocate(4).putInt(this.ageCapitaine).array();

		for (int i = 0 ; i < 4 ; i++) 
		{
			formatMessage[ i + TAILLE_MAX_MESSAGE_CAPITAINE ] = age[i];
		}
		*/	

		/* 2.1 */
		/*
		formatMessage = ByteBuffer.allocate(TAILLE_MESSAGE_ECHANGE)
			.put(this.messageCapitaine.getBytes())
			.put(tailleMessageCapitaine, (byte)0)
			.putInt(TAILLE_MAX_MESSAGE_CAPITAINE, this.ageCapitaine)
			.array();
		*/

		/* 2.2 */
		ByteBuffer.wrap(formatMessage)
			.put(this.messageCapitaine.getBytes())
			.put(tailleMessageCapitaine, (byte)0)
			.putInt(TAILLE_MAX_MESSAGE_CAPITAINE, this.ageCapitaine);
		
		/* Si besoin d'afficher le message formaté en tableau de bytes. */
		// System.out.println(Arrays.toString(formatMessage));

		return formatMessage;
	}


	/**
	 * Affiche le message sur la sortie standard.
	 *
	 */
	public void printMessage()
	{
		System.out.println (this.messageCapitaine + ' ' + this.ageCapitaine);
	}

}

