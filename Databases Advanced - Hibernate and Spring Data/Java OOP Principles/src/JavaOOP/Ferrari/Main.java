package JavaOOP.Ferrari;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.instrument.IllegalClassFormatException;

public class Main {
    public static void main(String[] args) throws IllegalClassFormatException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        String ferrariName = Ferrari.class.getSimpleName();
        String carInterface = Car.class.getSimpleName();
        boolean isCreated = Car.class.isInterface();
        if (!isCreated) {
            throw new IllegalClassFormatException("No interface created!");
        }

        String name = reader.readLine();

        Ferrari ferrari = new Ferrari(name);

        System.out.println(ferrari.getModel() + '/' + ferrari.brake() + '/'+ ferrari.gasPedal() + '/' + ferrari.getName());
    }
}
