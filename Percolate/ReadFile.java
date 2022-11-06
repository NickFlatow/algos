import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class ReadFile {
  private Percolation p;
  private boolean first = true;
  
  public static void main(String[] args) {
    ReadFile rf = new ReadFile();
    rf.readFile("./testfiles/input10.txt");
  }

  public void readFile(String file){
    try {
      File myObj = new File(file);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        String[] splited = data.split("\\s+");
        
        if (this.first){
          this.p = new Percolation(Integer.parseInt(splited[0]));
          first = false;
        }else{
          int row = Integer.parseInt(splited[0]);
          int col = Integer.parseInt(splited[1]);
          // System.out.println("System Percolates: " + p.percolates());

          this.p.open(row,col);
          if(p.numberOfOpenSites() == 56){
            p.printBoolGrid();
            System.out.println(p.isFull(9,1));
          }
        }

      }
      p.printBoolGrid();
      System.out.println("peroclates " + p.percolates());
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}