public class Dispatcher
{
    /**
     *
     */
    private static volatile Dispatcher _instance;

    /**
     *
     * @return
     */
    public static Dispatcher Instance()
    {
        if (_instance == null)
        {
            synchronized(Dispatcher.class){
                if(_instance == null)
                {
                    _instance = new Dispatcher();
                }
            }
        }
        return _instance;
    }

    public void setMessage(DataIn message)
    {
        System.out.println(message.toString());


    }

    public void isInitOk(Boolean isOk) throws Exception
    {
        if(isOk)
        {
            System.out.println(" => >INIT OK ...");
        }
        else
        {
            throw new Exception("Erreur de communication : init failed");
        }
    }

    public void setEtatRobot(DataIn dataIn)
    {
        switch (dataIn.get_etat())
        {
            case 0:
                System.out.println(" => ERROR : " + dataIn.get_message());
                break;
            case 1:
                System.out.println(" => EN MOUVMENT ...");
                break;
            case 2:
                System.out.println(" => A L'ARRET...");
                break;
            case 3 :
                System.out.println(" => ARRET URGENCE ...");
                break;
                default:
                    break;
        }
        System.out.println(" => Etat robot ...");
    }

    public void setError(DataIn dataIn)
    {
        System.out.println(" => Error | Message : " + dataIn.get_message());
    }
}
