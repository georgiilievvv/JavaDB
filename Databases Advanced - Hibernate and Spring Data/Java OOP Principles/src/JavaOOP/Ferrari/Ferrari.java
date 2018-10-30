package JavaOOP.Ferrari;

public class Ferrari implements Car {

    private String name;

    public String getModel() {
        return model;
    }

    private String model = "488-Spider";

    public Ferrari(String name) {
        this.name = name;
    }

    @Override
    public String brake() {
        return "Brakes!";
    }

    @Override
    public String gasPedal() {
        return "Zadu6avam sA!";
    }

    public String getName() {
        return name;
    }
}
