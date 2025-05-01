package cainsgl.redis.core.network.response.resp;

public abstract class AbstractRESP2Response implements RESP2Response
{
    protected static final String END = "\r\n";

    private  final char PROTOCOL_CHAR;
    public AbstractRESP2Response(char protocolChar)
    {
        this.PROTOCOL_CHAR = protocolChar;
    }
    protected abstract String toData();

    public String nil()
    {
        return "$-1" + END;
    }
    @Override
    public String serialization(){
        if(toData() == null)
        {
            return nil();
        }
        return  PROTOCOL_CHAR+toData()+ END;
    }

    @Override
    public String toString()
    {
       return serialization();
    }
}
