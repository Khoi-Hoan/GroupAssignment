import java.util.*;

public class Chart {
    public static void main(String[] args) {
        ArrayList<Integer> i = new ArrayList<>();
        i.add(4);
        i.add(16);
        i.add(8);
        i.add(26);
        i.add(19);
        chartDisplay(chart(i));
    }
    public static String[][] chart(ArrayList<Integer> values){
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

        int noCollum = values.size();
        System.out.println(noCollum);
        int xDistance = 80 / noCollum;
        System.out.println(xDistance);
        int minValue = values.get(minIndex(values));
        System.out.println(minValue);
        int maxValue = values.get(maxIndex(values));
        System.out.println(maxValue);
        int yDistance = (maxValue - minValue) / 23;
        if (yDistance < 1){
            yDistance = 1;
        }
        System.out.println(yDistance);
        int xPos = xDistance / 2;
        if (xPos == 0) {xPos = 1;}
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
        return chart;
    }

    public static void chartDisplay(String[][] chart){
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
