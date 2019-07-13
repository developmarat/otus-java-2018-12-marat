package develop.marat.ms.messageSystem;


public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private Address frontendAddress;
    private Address backendAddress;

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontendAddress() {
        return frontendAddress;
    }

    public void setFrontendAddress(Address frontendAddress) {
        this.frontendAddress = frontendAddress;
    }

    public Address getBackendAddress() {
        return backendAddress;
    }

    public void setBackendAddress(Address backendAddress) {
        this.backendAddress = backendAddress;
    }
}
