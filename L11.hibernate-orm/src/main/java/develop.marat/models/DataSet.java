package develop.marat.models;

import javax.persistence.*;

@MappedSuperclass
public class DataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public DataSet(){
    }

    public DataSet(long id) {
        this.id = id;
    }

    long getId() {
        return id;
    }

    @Override
    public String toString() {
        return super.toString() + " [ id = " + id + "]";
    }
}
