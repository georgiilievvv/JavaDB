package JavaOOP.Telephony;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        List<String> phoneNum = Arrays.asList(reader.readLine().split(" "));
        List<String> sites = Arrays.asList(reader.readLine().split(" "));

        Smartphone phone = new Smartphone(phoneNum,sites);
        phone.call();
        phone.checkSite();
    }
}
