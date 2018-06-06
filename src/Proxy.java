public class Proxy
{
    /**
     *
     */
    private static volatile Proxy _instance;

    /**
     *
     * @return
     */
    public static Proxy Instance()
    {
        if (_instance == null)
        {
            synchronized(Proxy.class){
                if(_instance == null)
                {
                    _instance = new Proxy();
                }
            }
        }
        return _instance;
    }

    public void sendMessage(Data msg)
    {
        Facteur.set_messageTosend(msg);
    }

}
