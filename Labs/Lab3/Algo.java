import java.util.*;
import java.io.*;
import java.*;
public class Algo{

  public Scanner scanner;

  public void openFile(){
    try{
      scanner = new Scanner(new File("input.txt"));
    }
    catch(Exception e){
      System.out.println("Could not find file!");
    }
  }

  public void readFile(){
    //real y = 4 || padded y = 6
    int y = Integer.parseInt(scanner.next()) + 2;
    //System.out.printf("y = %d\n", y);

    //real x = 5 || padded x = 7
    int x = Integer.parseInt(scanner.next()) + 2;
    //System.out.printf("x = %d\n", x);

    //Create Array matrix[y][x]
    Integer[][] matrix = new Integer[y][x];
    String[] strArray = new String[y];

    for(int i = 0; i < y; i++){
      strArray[i] = scanner.next();
      System.out.println(strArray[i]);
    }

    char ch = strArray[2].charAt(4);
    System.out.println(ch);

 /*
    // i = y-axis
    for(int i = 0; i < y; i++){
      System.out.printf("i = %d | ", i);
      for(int j = 0; j < x; j++){
        System.out.print(scanner.next());
        //matrix[i][j] = Integer.parseInt(scanner.next());
      }
      System.out.println();
    }*/

  }

  public void closeFile(){
    scanner.close();
  }
  public static void main(String[] args){
    Algo execute = new Algo();
    execute.openFile();
    execute.readFile();
    execute.closeFile();
  }
}
