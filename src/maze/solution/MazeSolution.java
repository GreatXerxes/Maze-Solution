/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze.solution;

import gui.GuiFrontEnd;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author User
 */
public class MazeSolution {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Maze Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GuiFrontEnd(), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
    
}
