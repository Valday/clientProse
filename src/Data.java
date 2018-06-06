import java.nio.ByteBuffer;

public class Data
{
    /** Taille maximum de la partie textuelle du message */
    public static final int TAILLE_MAX_MESSAGE = 32;

    /** Taille total du message (texte + entier) */
    public static final int TAILLE_MESSAGE_ECHANGE = (TAILLE_MAX_MESSAGE + 4);

    

    private String _message;

    private int _x;

    private int _y;


    public String get_message() {
        return _message;
    }

    public void set_message(String _message) {
        this._message = _message;
    }

    public int get_x() {
        return _x;
    }

    public void set_x(int _x) {
        this._x = _x;
    }

    public int get_y() {
        return _y;
    }

    public void set_y(int _y) {
        this._y = _y;
    }

    public Data(String _message, int _x, int _y)
    {
        this._message = _message;
        this._x = _x;
        this._y = _y;
    }

    private Data()
    {
    }

    public Data(byte [] donneeEchange)
    {

        int tailleMessageCapitaine = 0;

        while (tailleMessageCapitaine < TAILLE_MAX_MESSAGE - 1
                && donneeEchange[tailleMessageCapitaine] != 0) {
            tailleMessageCapitaine++;
        }

        this._message = new String(donneeEchange).substring(0, tailleMessageCapitaine);

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
        this._x = ByteBuffer.wrap(donneeEchange).getInt(TAILLE_MAX_MESSAGE);
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
                ( this._message.length() < TAILLE_MAX_MESSAGE )?
                        this._message.length() : TAILLE_MAX_MESSAGE;

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
                .put(this._message.getBytes())
                .put(tailleMessageCapitaine, (byte)0)
                .putInt(TAILLE_MAX_MESSAGE, this._x);

        /* Si besoin d'afficher le message formaté en tableau de bytes. */
        // System.out.println(Arrays.toString(formatMessage));

        return formatMessage;
    }


    @Override
    public String toString()
    {
        return "Data{" +
                "_message='" + _message + '\'' +
                ", _x=" + _x +
                ", _y=" + _y +
                '}';
    }
}
