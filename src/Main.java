import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        HashMap<String, Data> dataHashMap = Data.readFile();
        dataHashMap.size();
    }
}



class Data {
    String iso_code;
    String continent;
    String location;
    Date date;
    int new_case;
    float new_death;
    float people_vaccinated;
    float population;

    public Data (String iso_code, String continent, String location, Date date, int new_case, float new_death, float people_vaccinated,  float population) {
        this.iso_code = iso_code;
        this.continent = continent;
        this.location = location;
        this.date = date;
        this.new_case = new_case;
        this.new_death = new_death;
        this.people_vaccinated = people_vaccinated;
        this.population = population;
    }

    public String getIso_code() {
        return iso_code;
    }

    public int getNew_case() {
        return new_case;
    }

    public float getNew_death() {
        return new_death;
    }

    public float getPeople_vaccinated() {
        return people_vaccinated;
    }

    public float getPopulation() {
        return population;
    }

    public void display(){
        System.out.println(iso_code + " " + continent + " " + location + " " + date + " " + new_case + " " + new_death + " " + people_vaccinated + " " + population);
    }


    //read file and create object based on data then put it in the array
    public static HashMap<String, Data> readFile() throws IOException, ParseException {
        BufferedReader read = new BufferedReader(new FileReader("Data.txt"));
        String i;
        HashMap<String, Data> information = new HashMap<>();
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
                float new_death = 0;
                int people_vaccinated = 0;
                if(!s[4].equals("")) {
                    new_case = Integer.parseInt(s[4]);
                }
//                System.out.println(e);
                if(!s[5].equals("")) {
                    new_death = Float.parseFloat(s[5]);
                }
//                System.out.println(f);
                if(!s[6].equals("")) {
                    people_vaccinated = Integer.parseInt(s[6]);
                }
                float population = Float.parseFloat(s[7]);
//                System.out.println(g);
                Data newData = new Data(iso_code, continent, location, date, new_case, new_death, people_vaccinated, population);
                newData.display();
                information.put(s[2] + s[3] ,newData);
            }
            count++;
        }
        read.close();
        for (String data : information.keySet() ) {
            information.get(data).display();
        }
        return information;
    }
}