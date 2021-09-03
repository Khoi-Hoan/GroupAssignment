import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.time.format.DateTimeFormatter;
class Data {
    String iso_code;
    String continent;
    String location;
    LocalDate date;
    int new_case;
    int new_death;
    int people_vaccinated;
    float population;

    public Data(String iso_code, String continent, String location, LocalDate date, int new_case, int new_death, int people_vaccinated, float population) {
        this.iso_code = iso_code;
        this.continent = continent;
        this.location = location;
        this.date = date;
        this.new_case = new_case;
        this.new_death = new_death;
        this.people_vaccinated = people_vaccinated;
        this.population = population;
    }
    public String getLocation(){return this.location;}
    public int getNew_case() {
        return this.new_case;
    }
    public void setPeople_vaccinated(int people_vaccinated){
        this.people_vaccinated = people_vaccinated;
    }
    public LocalDate getDate(){
        return this.date;
    }
    public int getNew_death() {
        return this.new_death;
    }
    public int getPeople_vaccinated() {
        return this.people_vaccinated;
    }
    public void display() {
        System.out.println(iso_code + " " + continent + " " + location + " " + date + " " + new_case + " " + new_death + " " + people_vaccinated + " " + population);
    }
    //read file and create object based on data then put it in the array
    public static HashMap<String, Data> readFile() throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("Data.txt"));
        String i;
        HashMap<String, Data> information = new HashMap<>();
        int count = 0;
        while ((i = read.readLine()) != null) {
            if (count != 0) {
                String[] s = i.split(",");
                String iso_code = s[0];
                String continent = s[1];
                String location = s[2];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                LocalDate date = LocalDate.parse(s[3], formatter);
                int new_case = 0;
                int new_death = 0;
                int people_vaccinated = 0;
                float population = 0;

                if (s.length >= 5 && !s[4].equals("")) {//in case the split read blank and in case that country does not record population
                    new_case = Integer.parseInt(s[4]);
                }

                if (s.length >= 6 && !s[5].equals("")) {
                    new_death = Integer.parseInt(s[5]);
                }
                if (s.length >= 7 && !s[6].equals("")) {
                    people_vaccinated = Integer.parseInt(s[6]);
                }
                if (s.length >= 8 && !s[7].equals("")) {
                    population = Float.parseFloat(s[7]);
                }
                Data newData = new Data(iso_code, continent, location, date, new_case, new_death, people_vaccinated, population);
                information.put(location + date, newData);//hashmap with location+date as string
            }
            count++;
        }
        read.close();
        return information;
    }
}







