package develop.marat.ms.core;


public class BaseMSService implements Addressee {
    protected final Address address;

    public BaseMSService(Address address){
        this.address = address;
    }

    @Override
    public Address getAddress() {
        return address;
    }
}
