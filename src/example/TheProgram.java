package example;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

public class TheProgram
{
   public static void main(String[] args)
   {
      
      // create a window frame
      JFrame mainScreen = new JFrame ("Main Screen");
      mainScreen.setBounds(300, 300, 800, 600);   //establish initial size in pixels
      mainScreen.getContentPane().setBackground(Color.WHITE); // set background color
      //...
      // other window properties can be set here
      //
      mainScreen.setResizable(true); // the window can be resized
      mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // this happens with window is closed
      mainScreen.setVisible(true);
      
      // create components (widgets) to add to the window.  this can be panels, buttons, images, etc.
      // in this case a new panel     
      CustomPanel mainPanel = new CustomPanel(); // create a panel to add to window
      mainPanel.setBackground(Color.WHITE);// set the background color of the panel to white
      
      // add the panel to the frame
      Container windowContainer = mainScreen.getContentPane();// get the window's container
      windowContainer.add(mainPanel); // add the panel to the window
      
      // make the window visible
      mainScreen.setVisible(true);
      // *****
   }

}
