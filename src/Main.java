import java.net.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Data.readFile();
    }
}



class Data {
    String iso_code;
    String continent;
    String location;
    Date date;
    int new_case;
    int people_vaccinated;
    float population;

    public Data (String iso_code, String continent, String location, Date date, int new_case, int people_vaccinated, float population) {
        this.iso_code = iso_code;
        this.continent = continent;
        this.location = location;
        this.date = date;
        this.new_case = new_case;
        this.people_vaccinated = people_vaccinated;
        this.population = population;
    }
    public void display(){
        System.out.println(iso_code + continent +location + date + new_case + people_vaccinated + population);
    }


    //read file and create object based on data then put it in the array
    public static void readFile() throws IOException, ParseException {
        URL url = new URL("https://raw.githubusercontent.com/TriDang/cosc2081/main/assignments/project/covid-data.csv");
        BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
        String i;
        ArrayList<Data> information = new ArrayList<>();
        while ((i = read.readLine()) != null) {
            String[] s = i.split(",");
            String a = s[0];
            String b = s[1];
            String c = s[2];
            Date d = new SimpleDateFormat("MM/dd/yyyy").parse(s[3]);
            int e = Integer.parseInt(s[4]);
            int f = Integer.parseInt(s[5]);
            float g = Float.parseFloat(s[6]);
            Data newData = new Data(a,b,c,d,e,f,g);
            information.add(newData);
        }
        read.close();
        for (Data data : information ) {
            data.display();
        }
    }
}