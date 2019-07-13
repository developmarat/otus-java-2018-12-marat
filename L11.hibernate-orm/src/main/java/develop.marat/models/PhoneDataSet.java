package develop.marat.models;

import javax.persistence.*;

@Entity
@Table(name="phones")
public class PhoneDataSet extends DataSet {
    @Column(name = "number")
    private String number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable=false)
    private UserDataSet user;

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
