/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze.solution.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ciall McArdle
 */
public class Maze 
{
    private Coordinate size;
    private Coordinate startPoint;
    private Coordinate endPoint;
    private List<List<Character>> maze = new ArrayList<List<Character>>();
    private File f;
    
    public Maze(File file)
    {
        f = file;
        try 
        {
            createList();
        } 
        catch(FileNotFoundException ex)
        {
            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //=============================================================================================================
    /**
     * Taking the three first lines and storing them in an object is a little messy
     * TODO - sorting the first three lines
     * @param file
     * @throws IOException 
     */
    //==============================================================================================================
    public void createList() throws FileNotFoundException
    {
        // read maze from a file and create a list
        Scanner input = new Scanner(f);
        
        String[] split;//temp variable to store split strings
        
        String sizeLine = input.nextLine();
        split = sizeLine.split(" ");
        size = new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]));

        String startLine = input.nextLine();
        split = startLine.split(" ");
        startPoint = new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]));

        String endLine = input.nextLine();
        split = endLine.split(" ");
        endPoint = new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]));

        while(input.hasNextLine())
        {
            Scanner temReader = new Scanner(input.nextLine());
            ArrayList tem = new ArrayList();
            while(temReader.hasNextInt())
            {
                int i = temReader.nextInt();
                char ch = Character.forDigit(i, 10);
                tem.add(ch);
            }
            maze.add(tem);
        }
   }
    
    //==============================================================================================================
    //Print out the maze
    //==============================================================================================================
    public void printMaze()
    {
        System.out.println();

        for(int row = 0; row < maze.size(); row++)
        {
            for(int col = 0; col < maze.get(row).size(); col++)
            {
                if(maze.get(row).get(col) != null)
                {
                    switch(maze.get(row).get(col)) 
                    {
                        case '1':
                            System.out.print("#");
                            break;
                        case '0':
                            System.out.print(" ");
                            break;
                        default:
                            System.out.print(maze.get(row).get(col));
                            break;
                    }
                }
         }
         System.out.println();
      }
    }
    
    public String printMazeString()
    {
        String s = "<html>";
        for(int row = 0; row < maze.size(); row++)
        {
            for(int col = 0; col < maze.get(row).size(); col++)
            {
                if(maze.get(row).get(col) != null)
                {
                    switch(maze.get(row).get(col)) 
                    {
                        case '1':
                            s = s + "#";
                            break;
                        case '0':
                             s = s + " ";
                            break;
                        default:
                            s = s + maze.get(row).get(col);
                            break;
                    }
                }
            }
            s = s + "<br>";
        }
        s = s + "</html>";
        return s;
    }
    
    public String outputMazeString()
    {
        String s = "";
        maze.get(startPoint.getX()).set(startPoint.getY(), 'S');
        maze.get(endPoint.getY()).set(endPoint.getX(), 'E');//Again werid bug where it is back to front
        
        for(int row = 0; row < maze.size(); row++)
        {
            for(int col = 0; col < maze.get(row).size(); col++)
            {
                
                if(maze.get(row).get(col) != null)
                {
                    switch(maze.get(row).get(col)) 
                    {
                        case '1':
                            s = s + "#";
                            break;
                        case '0':
                             s = s + " ";
                            break;
                        case '~':
                            s = s + " ";
                            break;
                        default:
                            s = s + maze.get(row).get(col);
                            break;
                    }
                }
                if(col < maze.get(row).size() - 1)
                {
                    s = s + " ";
                }
            }
            s = s + "\r\n";
        }
        return s;
    }
    
    //==============================================================================================================
    /**
     * Tries to solve the maze recursively.
     * Uses ~ to show what the path it is taking/took
     * Finishes with an X for final path
     * @param row
     * @param column
     * @return true or false on if the maze can be solved
     */
    //==============================================================================================================
    public boolean solveMaze(int row, int col)
    {
        boolean done = false;

        if(canMoveHere(row, col)) 
        {
           maze.get(row).set(col, '~');  // cell has been tried

           if(row == endPoint.getY() && col == endPoint.getX())//This is opposite for some odd reason
              done = true;  // maze is solved
           else
           {
              done = solveMaze(row+1, col);  // move down
              if(!done)
                 done = solveMaze(row, col+1);  // move right
              if(!done)
                 done = solveMaze(row-1, col);  // move up
              if(!done)
                 done = solveMaze(row, col-1);  // move left
           }
           if(done)//if it has found the route to the goal
           {
              maze.get(row).set(col, 'X');
           }

        }
        return done;
    }
		   		   
		
		
    //===========================================================
    /**
     * Checks to see if the tile it is on valid for movement 
     * @param row
     * @param column
     * @return returns true if the tile is moveable
     */
    //===========================================================
    private boolean canMoveHere(int row, int column)
    {

       boolean valid = false;
       // check if tile is with the maze
       if(row >= 0 && row < maze.size() && column >= 0 && column < maze.get(0).size())
       {
          //check if tile is not a wall and not yet tried
          if(maze.get(row).get(column) == '0')
          {
             valid = true;
          }
       }
       return valid;

    }
    
    //===========================================================
    /**
     * Write the output into a file
     */
    //===========================================================
    public void writeToFile()
    {
        String[] fileName = f.getName().split("\\.");//Split by "."
        
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName[0] + "_solution.txt"));
            out.write(outputMazeString());   
            out.close();
        }
        catch (IOException e)
        {
            System.out.println("Exception ");

        }
    }
    
    
    public Coordinate getStartPoint()
    {
        return this.startPoint;
    }
}
