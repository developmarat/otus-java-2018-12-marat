package develop.marat.ms_clients_common.db.models;

import javax.persistence.*;

@Entity
@Table(name="phones")
public class PhoneDataSet extends DataSet {
    @Column(name = "number")
    private String number;

    @ManyToOne(cascade = CascadeType.ALL)
    private transient UserDataSet user;

    public PhoneDataSet(){

    }

    public PhoneDataSet(String number){
        super();
        this.number = number;
    }

    public UserDataSet getUser(){
        return user;
    }

    public void setUser(UserDataSet user){
        this.user = user;
    }

    @Override
    public String toString() {
        return number;
    }
}
