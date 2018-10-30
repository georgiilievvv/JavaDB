package JavaOOP.DefineAnInterfacePerson;

import JavaOOP.MultipleImplementation.Birthable;
import JavaOOP.MultipleImplementation.Identifiable;

public class Citizen implements Person, Identifiable, Birthable {
    private String name;
    private int age;
    private String id;
    private String Birthdate;

    public void setId(String id) {
        this.id = id;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    public Citizen(String name, int age, String id, String birthdate) {
        this.setAge(age);
        this.setName(name);
        this.setId(id);
        this.setBirthdate(birthdate);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String Birthdate() {
        return Birthdate;
    }

    @Override
    public String id() {
        return id;
    }
}
