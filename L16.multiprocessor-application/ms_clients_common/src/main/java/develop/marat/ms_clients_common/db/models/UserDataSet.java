package develop.marat.ms_clients_common.db.models;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {

    @Column(name = "login", unique=true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PhoneDataSet> phones;

    public enum Role {
        ADMIN,
        USER
    }

    public UserDataSet(){

    }

    public UserDataSet(@NotNull @NotEmpty String login, @NotNull @NotEmpty String password) {
        super();
        this.login = login;
        this.password = password;
    }

    public UserDataSet(String login, String password, String name, int age, AddressDataSet address, List<PhoneDataSet> phones) {
        this(login, password);
        this.name = name;
        this.age = age;
        this.address = address;

        this.phones = phones;
        for(PhoneDataSet phone: phones){
            phone.setUser(this);
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public Role getRole() {
        if(name.equals("admin")){
            return Role.ADMIN;
        } else {
            return Role.USER;
        }

    }

    @Override
    public String toString() {
        return super.toString() + " [ name = " + name + ", age = " + age + ", address = " + address + ", phones = " + phones + "  ]";
    }
}
