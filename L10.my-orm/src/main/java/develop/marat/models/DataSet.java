package develop.marat.models;

public class DataSet {
    private long id;

    public DataSet(){

    }

    public DataSet(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return super.toString() + " [ id = " + id + "]";
    }
}
