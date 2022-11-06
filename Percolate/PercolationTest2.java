import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class PercolationTest2 {
    private Percolation p;
    private boolean first;

    @Before
    public void init(){
        this.first = true;
    }


    @Test
    public void TestInputFile6(){
        String file = "./testfiles/input6.txt";

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
                this.p.open(row,col);
                if(row == 4 && col == 4){
                   assertEquals(false,p.isFull(row,col));
                }
              }
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
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
              this.p.open(Integer.parseInt(splited[0]),Integer.parseInt(splited[1]));
            }
          }
          myReader.close();
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
      }
    
}







