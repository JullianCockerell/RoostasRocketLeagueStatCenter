/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author julliancockerell
 */
public class RecordMap {
    HashMap recordMap;
    int maxInt;
    
    public RecordMap()
    {
        recordMap = null;
        maxInt = 0;
    }
    
    void setTemplateData()
    {
        HashMap<Integer, RLRecord> hashMap = new HashMap<Integer, RLRecord>();
        RLRecord one = new RLRecord(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        RLRecord two = new RLRecord(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1);
        RLRecord three = new RLRecord(2,2,2,2,2,2,2,2,2,2,2,2,2,2,2);
        RLRecord four = new RLRecord(3,3,3,3,3,3,3,3,3,3,3,3,3,3,3);
        RLRecord five = new RLRecord(4,4,4,4,4,4,4,4,4,4,4,4,4,4,4);
        RLRecord six = new RLRecord(5,5,5,5,5,5,5,5,5,5,5,5,5,5,5);
        RLRecord seven = new RLRecord(6,6,6,6,6,6,6,6,6,6,6,6,6,6,6);
        RLRecord eight = new RLRecord(7,7,7,7,7,7,7,7,7,7,7,7,7,7,7);
        hashMap.put(1, one);
        hashMap.put(2, two);
        hashMap.put(3, three);
        hashMap.put(4, four);
        hashMap.put(5, five);
        hashMap.put(6, six);
        hashMap.put(7, seven);
        hashMap.put(8, eight);
        recordMap = hashMap;
        maxInt = 8;
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
    
    //generates blank graphs for program to placehold
    void genHoldImages()
    {
        int zeros[] = new int[]{0};
        genGraphImage(zeros, "hshots", "Shots");
        genGraphImage(zeros, "hassists", "Assists");
        genGraphImage(zeros, "hgoals", "Goals");
        genGraphImage(zeros, "hsaves", "Saves");
        genGraphImage(zeros, "hsrankpoints", "Solo Rank Points");
        genGraphImage(zeros, "hdrankpoints", "Duos Rank Points");
        genGraphImage(zeros, "htrankpoints", "Standard Rank Points");
        genGraphImage(zeros, "hwins", "Wins");
    }
    
    //write all graphs
    void genAllImages()
    {
        int mapIndex = maxInt;
        RLRecord[] lastFive = new RLRecord[5];
        for(int i = 0; i < 5; i++)
        {
            if(mapIndex > 0)
            {
                lastFive[4-i] = (RLRecord) recordMap.get(mapIndex);
            }
            else
            {
                lastFive[4-i] = new RLRecord(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
            }
            mapIndex--;
        }
        
        int shots[] = new int[5];
        int assists[] = new int[5];
        int goals[] = new int[5];
        int saves[] = new int[5];
        int sRankPoints[] = new int[5];
        int dRankPoints[] = new int[5];
        int tRankPoints[] = new int[5];
        int wins[] = new int[5];
        for(int i = 0; i < 5; i++)
        {
            RLRecord rec = lastFive[i];
            shots[i] = rec.getShots();
            assists[i] = rec.getAssists();
            goals[i] = rec.getGoals();
            saves[i] = rec.getSaves();
            sRankPoints[i] = rec.getSRP();
            dRankPoints[i] = rec.getDRP();
            tRankPoints[i] = rec.getTRP();
            wins[i] = rec.getWins();
        }
        genGraphImage(shots, "shots", "Shots");
        genGraphImage(assists, "assists", "Assists");
        genGraphImage(goals, "goals", "Goals");
        genGraphImage(saves, "saves", "Saves");
        genGraphImage(sRankPoints, "srankpoints", "Solo Rank Points");
        genGraphImage(dRankPoints, "drankpoints", "Duos Rank Points");
        genGraphImage(tRankPoints, "trankpoints", "Standard Rank Points");
        genGraphImage(wins, "wins", "Wins");
    }
    
    //saves an image composed of data from size 5-10 array
    void genGraphImage(int[] records, String name, String title)
    {
        try 
        {
            int width = 300, height = 300;      
            //read shots into array
            int mapSize = records.length;
            int valMax = Integer.MIN_VALUE;
            int valMin = Integer.MAX_VALUE;
            int array[] = new int[mapSize];
            for(int i = 0; i < mapSize; i++)
            {
                array[i] = records[i];
                if(array[i] > valMax){valMax = array[i];}
                if(array[i] < valMin){valMin = array[i];}
            }
      
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D shotGraph = bi.createGraphics();
            shotGraph = drawGraph(array, shotGraph, height, width, mapSize, valMax, valMin, title);
            String finName = name + ".PNG";
            ImageIO.write(bi, "PNG", new File(finName));
        } 
        catch (IOException ie) 
        {
            ie.printStackTrace();
        }
        
    }
    
    
    //takes array of 5 values and draws appropiate graph
    static Graphics2D drawGraph(int array[], Graphics2D win, int height, int width, int mapSize, int maxVal, int minVal, String title)
    {
        int gap = (int)((width - 40.0) / (mapSize + 1));
        win.setPaint(Color.WHITE);
        win.fillRect(0, 0, width, height);
        Font titleFont = new Font("Helvetica", Font.BOLD, 20);
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
            double maxValDoub = (double) maxVal;
            double minValDoub = (double) minVal;
            int yLevel = (int) (((array[i] - minValDoub)/(maxValDoub - minValDoub))*(height - 70));
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
