import java.nio.ByteBuffer;
import java.util.Arrays;

public class Data
{
    /** Taille maximum de la partie textuelle du message */
    public static final int TAILLE_TYPE = 1;
    public static final int TAILLE_X = 1;
    public static final int TAILLE_Y = 1;
    public static final int TAILLE_DISTANCE = 1;

    /** Taille total du message (type (byte) + x (byte) + y (byte) + distance (short)) */
    public static final int TAILLE_DATA_ECHANGE = (TAILLE_TYPE + TAILLE_X + TAILLE_Y + TAILLE_DISTANCE);

    private byte _type;

    private byte _x;

    private byte _y;

    private short _distance;

    public byte get_type()
    {
        return _type;
    }

    public void set_type(byte _type)
    {
        this._type = _type;
    }

    public byte get_x()
    {
        return _x;
    }

    public void set_x(byte _x)
    {
        this._x = _x;
    }

    public byte get_y()
    {
        return _y;
    }

    public void set_y(byte _y)
    {
        this._y = _y;
    }

    public short get_distance()
    {
        return _distance;
    }

    public void set_distance(short _distance)
    {
        this._distance = _distance;
    }

    private Data()
    {
    }

    public Data(byte _type, byte _x, byte _y, short _distance)
    {
        this._type = _type;
        this._x = _x;
        this._y = _y;
        this._distance = _distance;
    }

    public Data(byte [] donneeEchange)
    {

        this._type = ByteBuffer.wrap(donneeEchange).get(0);

        this._x = ByteBuffer.wrap(donneeEchange).get(TAILLE_TYPE);

        this._y = ByteBuffer.wrap(donneeEchange).get(TAILLE_TYPE + TAILLE_X);

        this._y = ByteBuffer.wrap(donneeEchange).get(TAILLE_TYPE + TAILLE_X + TAILLE_Y);

    }

    /**
     * Formate un message en un tableau d'octet interprétable par un client.
     *
     * @return un tableau d'octet.
     *
     */
    public byte [] formatMessageEnvoi()
    {
        byte [] formatMessage = new byte [TAILLE_DATA_ECHANGE];

        /* 2.2 */
        ByteBuffer.wrap(formatMessage)
                .put(this._type)
                .put(this._x)
                .put(this._y)
                .putShort(this._distance);

        /* Si besoin d'afficher le message formaté en tableau de bytes. */
         System.out.println(Arrays.toString(formatMessage));

        return formatMessage;
    }


    @Override
    public String toString()
    {
        return "Data{" +
                "_type=" + _type +
                ", _x=" + _x +
                ", _y=" + _y +
                ", _distance=" + _distance +
                '}';
    }
}
