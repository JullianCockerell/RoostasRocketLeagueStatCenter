
package rrlsc;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;


public class RRLSC {

    
    public static void main(String[] args) 
    {
        
    }
    
    
    //Converts HashMap object into serialized object in file "records.ser"
    boolean serializeMap(HashMap map)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream("records.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            oos.close();
            fos.close();
            System.out.printf("Serialized HashMap data is saved in records.ser");
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            return false;
        }
        return true;
    }
    
    
    //Converts serialized map in "records.ser" into substantiated HashMap object
    HashMap deserializeMap()
    {
        final String dir = System.getProperty("user.dir") + "/records.ser";
        File f = new File(dir);
        System.out.println(dir);
        HashMap<Integer, RLRecord> map = null;
        if(f.exists() && !f.isDirectory()) 
        { 
            try
            {
                FileInputStream fis = new FileInputStream("records.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                map = (HashMap) ois.readObject();
                ois.close();
                fis.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
                return null;
            }
            catch(ClassNotFoundException c)
            {
                System.out.println("Class not found");
                c.printStackTrace();
                return null;
            }
            return map;
        }
        else
        {
            System.out.println("File not found!");
            return null;
        }
    }
    
    
    //Checks if record.ser exists in directory and is a valid, readable serialized HashMap
    boolean checkForFile()
    {
        final String dir = System.getProperty("user.dir") + "/records.ser";
        File f = new File(dir);
        System.out.println(dir);
        if(f.exists() && !f.isDirectory()) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    
    //saves an image composed of data from size 5-10 array
    static void genGraphImage(int[] records, String name, String title)
    {
        try 
        {
            int width = 300, height = 300;      
            //read shots into array
            int mapSize = records.length;
            int valMax = 0;
            int array[] = new int[mapSize];
            for(int i = 0; i < mapSize; i++)
            {
                array[i] = records[i];
                if(array[i] > valMax){valMax = array[i];}
            }
      
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D shotGraph = bi.createGraphics();
            shotGraph = drawGraph(array, shotGraph, height, width, mapSize, valMax, title);
            String finName = name + ".PNG";
            ImageIO.write(bi, "PNG", new File(finName));
        } 
        catch (IOException ie) 
        {
            ie.printStackTrace();
        }
        
    }
    
    
    //takes array of 5 values and draws appropiate graph
    static Graphics2D drawGraph(int array[], Graphics2D win, int height, int width, int mapSize, int maxVal, String title)
    {
        int gap = (int)((width - 40.0) / (mapSize + 1));
        win.setPaint(Color.WHITE);
        win.fillRect(0, 0, width, height);
        Font titleFont = new Font("TimesRoman", Font.BOLD, 20);
        win.setFont(titleFont);
        String message = title;
        FontMetrics fontMetrics = win.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(message);
        int stringHeight = fontMetrics.getAscent();
        win.setPaint(Color.BLACK);
        win.drawRect(0, 0, width - 1, height - 1);
        win.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4 - 130);
        win.drawRect(20, 30, width - 40, height - 50);
        int oldX = 0, oldY = 0;
        int xLevel = 20 + gap;
        Font numFont = new Font("TimesRoman", Font.PLAIN, 10);
        win.setFont(numFont);
      
        
        //converts shot count and draws dots and lines
        for(int i = 0; i < mapSize; i++)
        {
            double shotMaxDouble = (double) maxVal;
            int yLevel = (int) ((array[i]/shotMaxDouble)*(height - 70));
            yLevel = convertCo(yLevel, height);
            win.setPaint(Color.DARK_GRAY);
            win.drawLine(20, yLevel, width - 20, yLevel);
            String num = "" + array[i];
            win.drawString(num, 10, yLevel);
            win.setPaint(Color.BLACK);
            win.fillOval(xLevel - 5, yLevel - 5, 10, 10);
            if(i > 0)
            {
                win.drawLine(oldX, oldY, xLevel, yLevel);
            }
            if(i == mapSize - 1)
            {
                win.drawLine(20, height - 20, width - 20, height - 20);
            }
            oldX = xLevel;
            oldY = yLevel;
            xLevel = xLevel + gap;
        }
        return win;
    }
    
    
    //converts coordinates for point placement
    static int convertCo(int yLevel, int height)
    {
        return height - (20 + yLevel);
    }
    
    
    
}
