
import java.util.ArrayList;


public class tabular {
    public static void main(String[] args)  {
        ArrayList<String> date = new ArrayList<>();
        date.add("02/13/2021 - 02/16/2021");
        date.add("02/21/2021 - 02/16/2021");
        date.add("02/31/2021 - 03/31/2021");
        ArrayList<Integer> value = new ArrayList<>();
        value.add(32132143);
        value.add(92135321);
        value.add(74266321);
        tabularDisplay(date,value);
    }
        // create table form
        public static void tabularDisplay(ArrayList<String> date,ArrayList<Integer> value){
        // Making table form
        System.out.println("-----------------------------------------------------------");
        System.out.format("%c %21s %6c %10s %10c\n",'|', "Range(First date- Last date)", '|', "Value",'|');
        System.out.println("|---------------------------------------------------------|");
        // insert data into table
        for (int i = 0; i < date.size(); i++) {

            System.out.format("%c %9s %11c %12s %8c\n",'|', date.get(i),'|', value.get(i),'|');
        }
        System.out.println("-----------------------------------------------------------");
    }

}


