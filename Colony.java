
package gameOfLife;

//import
import java.awt.*;
import javax.imageio.*; // allows image loading
import java.io.*; // allows file access
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import java.util.Timer;
import java.util.Random;
import java.awt.Button;
import java.util.*;
import java.util.Random;
import java.lang.Math;

class Colony {
    
    //initialize instance variables
    private boolean grid[] [];
    private Scanner sc;

    public Colony (double density)//constructor called Colony
    {
        grid = new boolean [100] [100];//create 100 by 100 2d array
        for (int row = 0 ; row < grid.length ; row++)//declare as 0; loop so long as row is less than grid.length; add row by 1
            for (int col = 0 ; col < grid [0].length ; col++)//declare as 0; loop so long as col is less than grid[0].length; add col by 1
                grid [row] [col] = Math.random () < density;//grid[row][col] is equal to whether Math.random() is less than density in boolean
    }

    public void show (Graphics g)//method called show
    {
        //loops throughout the cells of the grid
        for (int row = 0 ; row < grid.length ; row++)
            for (int col = 0 ; col < grid [0].length ; col++)
            {
                if (grid [row] [col]) // life
                    g.setColor (Color.black);//set colour as black
                else
                    g.setColor (Color.white);//set colour as white
                g.fillRect (col * 5 + 2, row * 5 + 2, 5, 5); // draw life form
        }
    }
    
    public int getCol()//method called getCol()
    {
        return grid[0].length;//return grid[0].length
    }
    
    public int getRow()//method called getRow()
    {
        return grid.length;//return grid.length
    }

    public boolean live (int row, int col)//method called live
    {
        // count number of life forms surrounding to determine life/death
        int factors= 0;
       
        for( int rows= row - 1; rows <= row + 1; rows ++ )//declare as row - 1; loops so long as rows is less than or equal to row + 1; add rows by 1
        {
            for( int cols= col - 1; cols <= col + 1; cols ++ )//declare as col - 1; loops so long as cols is less than or equal to col + 1; add cols by 1
            {
                if( rows >= 0 && rows < grid.length - 1 && cols >= 0 && cols < grid[0].length - 1 )//determines whether rows is greater than or equal to 0 and less than grid.length - 1; and whether cols is greater than or equal to 0 and less than grid[0].length - 1
                {
                    if( rows != row || cols != col ){//determines whether rows is not equal to row, and cols is not equal to col
                    if( grid[rows][cols] )//determines whether grid[rows][cols] is equal to true
                        factors ++;//add factors by 1
                    }
                }
            }
        }

        if( factors == 2 && grid[row][col] )//determines whether there are two neighbours and the cell is alive
            return true;//return true
        else if( factors == 3 && grid[row][col])//determines whether there are three neighbours and the cell is alive
            return true;//return true
        else if( factors == 3 && grid[row][col] == false )//determines whether there are three neighbours and the cell is false
            return true;//return true
       
        return false;//return false
        
    }

    public void advance ()//method called advance
    {
        boolean nextGen[] [] = new boolean [grid.length] [grid [0].length]; // create next generation of life forms
        for (int row = 0 ; row < grid.length ; row++)
            for (int col = 0 ; col < grid [0].length ; col++)
                nextGen [row] [col] = live (row, col); // determine life/death status
        grid = nextGen; // update life forms
    }
    
    public void populate( int row, int col, int size )//method called populate
    {
        try//try
        {
        int sizes;
        
//        if( (grid.length - 1) - row < size )//determines whether grid.length-1 -row is less than size
//            sizes= (grid.length - 1) - row;//sizes is equal to grid.length - 1 - row
//        else if( (grid[0].length - 1) - col < size )//determines whether grid[0]/length - 1 - col is less than size
//            sizes= (grid[0].length - 1) - col;//sizes is equal to grid[0].length - 1 - col
//        else
//            sizes= size;//sizes is equal to size
        
        
            for( int rows= row - size; rows <= row + size; rows ++ )//rows is equal to row - size; loop so long as rows is less than row + size; add rows by 1
            {
                for( int cols= col - size; cols < col + size; cols ++ )//cols is equal to col - size; loop so long as cols is less than col + size; add cols by 1
                {
                    if( rows >= 0 && rows < grid.length - 1 && cols >= 0 && cols < grid[0].length - 1 )//determines whether rows is greater than or equal to 0 and less than grid.length - 1; and whether cols is greater than or equal to 0 and less than grid[0].length - 1
                        grid[rows][cols]= Math.random() < 0.75;//grid[row][col] is equal to whether Math.random() is less than 0.75 in boolean
                }
            }
        } catch(Exception e){}//catch
    }
    
    public void eradicate( int row, int col, int size )
    {
        try{
        int sizes;
        
        if( (grid.length - 1) - row < size )
            sizes= (grid.length - 1) - row;
        else if( (grid[0].length - 1) - col < size )
            sizes= (grid[0].length - 1) - col;
        else
            sizes= size;
        
            for( int rows= row- size; rows <= row + size; rows ++ )//rows is equal to row - size; loop so long as rows is less than row + size; add rows by 1
            {
                for( int cols= col - size; cols < col + size; cols ++ )//cols is equal to col - size; loop so long as cols is less than col + size; add cols by 1
                {
                    if( rows >= 0 && rows < grid.length - 1 && cols >= 0 && cols < grid[0].length - 1 )//determines whether rows is greater than or equal to 0 and less than grid.length - 1; and whether cols is greater than or equal to 0 and less than grid[0].length - 1
                        grid[rows][cols]= Math.random() < 0.1;//grid[row][col] is equal to whether Math.random() is less than 0.1 in boolean
                }
            }
        }
        catch(Exception e){}//catch
        
    }
    
    public void load(String fi) throws IOException//method called load that throws IOException
    {
        try//try
        {
        File file= new File(fi);//create new file
        Scanner s= new Scanner(file);//create it as scanner
        
        while( s.hasNextBoolean() )//while there are still boolean files to read
        {
            for( int row= 0; row < 100; row ++ )//declare as 0; loop so long as row is less than 100; add row by 1
            {
                for(int col= 0; col < 100; col ++ )//declare as 0; loop so long as col is less than 100; add col by 1
                    grid[row][col]= s.nextBoolean();//grid[row][col] is equal to s.nextBoolean
            }
        }
        }
        catch(IOException e){}//catch
    }
    
    public void save() throws IOException//method called save throws IOException
    {
        try//try
        {
            PrintWriter output= new PrintWriter("SaveFiles.txt");//create PrintWriter for "SaveFiles.txt"
            for( int row= 0; row < 100; row ++ )//declare as 0; loop so long as row is less than 100; add row by 1
            {
                for( int col= 0; col < 100; col ++ )//declare as 0; loop so long as col is less than 100; add col by 1
                    output.print( "" + grid[row][col] + " ");//write in file grid[row][col]
                output.println();//create a line
            }
            output.close();//close output
        }
        catch(IOException e)//catch
        {
        }
    }
    
}
