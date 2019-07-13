package develop.marat.ms.core;

import java.util.Objects;
import java.util.UUID;

public abstract class Message {

    private final UUID id = UUID.randomUUID();
    private final Address from;
    private final Address to;

    public UUID getId() {
        return id;
    }

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract Object exec(Addressee addressee);

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof Message)){
            return false;
        }

        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

}


