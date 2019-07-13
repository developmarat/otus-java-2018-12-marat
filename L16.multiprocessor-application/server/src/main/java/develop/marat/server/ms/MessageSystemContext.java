package develop.marat.server.ms;


import develop.marat.ms.core.Address;
import develop.marat.server.controller.ControllerExecutor;

public class MessageSystemContext {
    private Address frontendAddress;
    private Address backendAddress;

    private ControllerExecutor controller;

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

    public void setController(ControllerExecutor controller){
        this.controller = controller;
    }

    public ControllerExecutor getController() {
        return controller;
    }

    public Address getControllerAddress() {
        return controller.getAddress();
    }
}
