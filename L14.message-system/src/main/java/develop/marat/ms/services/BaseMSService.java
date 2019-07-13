package develop.marat.ms.services;

import develop.marat.ms.messageSystem.MessageSystemContext;
import develop.marat.ms.messageSystem.Address;
import develop.marat.ms.messageSystem.Addressee;
import develop.marat.ms.messageSystem.MessageSystem;

public abstract class BaseMSService implements Addressee {
    protected final Address address;
    protected final MessageSystemContext context;

    public BaseMSService(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

}
