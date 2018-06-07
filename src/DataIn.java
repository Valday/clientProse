import java.nio.ByteBuffer;

public class DataIn
{
    /** Taille maximum de la partie textuelle du message */
    public static final int TAILLE_TYPE = 1;
    public static final int TAILLE_ETAT = 1;
    public static final int TAILLE_MESSAGE = 32;
    //public static final int TAILLE_DISTANCE = 1;

    /** Taille total du message (type (byte) + x (byte) + y (byte) + distance (short)) */
    public static final int TAILLE_DATA_ECHANGE = (TAILLE_TYPE + TAILLE_ETAT + TAILLE_MESSAGE);

    private byte _type;

    private byte _etat;

    private String _message;

    public byte get_type()
    {
        return this._type;
    }

    public byte get_etat()
    {
        return this._etat;
    }

    public String get_message()
    {
        return this._message;
    }

    private DataIn()
    {

    }

    public DataIn(byte _type, byte _etat, String _message)
    {
        this._type = _type;
        this._etat = _etat;
        this._message = _message;
    }

    public DataIn(byte [] donneeEchange)
    {

        this._type = ByteBuffer.wrap(donneeEchange).get(0);

        this._etat= ByteBuffer.wrap(donneeEchange).get(TAILLE_TYPE);

       // this._message = ByteBuffer.wrap(donneeEchange).get(TAILLE_ETAT + TAILLE_TYPE);

    }


    @Override
    public String toString()
    {
        return "DataIn{" +
                "_type=" + _type +
                ", _etat=" + _etat +
                ", _message='" + _message + '\'' +
                '}';
    }
}
