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
    int new_death;
    int people_vaccinated;
    float population;

    public Data (String iso_code, String continent, String location, Date date, int new_case, int people_vaccinated,int new_death, float population) {
        this.iso_code = iso_code;
        this.continent = continent;
        this.location = location;
        this.date = date;
        this.new_case = new_case;
        this.new_death = new_death;
        this.people_vaccinated = people_vaccinated;
        this.population = population;
    }
    public void display(){
        System.out.printf(iso_code + " " + continent + " " + location + " " + date + " " + new_case + " " + new_death + " " + people_vaccinated + " " + population);
    }


    //read file and create object based on data then put it in the array
    public static void readFile() throws IOException, ParseException {
        URL url = new URL("https://raw.githubusercontent.com/TriDang/cosc2081/main/assignments/project/covid-data.csv");
        BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
        String i;
        ArrayList<Data> information = new ArrayList<>();
        int count = 0;
        while ((i = read.readLine()) != null) {
            if(count != 0) {
                String[] s = i.split(",");
                String iso_code = s[0];
//                System.out.println(a);
                String continent = s[1];
//                System.out.println(b);
                String location = s[2];
//                System.out.println(c);
                Date date = new SimpleDateFormat("MM/dd/yyyy").parse(s[3]);
//                System.out.println(d);
                int new_case = 0;
                int new_death = 0;
                int people_vaccinated = 0;
                if(s[4] != "") {
                    new_case = Integer.parseInt(s[4]);
                }
//                System.out.println(e);
                if(s[5] != "") {
                    new_death = Integer.parseInt(s[5]);
                }
//                System.out.println(f);
                if(s[6] != "") {
                    people_vaccinated = Integer.parseInt(s[6]);
                }
                float population = Float.parseFloat(s[7]);
//                System.out.println(g);
                Data newData = new Data(iso_code, continent, location, date, new_case, new_death, people_vaccinated, population);
                newData.display();
                information.add(newData);
            }
            count+=1;
        }
        read.close();
        for (Data data : information ) {
            data.display();
        }
    }
}