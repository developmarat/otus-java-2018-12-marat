package develop.marat.ms.core;

/**
 *  Use in SocketMessageSystem for conversion from json and address determination
 */
public class SimpleMessage extends Message {

    public SimpleMessage(Address from, Address to) {
        super(from, to);
    }

    public Object exec(Addressee addressee){
        return null;
    }
}


