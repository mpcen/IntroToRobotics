import java.util.*;
import java.io.*;

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
    int y = Integer.parseInt(scanner.nextInt()) + 2;
    //real x = 5 || padded x = 7
    int x = Integer.parseInt(scanner.nextInt()) + 2;

    //Create Array matrix[y][x]
    Integer[][] matrix = new Integer[y][x];

    // i = y-axis
    for(int i = 0; i < y; i++){
      for(int j = 0; j < x; j++){
        matrix[y][x] = Integer.parseInt(scanner.next());
        System.out.printf("%d", matrix[y][x]);
      }
      System.out.println();
    }
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
