import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.time.format.DateTimeFormatter;
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
    LocalDate date;
    int new_case;
    int new_death;
    int people_vaccinated;
    float population;

    public Data (String iso_code, String continent, String location, LocalDate date, int new_case, int new_death, int people_vaccinated,  float population) {
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

    public static void read_amount(int the_index_length){

    }
    //read file and create object based on data then put it in the array
    public static HashMap<String, Data> readFile() throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("Data.txt"));
        String i;
        HashMap<String, Data> information = new HashMap<>();
        int count = 0;
        while ((i = read.readLine()) != null) {
            if(count != 0) {
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

                if(s.length >= 5 && !s[4].equals("")) {
                    new_case = Integer.parseInt(s[4]);
                }

                if(s.length >= 6 && !s[5].equals("")) {
                    new_death = Integer.parseInt(s[5]);
                }
                if(s.length >= 7 && !s[6].equals("")) {
                    people_vaccinated = Integer.parseInt(s[6]);
                }
                if(s.length >= 8 && !s[7].equals("")) {
                    population = Float.parseFloat(s[7]);
                }
                Data newData = new Data(iso_code, continent, location, date, new_case, new_death, people_vaccinated, population);
                information.put(location + date ,newData);
            }
            count++;
        }
        read.close();
        return information;
    }

    // Get user option
    public static Scanner userOption(){
        System.out.println("There are three option:");
        System.out.println("1)New Cases");
        System.out.println("2)New Deaths");
        System.out.println("3)People Vaccinated");
        Scanner sc= new Scanner(System.in);
        System.out.println("Your option is ");
        String option= sc.next();
        return userOption();
    }
// this function can get the metric and the total case, death
    public static int sumDataINArrayList(ArrayList<Integer> numbers){
        int sum = 0;
        for(int i = 0; i<numbers.size(); i++){
            sum+=numbers.get(i);
        }
        return sum;
    }

// this function can get data from vaccine column
    public static int[] subtractData(int[] lastDay,int[] firstDay){
       int[] equal = new int[0];
        for (int i=0;i<lastDay.length;i++){
                equal[i]=lastDay[i]-firstDay[i];
            }
            return equal;
        }

    }





