import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PuzzleTests {


    @Test
    public void HammingTest() {
        int n = 3;
        int[] rand = new int[] {8,1,3,4,0,2,7,6,5};
        int k = 0;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles [i][j] = rand[k];
                k++;
            }
        }

        // solve the slider puzzle
        Board initial = new Board(tiles);
        assertEquals(5,initial.hamming());

    }


    @Test
    public void ManhattanTest() {
        int n = 3;
        int[] rand = new int[] {8,1,3,4,0,2,7,6,5};
        int k = 0;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles [i][j] = rand[k];
                k++;
            }
        }

        // solve the slider puzzle
        Board initial = new Board(tiles);

        int[] tileInGoalBoardCoords8 = initial.findTileInGoalBoard(8);
        int [] goalExpected8 = {2,1};
        Assert.assertArrayEquals(goalExpected8, tileInGoalBoardCoords8);
        assertEquals(3,initial.distanceFromHome(tileInGoalBoardCoords8,0,0));
        
        
        int[] tileInGoalBoardCoords1 = initial.findTileInGoalBoard(1);
        int [] goalExpected1 = {0,0};
        Assert.assertArrayEquals(goalExpected1, tileInGoalBoardCoords1);
        assertEquals(1,initial.distanceFromHome(tileInGoalBoardCoords1,0,1));
        
        int[] tileInGoalBoardCoords6 = initial.findTileInGoalBoard(6);
        int [] goalExpected6 = {1,2};
        Assert.assertArrayEquals(goalExpected6, tileInGoalBoardCoords6);
        assertEquals(2,initial.distanceFromHome(tileInGoalBoardCoords6,2,1));
        assertEquals(10,initial.manhattan());

    }
    @Test
    public void GoalTestFail() {
        int n = 3;
        int[] rand = new int[] {8,1,3,4,0,2,7,6,5};
        int k = 0;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles [i][j] = rand[k];
                k++;
            }
        }

        Board initial = new Board(tiles);
        assertEquals(false, initial.isGoal());
    }
    @Test
    public void GoalTestPass() {
        int n = 3;
        int[] rand = new int[] {1,2,3,4,5,6,7,8,0};
        int k = 0;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles [i][j] = rand[k];
                k++;
            }
        }

        Board initial = new Board(tiles);
        assertEquals(true, initial.isGoal());
    }

    @Test
    public void equalsTrue() {
        int n = 3;
        int[] rand = new int[] {8,1,3,4,0,2,7,6,5};
        int k = 0;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles [i][j] = rand[k];
                k++;
            }
        }


        // int h = 3;
        int[] randy = new int[] {8,1,3,4,0,2,7,6,5};
        int l = 0;
        int[][] timlins = new int[n][n];
        for (int a = 0; a < n; a++) {
            for (int b = 0; b < n; b++) {
                timlins [a][b] = randy[l];
                l++;
            }
        }

        Board initial = new Board(tiles);
        Board second = new Board(timlins);
        assertEquals(true, initial.equals(second));
    }

    @Test
    public void equalsFalse() {
        int n = 3;
        int[] rand = new int[] {8,1,3,4,0,2,7,6,5};
        int k = 0;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles [i][j] = rand[k];
                k++;
            }
        }


        // int h = 3;
        int[] randy = new int[] {1,2,3,4,5,6,7,8,0};
        int l = 0;
        int[][] timlins = new int[n][n];
        for (int a = 0; a < n; a++) {
            for (int b = 0; b < n; b++) {
                timlins [a][b] = randy[l];
                l++;
            }
        }

        Board initial = new Board(tiles);
        Board second = new Board(timlins);
        assertEquals(false, initial.equals(second));
    }
}
