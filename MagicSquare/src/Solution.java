import java.io.*;
import java.util.*;

public class Solution {

    private static int MAGIC_NUMBER = 15;

    // Complete the formingMagicSquare function below.
    public static int formingMagicSquare(int[][] s) {
        int cost = 0;

        for (int i=0; i < 3; i++) {
            for (int j=0; j < 3; j++) {
                if (!verifyValue(s, i, j)){
                    cost = cost + findRightValue(s, i, j);
                }
            }
        }

        return cost;
    }

    private  final Scanner scanner = new Scanner(System.in);

    public static  void main(String[] args) throws IOException {

        int[][] s0 = new int[][] { {5, 3, 4}, {1, 5, 8}, {6, 4, 2} };
        int[][] s1 = new int[][] { {4, 9, 2}, {3, 5, 7}, {8, 1, 5} };
        int[][] s2 = new int[][] { {4, 8, 2}, {4, 5, 7}, {6, 1, 6} };
        int[][] s3 = new int[][] { {4, 5, 8}, {2, 4, 1}, {1, 9, 7} };
        int[][] s4 = new int[][] { {7, 6, 5}, {7, 2, 8}, {5, 3, 4} };

        int result = formingMagicSquare(s0);
        System.out.println("Custo: "+result);
        result = formingMagicSquare(s1);
        System.out.println("Custo: "+result);
        result = formingMagicSquare(s2);
        System.out.println("Custo: "+result);
        result = formingMagicSquare(s3);
        System.out.println("Custo: "+result);
        result = formingMagicSquare(s4);
        System.out.println("Custo: "+result);

    }

    public static int sumLine(int[][] s, int line){
        //Variables
        int sum = 0;
        for (int j=0; j < 3; j++) {
            sum=sum+s[line][j];
        }
        return sum;
    }

    public static boolean testLine (int fixedValue, int s[][]){

        int i=fixedValue;
        int j=0;
        int sum=0;

        while(j<3){
            sum=sum+(s[i][j]);
            j++;
        }

        return isMagicNumber(sum);
    }

    public static boolean testColumn(int fixedValue, int s[][]){

        int j=fixedValue;
        int i = 0;
        int sum=0;
        while(i<3){
            sum=sum+(s[i][j]);
            i++;
        }

        return isMagicNumber(sum);
    }

    public static boolean testMainDiagonal(int[][] s, boolean needMainDiagonalTest){

        int sum;

        if(needMainDiagonalTest){
            sum=0;
            for (int i = 0; i<3; i++){
                for (int j = 0; j<3; j++){
                    if(i==j){
                        sum=sum+(s[i][j]);
                    }
                }
            }
        }else{
            sum =MAGIC_NUMBER;
        }

        return isMagicNumber(sum);
    }

    public static boolean testSecondaryDiagonal(int s[][], boolean needSecondDiagonalTest){

        int sum;

        if(needSecondDiagonalTest){
            sum =0;
            for (int i = 0; i<3; i++){
                for (int j = 0; j<3; j++){
                    if(i+j==2){
                        sum=sum+(s[i][j]);
                    }
                }
            }
        }else{
            sum=MAGIC_NUMBER;
        }

        return isMagicNumber(sum);
    }

    public static boolean isMagicNumber(int result){
        boolean isMagicNumber = false;
        if(result==MAGIC_NUMBER)
            isMagicNumber=true;
        return isMagicNumber;
    }

    public static boolean verifyValue(int s[][], int line, int column) {

        boolean isValueOK = true;

        boolean needMainDiagonalTest = (column == line) ? true : false;
        boolean needSecDiagonalTest = (column + line == 2) ? true : false;
        if (!testColumn(column, s) || !testLine(line, s) || !testMainDiagonal(s, needMainDiagonalTest) || !testSecondaryDiagonal(s, needSecDiagonalTest)) {
            isValueOK = false;
        }
        return isValueOK;
    }

    public static int findRightValue(int s[][], int line, int column){

        int aux = 0;
        int cost = 0;

        int currentSum = sumLine(s, line);

        //Fixing value in the line whose magicSquare wasn't found
        aux = s[line][column];
        int delta = MAGIC_NUMBER - currentSum;
        s[line][column] = s[line][column] + delta;
        cost =  Math.abs(s[line][column] - (s[line][column] + delta));
        if (!verifyValue(s, line, column)){
            s[line][column] = aux;
            cost = cost + findRightValueCornerCase(s, line, column);
        }

         return cost;
    }

    public static int findRightValueCornerCase(int s[][], int line, int column){

        int aux = 0;
        int costAux = 0;
        int cost = 0;
        boolean rightValueFound = false;
        int currentSum = sumLine(s, line);

        aux = s[line][column];
        int delta = MAGIC_NUMBER - currentSum;
        s[line][column] = s[line][column] + delta;
        //Now going to the next line to test if the change above can find the magic square
        for(int i=line; i<3; i++) {
            int sum = sumLine(s, line);
            int delta1 = MAGIC_NUMBER - sum;
            int aux1 = s[line][column];
            s[line][column] = s[line][column] + delta1;
            if (verifyValue(s, line, column)) {
                cost = costAux + Math.abs(s[line][column] - (s[line][column] + delta1));
            } else {
                s[line][column] = aux;
                s[line][column] = aux1;
            }
        }

        return cost;
    }
}