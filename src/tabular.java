
import java.util.ArrayList;


public class tabular {
    public static void main(String[] args)  {
        ArrayList<String> date1 = new ArrayList<>();
        date1.add("02/13/2021");
        date1.add("02/21/2021");
        date1.add("02/31/2021");
        ArrayList<String> date2 = new ArrayList<>();
        date2.add("02/16/2021");
        date2.add("02/16/2021");
        date2.add("03/31/2021");
        ArrayList<Integer> value = new ArrayList<>();
        value.add(32132143);
        value.add(92135321);
        value.add(74266321);
        tabularDisplay(date1,date2,value);
    }
        // create table form
        public static void tabularDisplay(ArrayList<String> date1,ArrayList<String> date2,ArrayList<Integer> value){
        System.out.println("-----------------------------------------------------------");
        System.out.format("%c %21s %13c %10s %10c\n",'|', "Range(date 1- date 2)", '|', "Value",'|');
        System.out.println("|---------------------------------------------------------|");
        // insert data into table
        for (int i = 0; i < date1.size(); i++) {

            System.out.format("%c %9s %c %9s %11c %12s %8c\n",'|', date1.get(i),'-', date2.get(i), '|', value.get(i),'|');
        }
        System.out.println("-----------------------------------------------------------");
    }

}


