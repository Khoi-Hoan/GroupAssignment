import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.util.stream.Collectors;


public class menu {
    public static void main(String[] args) throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);
        HashMap<String, Data> information = Data.readFile();
        for (String data : information.keySet() ) {
            information.get(data).display();
        }
        input(sc, information);

    }
    public static List<Integer> subtractData(int[] lastDay,int[] firstDay){
            List<Integer> equal = new ArrayList<>();
        for (int i=0;i<lastDay.length;i++){
            equal.add(lastDay[i]-firstDay[i]);
        }
        return equal;
    }

    public static void input(Scanner sc, HashMap<String, Data> information){
        System.out.print("Please enter the location you want to get data about: ");
        String location = sc.nextLine();
        System.out.print("""
                Please choose 1 out of 3 option to select to get the data.\s
                1. Start date and end date
                2. A number of days or weeks from a particular date
                3. A number of days or weeks to a particular date
                """);
        int date_type_input = Integer.parseInt(sc.nextLine());
        System.out.println(location);
        List<LocalDate> date_being_used = new ArrayList<>();
        if (date_type_input == 1){
            date_being_used = get_date_option_1(sc);
        }
        else if(date_type_input == 2){
            date_being_used = get_date_option_2(sc);

        }
        else if(date_type_input == 3){
            date_being_used = get_date_option_2(sc);
        }
        else{
            System.out.println("You have put in invalid number.");
            return;
        }
        List<Data> List_of_data_being_used = new ArrayList<>();
        for (LocalDate selected_date : date_being_used){
            List_of_data_being_used.add(information.get(location + selected_date));
        }
//        for (Data beingchoosen : List_of_data_being_used){
//            beingchoosen.display();
//        }
        List<List<Data>> Group = new ArrayList<>();
        System.out.print("""
                Please choose 1 out of 3 option to select to get the data.\s
                1. No grouping
                2. Number of groups
                3. Number of days
                """);
        int group_type_input = Integer.parseInt(sc.nextLine());
        if (group_type_input  == 1){
            Group = group_date_option_1(List_of_data_being_used);
        }
        else if(group_type_input  == 2){
            System.out.println("Please enter the amount of group you want to split the data into: ");
            int number_of_group = Integer.parseInt(sc.nextLine());
            Group = group_date_option_2(List_of_data_being_used, number_of_group);

        }
        else if(group_type_input  == 3){
            boolean correct_input = true;
            int number_of_group = 0;
            while(correct_input){
                System.out.println("Please enter the amount of days you want to have inside a group: ");
                number_of_group = Integer.parseInt(sc.nextLine());
                if(date_being_used.size()%number_of_group == 0){
                    correct_input = false;
                }
                else{
                    System.out.println("You can't divide the choosen amount of date to your given number, please try again");
                }
            }
            Group = group_date_option_3(List_of_data_being_used, number_of_group);
        }
        else{
            System.out.println("You have put in invalid number.");
            return;
        }
    }
    //Get group data option 1
    public static List<List<Data>> group_date_option_1(List<Data> list_of_data){
        List<List<Data>> group_of_data = new ArrayList<List<Data>>();
        for (Data day : list_of_data){
            List<Data> smaller_list = new ArrayList<Data>(); // create a new list
            smaller_list.add(day);      // add element for a list and in this case (1 group contain 1 day)
            group_of_data.add(smaller_list);        //add group to the big group.
        }
//        for (List<Data> smallerlist: group_of_data){
//            for(Data smallerdata: smallerlist){
//                smallerdata.display();
//            }
//                System.out.println("End list *");
//        }
        return group_of_data;
    }
    //Get group data option 2
    public static List<List<Data>> group_date_option_2(List<Data> list_of_data, int number_of_group){
        List<List<Data>> group_of_data = new ArrayList<List<Data>>();
        int spare_day_amount = list_of_data.size()%number_of_group;
        System.out.println(spare_day_amount);
        int amount_of_day_in_1_group = (int)Math.floor(list_of_data.size()/number_of_group);
        System.out.println(amount_of_day_in_1_group);
        for(int group_number = 0; group_number < number_of_group; group_number++){
            List<Data> smaller_list = new ArrayList<>(); // create a new list
            if(group_number < spare_day_amount){
                for(int i = 0; i < amount_of_day_in_1_group+1; i++){
                    int current_index = group_number * amount_of_day_in_1_group + group_number  + i;
                    smaller_list.add(list_of_data.get(current_index));      // add element for a list and in this case (1 group contain 1 day)
                }
                group_of_data.add(smaller_list);

            }
            else{
                for(int i = 0; i < amount_of_day_in_1_group; i++){
                    int current_index = group_number * amount_of_day_in_1_group + i + group_number;
                    smaller_list.add(list_of_data.get(current_index));      // add element for a list and in this case (1 group contain 1 day)
                }
                group_of_data.add(smaller_list);

            }
        }
        for (List<Data> c : group_of_data){
            for (Data e : c){
                e.display();
            }
            System.out.println("*");
        }
        return group_of_data;
    }
    //Get group data option 3
    public static List<List<Data>> group_date_option_3(List<Data> list_of_data, int number_of_date_in_a_group){
        List<List<Data>> group_of_data = new ArrayList<List<Data>>();
        int amount_of_group = list_of_data.size()/number_of_date_in_a_group;
        for(int group_number= 0; group_number < amount_of_group; group_number++){
            List<Data> smaller_list = new ArrayList<>(); // create a new list
            for(int i = 0; i < number_of_date_in_a_group; i++){
                int current_index = group_number * number_of_date_in_a_group + i;
                smaller_list.add(list_of_data.get(current_index));      // add element for a list and in this case (1 group contain 1 day)
            }
            group_of_data.add(smaller_list);
        }
        for (List<Data> c : group_of_data){
            for (Data e : c){
                e.display();
            }
            System.out.println("*");
        }
        return group_of_data;
    }
//    GETTING USER INPUT DATE OPTION
    public static List<LocalDate> get_date_option_1(Scanner sc) {
//        getting input (can add situation where user put in invalid number0)
        System.out.print("Please enter the day of the starting date : ");
        String start_day = sc.nextLine();
        System.out.print("Please enter the month of the starting date : ");
        String start_month = sc.nextLine();
        System.out.print("Please enter the year of the starting date : ");
        String start_year = sc.nextLine();
        String start_date_S = start_month + "/" + start_day + "/" + start_year;
        // finish getting the start_date
        System.out.print("Please enter the day of the last date : ");
        String end_day = sc.nextLine();
        System.out.print("Please enter the month of the last date : ");
        String end_month = sc.nextLine();
        System.out.print("Please enter the year of the last date : ");
        String end_year = sc.nextLine();
        String end_date_S = end_month + "/" + end_day + "/" + end_year;
        //finish getting the end date
        //turn the starting/end date into localdate type
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate end_date = LocalDate.parse(end_date_S, formatter);
        LocalDate start_date = LocalDate.parse(start_date_S, formatter);
        //get the list of localdate
        List<LocalDate> listOfDates = get_date_between_2_date(start_date, end_date);
        for (LocalDate c : listOfDates){
            System.out.println(c);
        }
        return listOfDates;
    }
    public static List<LocalDate> get_date_option_2(Scanner sc) {
//        getting input (can add situation where user put in invalid number0)
        int select_day = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        System.out.print("Please select the option to select: " +
                "\n1. Day" +
                "\n2. Week \n" );
        int option = Integer.parseInt(sc.nextLine());
        if (option == 1){
            System.out.print("Please enter the amount of days after the selected date: ");
            select_day = Integer.parseInt(sc.nextLine());
        }
        else if(option == 2){
            System.out.print("Please enter the amount of weeks after the selected date: ");
            select_day = Integer.parseInt(sc.nextLine()) * 7;
        }
        else{
            System.out.println("You have put in invalid number;");
        }
        //getting the selected date
        System.out.print("Please enter the day of the selected date : ");
        String selected_day = sc.nextLine();
        System.out.print("Please enter the month of the selected date : ");
        String selected_month = sc.nextLine();
        System.out.print("Please enter the year of the selected date : ");
        String selected_year = sc.nextLine();
        String selected_day_S = selected_month + "/" + selected_day + "/" + selected_year;
        LocalDate selected = LocalDate.parse(selected_day_S, formatter);
        LocalDate end_date = selected.plusDays(select_day);
        //getting the end date
        List<LocalDate> listOfDates = get_date_between_2_date(selected, end_date);
        for (LocalDate c : listOfDates){
            System.out.println(c);
        }
        return listOfDates;
    }
    public static List<LocalDate> get_date_option_3(Scanner sc) throws ParseException {
        int select_day = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        System.out.print("Please select the option to select: " +
                "\n1. Day" +
                "\n2. Week \n" );
        int option = Integer.parseInt(sc.nextLine());
        if (option == 1){
            System.out.print("Please enter the amount of days before the selected date: ");
            select_day = Integer.parseInt(sc.nextLine());
        }
        else if(option == 2){
            System.out.print("Please enter the amount of weeks before the selected date: ");
            select_day = Integer.parseInt(sc.nextLine()) * 7;
        }
        else{
            System.out.println("You have put in invalid number;");
        }
        //getting the selected date
        System.out.print("Please enter the day of the selected date : ");
        String selected_day = sc.nextLine();
        System.out.print("Please enter the month of the selected date : ");
        String selected_month = sc.nextLine();
        System.out.print("Please enter the year of the selected date : ");
        String selected_year = sc.nextLine();
        String selected_day_S = selected_month + "/" + selected_day + "/" + selected_year;
        LocalDate selected = LocalDate.parse(selected_day_S, formatter);
        LocalDate end_date = selected.minusDays(select_day);
        //getting the end date
        List<LocalDate> listOfDates = get_date_between_2_date(selected, end_date);
        for (LocalDate c : listOfDates){
            System.out.println(c);
        }
        return listOfDates;
    }
    public static List<LocalDate> get_date_between_2_date(LocalDate start_date, LocalDate end_date){
        List<LocalDate> listOfDates = start_date.datesUntil(end_date).collect(Collectors.toList());
        listOfDates.add(end_date);
        return listOfDates;
    }
}


