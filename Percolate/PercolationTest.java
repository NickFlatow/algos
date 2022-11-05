import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;


public class PercolationTest { 

    private Percolation p;

    @Before
    public void init(){
        p = new Percolation(3);
    }

    @Test
    public void isOpenBoundryTest() {
        p.boolGrid[0][0] = true;
        p.boolGrid[2][2] = true;
        p.boolGrid[1][1] = true;

        assertEquals(true, p.isOpen(1, 1));
        assertEquals(true, p.isOpen(3,3));
        assertEquals(true, p.isOpen(2, 2));
        assertEquals(false, p.isOpen(2,3));
    }
    @Test(expected = IllegalArgumentException.class)
    public void isOpenExceptionTest(){
        assertEquals("is Open -- Illegal  Argument", p.isOpen(0, 0));
        assertEquals("tset", p.isOpen(4, 4));
    }

    @Test
    public void openCellWithNeighboursClosed(){
        int testRow = 0;
        int testCol = 2;
        //does it actually open?
        assertEquals(false, p.boolGrid[testRow][testCol]);
        p.open(1,3);
        assertEquals(true, p.boolGrid[testRow][testCol]);
        
        //do we open any neighbours by accident?
        for (int i = 0; i < p.boolGrid.length;i++ ){
            for(int j = 0; j < p.boolGrid[i].length; j++){
                if (i == testRow && j == testCol){
                    continue;
                }else{
                    assertEquals(false,p.boolGrid[i][j]);
                }
            }
        }
        // assertEquals(false, (p.uf.find(p.intGrid[0][2]) ==  p.uf.find(p.intGrid[1][1])));

    }
    @Test
    public void openCellWithNeigbourOpen(){

        p.open(1,2);
        assertEquals(true, p.isOpen(1,2));
        p.open(2,3);
        assertEquals(true, p.isOpen(2, 3));
    

        //does it acutally open?
        assertEquals(false, p.isOpen(1, 3));
        p.open(1,3);
        assertEquals(true, p.isOpen(1, 3));

        //Did we make a union with the open neighbours?
        assertEquals(true, (p.uf.find(p.getIntGridCell(1, 3)) ==  p.uf.find(p.getIntGridCell(1, 2))));
        assertEquals(true, (p.uf.find(p.getIntGridCell(1, 3)) ==  p.uf.find(p.getIntGridCell(2, 3))));


        //and not where we shouldn't
        assertEquals(false, (p.uf.find(p.getIntGridCell(1, 3)) ==  p.uf.find(p.getIntGridCell(2, 2))));
    }
    @Test
    public void adjecentNeigbourUnionTest(){
        //open top, middle and bottom sites
        p.open(1,2);
        p.open(2,1);
        p.open(2,3);
        p.open(3,2);

        //open the middle site
        p.open(2,2);

        // p.printBoolGrid();
        //test all sites for union
        // assertEquals(false, (p.uf.find(p.getIntGridCell(1, 1)) ==  p.uf.find(p.getIntGridCell(2, 2))));
        // assertEquals(true, (p.uf.find(p.getIntGridCell(1, 2)) ==  p.uf.find(p.getIntGridCell(2, 2))));
        // assertEquals(false, (p.uf.find(p.getIntGridCell(1, 3)) ==  p.uf.find(p.getIntGridCell(2, 2))));
        // assertEquals(true, (p.uf.find(p.getIntGridCell(2, 1)) ==  p.uf.find(p.getIntGridCell(2, 2))));
        // assertEquals(true, (p.uf.find(p.getIntGridCell(2, 3)) ==  p.uf.find(p.getIntGridCell(2, 2))));
        // assertEquals(false, (p.uf.find(p.getIntGridCell(3, 1)) ==  p.uf.find(p.getIntGridCell(2, 2))));
        // assertEquals(true, (p.uf.find(p.getIntGridCell(3, 2)) ==  p.uf.find(p.getIntGridCell(2, 2))));
        // assertEquals(false, (p.uf.find(p.getIntGridCell(3, 3)) ==  p.uf.find(p.getIntGridCell(2, 2))));


        assertEquals(true, (p.uf.find(p.getIntGridCell(2, 1)) ==  p.uf.find(p.getIntGridCell(2, 3))));

    }
    @Test
    public void unionTest(){
        p.open(2,2);
        p.uf.union(p.getIntGridCell(2, 2), p.getIntGridCell(1, 1));
        p.uf.union(p.getIntGridCell(3, 2), p.getIntGridCell(2, 2));

    }

    @Test
    public void percolatesTest(){
        p.open(1,2);
        p.open(2,1);
        p.open(2,3);
        p.open(3,2);
        //open the middle site
        p.open(2,2);

        assertEquals(true, p.percolates());

    }

    @Test 
    public void doesNotPercolateTest(){

        p.open(1,2);
        p.open(2,1);
        p.open(3,2);

        assertEquals(false, p.percolates());

    }
    @Test 
    public void numberOfOpenSitesTest(){
        p.open(1,2);
        p.open(2,1);
        p.open(2,1);
        p.open(3,2);

        assertEquals(3, p.openSites);
    }

}


