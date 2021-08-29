import java.util.ArrayList;

public class tabular {
    public static void main(String[] args) {
        String[][] table = new String[17][];
        table[0] = new String[]{"2", "5", "123522"};
        table[1] = new String[]{"5312132", "425345", "1535254"};
        table[2] = new String[]{"434983", "123535", "474453"};
        table[3] = new String[]{"213454534", "5232323", "2"};
        table[4] = new String[]{"2", "5", "123522"};
        table[5] = new String[]{"5312132", "425345", "1535254"};
        table[6] = new String[]{"434983", "123535", "474453"};
        table[7] = new String[]{"213454534", "5232323", "2"};
        table[8] = new String[]{"5312132", "425345", "1535254"};
        table[9] = new String[]{"434983", "123535", "474453"};
        table[10] = new String[]{"213454534", "52332323", "2"};
        table[11] = new String[]{"5312132", "425345", "1535254"};
        table[12] = new String[]{"4344983", "123535", "474453"};
        table[13] = new String[]{"213454534", "52324323", "2"};
        table[14] = new String[]{"5312132", "425345", "1535254"};
        table[15] = new String[]{"434983", "1235435", "4744453"};
        table[16] = new String[]{"213454534", "5232323", "2"};
        tabularDisplay(table);
    }
        // create table form
        public static void tabularDisplay(String[][] tabular){
        System.out.println("-----------------------------------------------------------");
        System.out.format("%c %21s %13c %10s %10c\n",'|', "Range(date 1- date 2)", '|', "Value",'|');
        System.out.println("|---------------------------------------------------------|");
        // insert data into table
        for (int i = 0; i < tabular.length; i++) {
            Object[] row = tabular[i];
            System.out.format("%c %9s %4c %9s %10c %9s %11c\n",'|', row[0],'-', row[1], '|', row[2],'|');
        }
        System.out.println("-----------------------------------------------------------");
    }

}


