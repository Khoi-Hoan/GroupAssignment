import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;


public class menu {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        HashMap<String, Data> information = Data.readFile();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        input(sc, information, formatter);

    }
    public static void input(Scanner sc, HashMap<String, Data> information, DateTimeFormatter formatter){
        Program:
        while(true) {
            List<String> list_of_location = new ArrayList<>();
            String current_location="";
            for(Data chosenday:information.values()){ //get all data within the hashmap
                if(!current_location.equals(chosenday.getLocation())){
                    list_of_location.add(chosenday.getLocation());
                }
            }
            String location;
            while(true) {
                System.out.print("Please enter the location you want to get data about: ");
                String not_edited_location = sc.nextLine();
                String firstLetStr = not_edited_location.substring(0, 1);
                // Get remaining letter using substring
                String remLetStr = not_edited_location.substring(1);

                // convert the first letter of String to uppercase
                firstLetStr = firstLetStr.toUpperCase();
                location = firstLetStr + remLetStr;// always capitalize the first letter.
                if(list_of_location.contains(location)){
                    break;
                }
                else{
                    System.out.println("You have input an invalid location");
                }
            }
            List<Data> sorted_list = list_sorted_based_on_date(information, location);
            System.out.println("""
                    Please choose 1 out of 3 option to select to get the data: \s
                    1. Start date and end date
                    2. A number of days or weeks from a particular date
                    3. A number of days or weeks to a particular date""");
            List<LocalDate> date_being_used;
            while(true) {
                System.out.print("Your option:  ");
                int date_type_input = Integer.parseInt(sc.nextLine());
                if (date_type_input == 1) {
                    date_being_used = get_date_option_1(sc, formatter);
                    break;
                } else if (date_type_input == 2) {
                    date_being_used = get_date_option_2(sc, formatter, sorted_list);
                    break;
                } else if (date_type_input == 3) {
                    date_being_used = get_date_option_3(sc, formatter, sorted_list);
                    break;
                } else {
                    System.out.println("You have put in invalid number.");
                }
            }
            List<Data> List_of_data_being_used = new ArrayList<>();
            for (LocalDate selected_date : date_being_used) {
                List_of_data_being_used.add(information.get(location + selected_date));
            }
            List<List<Data>> Group;
            System.out.println("""
                    Please choose 1 out of 3 option to choose grouping style: \s
                    1. No grouping
                    2. Number of groups
                    3. Number of days""");
            while(true) {
                System.out.print("Your option:  ");
                int group_type_input = Integer.parseInt(sc.nextLine());
                if (group_type_input == 1) {
                    Group = group_date_option_1(List_of_data_being_used);
                    break;
                } else if (group_type_input == 2) {
                    System.out.println("Please enter the amount of group you want to split the data into: ");
                    int number_of_group = Integer.parseInt(sc.nextLine());
                    Group = group_date_option_2(List_of_data_being_used, number_of_group);
                    break;
                } else if (group_type_input == 3) {
                    boolean correct_input = true;
                    int number_of_group = 0;
                    while (correct_input) {
                        System.out.println("Please enter the amount of days you want to have inside a group: ");
                        number_of_group = Integer.parseInt(sc.nextLine());
                        if (date_being_used.size() % number_of_group == 0) {
                            correct_input = false;
                        } else {
                            System.out.println("You can't divide the chosen amount of date to your given number, please try again");
                        }
                    }
                    Group = group_date_option_3(List_of_data_being_used, number_of_group);
                    break;
                } else {
                    System.out.println("You have put in invalid number.");
                }
            }
            List<String> key_list = create_key_list(Group, formatter);
            List<Integer> value_list = new ArrayList<>();
            int metric_option = Metric(sc); // #1 =new case, #2 = new death, #3 = ppl vaccinated.
            System.out.println("""
                    Please choose 1 out of 3 option to select to calculate data: \s
                    1. New total
                    2. Up to""");
            while(true){
                System.out.print("Your option:  ");
                int calculate_data_style = Integer.parseInt(sc.nextLine());
                if (calculate_data_style == 1) {
                    if (metric_option == 3) {
                        for (List<Data> smaller_list : Group) {
                            List<Integer> vaccinated_amount = turn_vaccinated_amount_new_total_method_into_int_list(smaller_list, information, location, sorted_list);
                            value_list.add(SumDataInAList(vaccinated_amount));
                        }
                    }
                    if (metric_option == 1 || metric_option == 2) {
                        for (List<Data> smaller_list : Group) {
                            List<Integer> list_of_result = get_new_case_or_death_to_list(smaller_list, metric_option);
                            value_list.add(SumDataInAList(list_of_result));
                        }
                    }
                    break;
                } else if (calculate_data_style == 2) {

                    if (metric_option == 3) {
                        int smallest_value = 0;//in case all the group has value = 0 (grouping 1)
                        for (List<Data> smaller_list : Group) {
                            int vaccinated_amount = get_up_to_vaccinated(smaller_list, smallest_value);
                            value_list.add(vaccinated_amount);
                            break;
                        }
                    }
                    if (metric_option == 1 || metric_option == 2) {
                        for (List<Data> smaller_list : Group) {
                            int list_of_result = Upto_newcase_or_newdeath_to_list(smaller_list, metric_option, sorted_list);
                            value_list.add(list_of_result);
                            break;
                        }
                    }
                    break;
                }
                else{
                    System.out.println("You have put in invalid number");
                }
            }
            DataDisplay result = chooseDisplay();
            for (int i = 0; i < key_list.size(); i++) {
                result.addDataPoint(key_list.get(i), value_list.get(i));
            }
            result.display();
            while(true) {
                System.out.print("Would you like to continue? (Y/N)");
                String user_input_for_continue = sc.nextLine();
                char continue_or_not = user_input_for_continue.charAt(0);
                if (continue_or_not == 'N') {
                    System.out.println("Thank you for using our program");
                    break Program;
                } else if (continue_or_not == 'Y') {
                    System.out.println("We will start from the beginning again.");
                } else {
                    System.out.println("Please only write either N or Y");
                }
            }
        }

    }

    public static List<String> create_key_list(List<List<Data>> list_of_data, DateTimeFormatter format){
        int index_count = check_index_count_for_data(list_of_data); //in case there is 1 day in each group
        List<String> key_list = new ArrayList<>();
        if (index_count == 1){
            for(List<Data> smaller_list : list_of_data){
                for (Data day: smaller_list){
                    String result = day.getDate().format(format);
                    key_list.add(result); //format: each day is a key
                }
            }
        }
        else{
            for(List<Data> smaller_list : list_of_data){
                String first_day = smaller_list.get(0).getDate().format(format);
                String last_day = smaller_list.get(smaller_list.size()-1).getDate().format(format);
                String result = first_day + "-" + last_day; //format first date - last date
                key_list.add(result);
            }
        }
        return key_list;
    }
    public static int Upto_newcase_or_newdeath_to_list(List<Data> list_of_data, int metric, List<Data> sorted_list){
        int current_up_to_date = 0;
        int last_day = 0;
        if(metric == 1) {
            for (Data chosenday : sorted_list) {
                if (chosenday.getDate() == list_of_data.get(list_of_data.size() - 1).getDate()) {
                    current_up_to_date = current_up_to_date + chosenday.getNew_case();
                    last_day = current_up_to_date;
                } else {
                    current_up_to_date = current_up_to_date + chosenday.getNew_case();
                }
            }
        }
        else{
            for (Data chosenday : sorted_list) {
                    if (chosenday.getDate() == list_of_data.get(list_of_data.size() - 1).getDate()) {
                        current_up_to_date = current_up_to_date + chosenday.getNew_death();
                        last_day = current_up_to_date;
                        chosenday.display();

                    } else {
                        current_up_to_date = current_up_to_date + chosenday.getNew_death();
                    }
                }
            }
        return last_day;
    }
    public static int get_up_to_vaccinated(List<Data> list_data, int smallest_value){
        int result;
        int number_of_wrong_data = 0; //the data might be wrong. (random 0 in the data)
        while(true){
            result = list_data.get(list_data.size() -1 - number_of_wrong_data).getPeople_vaccinated(); // get the last as long as the last not = 0
            if(result !=0){
                //might be chance that all the group has wrong data
                return Math.max(result, smallest_value);
            }
            else{
                number_of_wrong_data = number_of_wrong_data + 1; //1 index lower. not the last day
                if(number_of_wrong_data == list_data.size()){ // if it reach limit => it is = 0
                    //return the answer
                    return Math.max(result, smallest_value); // the first one
                }
            }
        }
    }

    public static List<Integer> get_new_case_or_death_to_list(List<Data> list_data, int metric){
        List<Integer> result = new ArrayList<>();
        if(metric == 1){ //get new case
            for(Data chosenday : list_data){
                result.add(chosenday.getNew_case()); //add to list
            }
        }
        if(metric == 2){ // get new death
            for(Data chosenday : list_data){
                result.add(chosenday.getNew_death()); //add to list
            }
        }
        return result;
    }
    public static List<Data> list_sorted_based_on_date(HashMap<String, Data> information, String location){
        List<Data> list = new ArrayList<>();
        for(Data chosenday:information.values()){ //get all data within the hashmap
            if(chosenday.getLocation().equals(location)){ //get all data in the chosen location
                list.add(chosenday); //add to a list
            }
        }
        list.sort(Comparator.comparing(Data::getDate)); //sort the list so that the first element is the first date
        return list;
    }
    public static List<Integer> turn_vaccinated_amount_new_total_method_into_int_list(List<Data> list_data, HashMap<String, Data> information, String location, List<Data> sorted_list){
        List<Integer> result = new ArrayList<>();
        for (Data chosenday : list_data){
            if(chosenday.getDate() != sorted_list.get(0).getDate()) {// not the first date
                LocalDate daytime = chosenday.getDate().minusDays(1); //minus 1 day to get "yesterday"
                int yesterday_vaccinated = information.get(location + daytime).getPeople_vaccinated();
                int today_vaccinated = chosenday.getPeople_vaccinated();
                int new_vaccinated_people_today = today_vaccinated - yesterday_vaccinated; //minus to get the new plus
                if(new_vaccinated_people_today < 0){
                    new_vaccinated_people_today = 0;//some data suddenly does not record vaccinated which break the code.
                    chosenday.setPeople_vaccinated(yesterday_vaccinated);
                }
                result.add(new_vaccinated_people_today); // add data
            }
            else{
                result.add(chosenday.getPeople_vaccinated());//it is the first date
            }
        }

        return result; //return
    }

    //Get group data option 1
    public static List<List<Data>> group_date_option_1(List<Data> list_of_data){
        List<List<Data>> group_of_data = new ArrayList<>();
        for (Data day : list_of_data){ //loop through all the smaller list
            List<Data> smaller_list = new ArrayList<>(); // create a new list
            smaller_list.add(day);      // add element for a list and in this case (1 group contain 1 day)
            group_of_data.add(smaller_list);        //add group to the big group.
        }
        return group_of_data;
    }
    //Get group data option 2
    public static List<List<Data>> group_date_option_2(List<Data> list_of_data, int number_of_group){
        List<List<Data>> group_of_data = new ArrayList<>();
        int spare_day_amount = list_of_data.size()%number_of_group; //get the amount of spare day
        int amount_of_day_in_1_group = (int)Math.floor(list_of_data.size()/number_of_group); //get the amount of day per group
        int spare_day_passed = 0; //this is used to calculated amount of spare day left
        for(int group_number = 0; group_number < number_of_group; group_number++){
            List<Data> smaller_list = new ArrayList<>(); // create a new list
            if(group_number < spare_day_amount){ //group with spare day
                for(int i = 0; i < amount_of_day_in_1_group+1; i++){ // i = the day already in the group ( + 1 because + 1 spare day)
                    int current_index = group_number * amount_of_day_in_1_group + group_number  + i;//all day in 1 group and exclude the spare day and the day already in the group
                    smaller_list.add(list_of_data.get(current_index));      // add element for a list and in this case (1 group contain 1 day)
                }
                spare_day_passed = spare_day_passed + 1; // this is used to calculated amount of spare day left
            }
            else{
                for(int i = 0; i < amount_of_day_in_1_group; i++){ //group not having a spare day
                    int current_index = group_number * amount_of_day_in_1_group + i + spare_day_passed; //still + spare because they have gone through all the spare day(even = 0).
                    smaller_list.add(list_of_data.get(current_index));      // add element for a list and in this case (1 group contain 1 day)
                }
            }
            group_of_data.add(smaller_list);
        }
        return group_of_data;
    }
    //Get group data option 3
    public static List<List<Data>> group_date_option_3(List<Data> list_of_data, int number_of_date_in_a_group){
        List<List<Data>> group_of_data = new ArrayList<>();
        int amount_of_group = list_of_data.size()/number_of_date_in_a_group; //find the amount of group since the total % the amount of day/group = 0
        for(int group_number= 0; group_number < amount_of_group; group_number++){
            List<Data> smaller_list = new ArrayList<>(); // create a new list
            for(int i = 0; i < number_of_date_in_a_group; i++){ // i = the day already in the group
                int current_index = group_number * number_of_date_in_a_group + i;
                smaller_list.add(list_of_data.get(current_index));      // add element for a list and in this case (1 group contain 1 day)
            }
            group_of_data.add(smaller_list);
        }
        return group_of_data;
    }
//    GETTING USER INPUT DATE OPTION
    public static List<LocalDate> get_date_option_1(Scanner sc, DateTimeFormatter formatter) {
//        getting input (can add situation where user put in invalid number0)
        while(true) {
            try {
                System.out.print("Please enter the day of the starting date : "); //get start date
                String start_day = sc.nextLine();
                System.out.print("Please enter the month of the starting date : "); //get start month
                String start_month = sc.nextLine();
                System.out.print("Please enter the year of the starting date : "); //get start year
                String start_year = sc.nextLine();
                String start_date_S = start_month + "/" + start_day + "/" + start_year; //combine them
                // finish getting the start_date
                System.out.print("Please enter the day of the last date : "); //get end date
                String end_day = sc.nextLine();
                System.out.print("Please enter the month of the last date : "); //get end month
                String end_month = sc.nextLine();
                System.out.print("Please enter the year of the last date : "); //get end year
                String end_year = sc.nextLine();
                String end_date_S = end_month + "/" + end_day + "/" + end_year; //combine to get end date
                //finish getting the end date
                //turn the starting/end date into local date type
                LocalDate end_date = LocalDate.parse(end_date_S, formatter); //turn to local date format
                LocalDate start_date = LocalDate.parse(start_date_S, formatter);  //turn to local date format
                //get the list of local date
                return get_date_between_2_date(start_date, end_date);
            }
            catch(Exception e){
                System.out.println("You have put in invalid date. please try again.");
            }
        }
    }
    public static List<LocalDate> get_date_option_2(Scanner sc, DateTimeFormatter formatter, List<Data> sorted_list) {
//        getting input (can add situation where user put in invalid number0)
        int select_day;
        System.out.print("""
                Please select the option to select:\s
                1. Day
                2. Week\s
                """);
        while(true) {
            System.out.print("Your option: ");
            int option = Integer.parseInt(sc.nextLine());//get the input
            if (option == 1 || option == 2) {
                if (option == 1) {
                    System.out.print("Please enter the amount of days after the selected date: "); //get the input days
                    select_day = Integer.parseInt(sc.nextLine()); //day
                } else {
                    System.out.print("Please enter the amount of weeks after the selected date: "); //get the input weeks
                    select_day = Integer.parseInt(sc.nextLine()) * 7; //week * 7 day
                }
                //getting the selected date
                LocalDate selected;
                try {
                    System.out.print("Please enter the day of the selected date : "); //get the selected date
                    String selected_day = sc.nextLine();
                    System.out.print("Please enter the month of the selected date : "); //get the selected month
                    String selected_month = sc.nextLine();
                    System.out.print("Please enter the year of the selected date : "); //get the selected year
                    String selected_year = sc.nextLine();
                    String selected_day_S = selected_month + "/" + selected_day + "/" + selected_year; //get the date
                    selected = LocalDate.parse(selected_day_S, formatter); //turn it into local date
                }
                catch(Exception e){
                    System.out.println("You have put in invalid date. Please try again");
                    continue;
                }
                for (Data chosendate : sorted_list) {
                    if (chosendate.getDate().isEqual(selected)) {
                        int index = sorted_list.indexOf(chosendate);
                        if (index >= select_day) {
                            LocalDate end_date = selected.plusDays(select_day); //get the end date
                            /* getting the end date */
                            return get_date_between_2_date(selected, end_date);
                        }
                        else{
                            System.out.println("your ending date is too far from your starting date");
                            System.out.println("Please choose again");
                        }
                    }
                }
            }
            else{
                System.out.println("You have put in invalid number");
                System.out.println("Please choose the available number of the option");
            }
        }
    }
    public static List<LocalDate> get_date_option_3(Scanner sc, DateTimeFormatter formatter, List<Data> sorted_list){
        int select_day;
        System.out.print("""
                Please select the option to select:\s
                1. Day
                2. Week\s
                """);
        while(true) {
            System.out.print("Your option: ");
            int option = Integer.parseInt(sc.nextLine());//get the input
            if (option == 1 || option == 2){
                    if (option == 1) {
                        System.out.print("Please enter the amount of days before the selected date: "); //get the input days
                        select_day = Integer.parseInt(sc.nextLine());
                    } else {
                        System.out.print("Please enter the amount of weeks before the selected date: "); //get the input weeks
                        select_day = Integer.parseInt(sc.nextLine()) * 7; // week = day * 7
                    }
                //getting the selected date
                    LocalDate selected;
                    try {
                        System.out.print("Please enter the day of the selected date: "); //get the selected date
                        String selected_day = sc.nextLine();
                        System.out.print("Please enter the month of the selected date: "); //get the selected month
                        String selected_month = sc.nextLine();
                        System.out.print("Please enter the year of the selected date: "); //get the selected year
                        String selected_year = sc.nextLine();
                        String selected_day_S = selected_month + "/" + selected_day + "/" + selected_year; //combine and get end day
                        selected = LocalDate.parse(selected_day_S, formatter); //turn it into located type
                    }
                    catch(Exception e){
                        System.out.println("You have put in invalid date, please try again");
                        continue;
                    }
                for(Data chosendate: sorted_list){
                    if(chosendate.getDate().isEqual(selected)){
                        int index = sorted_list.indexOf(chosendate);
                        if(index <= select_day){
                            LocalDate start_date = selected.minusDays(select_day); //get the starting date
                            return get_date_between_2_date(start_date, selected);
                        }
                        else{
                            System.out.println("your starting date is too far from the ending date");
                            System.out.println("Please choose again");
                        }
                    }
                }
            }
            else{
                System.out.println("You have put in invalid number");
                System.out.println("Please choose the available number of the option");
            }
        }
    }

    public static int Metric(Scanner sc) {
        System.out.println("Choose between 3 of this to get the metric you want");
        System.out.println("1)New Cases");
        System.out.println("2)New Deaths");
        System.out.println("3)People Vaccinated");
        while (true) {
            System.out.print("Your option: "); //get user input
            int op = Integer.parseInt(sc.nextLine());
            if (op != 1 && op != 2 && op != 3) {
                System.out.println("There is an error in your option");
                System.out.println("Please choose the available number of the option"); //return and force another answer
            } else {
                return op; //return answer
            }
        }
    }
    //this is to check if the index count = 1 or not
    public static int check_index_count_for_data(List<List<Data>> list_of_data){
        int index_count = 2;
        for (List<Data> smaller_list: list_of_data) //count the amount of element inside a list in case there is only 1 in a group
            if (smaller_list.size() == 1) {
                index_count = 1;
                break;
            }
        return index_count; // =2 for > 1
    }
    public static List<LocalDate> get_date_between_2_date(LocalDate start_date, LocalDate end_date){
        List<LocalDate> listOfDates = start_date.datesUntil(end_date).collect(Collectors.toList()); //get the list of date
        listOfDates.add(end_date);// add the end date too.
        return listOfDates;
    }
    public static int SumDataInAList(List<Integer> numbers) {
        int sum = 0;
        for (Integer number : numbers) {
            sum += number; //sum data in a list of integer
        }
        return sum;
    }
    //choose what type of display type you want
    public static DataDisplay chooseDisplay() {
        System.out.println("There are 2 display types: ");
        System.out.println("1. Tabular");
        System.out.println("2. Chart");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Your option: ");
            int op = sc.nextInt();
            if (op == 1) {
                return new TabularDisplay();
            }
            else if (op == 2) {
                return new ChartDisplay();
            }
            else {
                System.out.println("There is an error in your option");
                System.out.println("Please choose the available number of the option");
            }
        }
    }
}


