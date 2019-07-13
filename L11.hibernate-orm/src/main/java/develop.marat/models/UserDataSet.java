package develop.marat.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PhoneDataSet> phones;

    public UserDataSet(){

    }

    public UserDataSet(String name, int age, AddressDataSet address, List<PhoneDataSet> phones) {
        super();
        this.name = name;
        this.age = age;
        this.address = address;

        this.phones = phones;
        for(PhoneDataSet phone: phones){
            phone.setUser(this);
        }
    }

    public UserDataSet(long id, String name, int age) {
        super(id);

        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAge(int age){
        this.age = age;
    }

    @Override
    public String toString() {
        return super.toString() + " [ name = " + name + ", age = " + age + ", address = " + address + ", phones = " + phones + "  ]";
    }
}
