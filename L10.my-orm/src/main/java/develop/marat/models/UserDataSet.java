package develop.marat.models;

public class UserDataSet extends DataSet {

    private String name;
    private int age;


    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
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

    @Override
    public String toString() {
        return super.toString() + " [ name = " + name + ", age = " + age + "  ]";
    }
}
