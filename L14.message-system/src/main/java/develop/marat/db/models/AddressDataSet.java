package develop.marat.db.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="addresses")
public class AddressDataSet extends DataSet{
    @Column(name = "street")
    private String street;

    public AddressDataSet(){

    }

    public AddressDataSet(String street){
        super();
        this.street = street;
    }

    @Override
    public String toString() {
        return "street: " + street;
    }
}
