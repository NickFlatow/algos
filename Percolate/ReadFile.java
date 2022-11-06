import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class ReadFile {
  private Percolation p;
  private boolean first = true;
  
  public static void main(String[] args) {
    ReadFile rf = new ReadFile();
    rf.readFile("./testfiles/input7.txt");
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
          // p.printBoolGrid();
          // System.out.println();
          if(p.numberOfOpenSites() == 12){
            System.out.println("is full: " + p.isFull(6,1));
          }
          // System.out.println("peroclates " + p.percolates());

        }

      }
      // p.printBoolGrid();
      // System.out.println("peroclates " + p.percolates());
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}