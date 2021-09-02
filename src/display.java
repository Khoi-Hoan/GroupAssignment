import java.util.ArrayList;

abstract class DataDisplay {
    ArrayList<String> groups;
    ArrayList<Integer> values;

    public DataDisplay(){
        groups= new ArrayList<>();
        values = new ArrayList<>();
    }
    public void addDataPoint(String group, int value){
        groups.add(group);
        values.add(value);
    }

    abstract public void display();
}

class TabularDisplay extends DataDisplay {
    @Override
    public void display() {
        // Making table form
        System.out.println("-----------------------------------------------------------");
        System.out.format("%c %21s %6c %10s %10c\n",'|', "Range(First date- Last date)", '|', "Value",'|');
        System.out.println("|---------------------------------------------------------|");
        // insert data into table
        for (int i = 0; i < groups.size(); i++) {

            System.out.format("%c %9s %11c %12s %8c\n",'|', groups.get(i),'|', values.get(i),'|');
        }
        System.out.println("-----------------------------------------------------------");
    }
}
class ChartDisplay extends DataDisplay {
    @Override
    public void display() {
        //create a template to build a chart
        String[][] chart = new String[24][80];
        for (int j = 0; j < 24; j++){
            for (int i = 0; i < 80; i++){
                if (j == 0){
                    chart[j][i] = "-";
                }
                else if (i == 0){
                    chart[j][i]= "|";
                }
                else {
                    chart[j][i]= " ";
                }
            }
        }
        //needed values to calculate data position, distance and column height
        int noCollum = values.size();
        int xDistance = 80 / noCollum;
        int minValue = values.get(minIndex(values));
        int maxValue = values.get(maxIndex(values));
        int yDistance = (maxValue - minValue) / 23;
        if (yDistance < 1){
            yDistance = 1;
        }
        int xPos = xDistance / 2;
        if (xPos == 0) {xPos = 1;}
        //drawing column
        int recentValue;
        for (Integer value : values) {
            recentValue = minValue;
            for (int yPos = 1; yPos < 24; yPos++) {
                if (xPos < 80) {
                    if (recentValue <= value) {
                        recentValue += yDistance;
                        chart[yPos][xPos] = "*";
                    }
                }
            }
            xPos += xDistance;
        }
        //display the chart
        for (int j = 23; j > -1; j--){
            for (int i = 0; i < 80; i++){
                System.out.print(chart[j][i]);
            }
            System.out.println();
        }
    }
    public static int minIndex(ArrayList<Integer> list) {
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < list.get(index)) {
                index = i;
            }
        }
        return index;
    }
    public static int maxIndex(ArrayList<Integer> list) {
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > list.get(index)) {
                index = i;
            }
        }
        return index;
    }
}


