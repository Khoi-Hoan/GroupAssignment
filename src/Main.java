import java.net.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {

    }
    public static void readFile() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/TriDang/cosc2081/main/assignments/project/covid-data.csv");
        BufferedReader read = new BufferedReader(
                new InputStreamReader(url.openStream()));
        String i;
        while ((i = read.readLine()) != null)
            System.out.println(i);
        read.close();
    }
}



/*public class data {
    private int iso_code;
    private String continent;
    private String location;
    private
}*/