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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static java.lang.Math.toIntExact;

/**
 *
 * @author julliancockerell
 */
public class RecordMap implements Serializable {
    HashMap recordMap; //hashmap of all added records
    RLRecord orig; //first record from which all others are compared
    int maxInt; //the number of records added, most recent key added
    int gamesPlayed; //number of gamesplayed, updated on each record add
    double[] avgStats; //array holding the current average stats for ALL records
    
    //intializer
    public RecordMap()
    {
        recordMap = null;
        orig = null;
        avgStats = new double[]{0,0,0,0,0,0};
        maxInt = 0;
        gamesPlayed = 0;
    }
    
    //prints all values held in the avgStat array
    void printAvgs()
    {
        for(int i = 0; i < 6; i++)
        {
            System.out.print(avgStats[i] + " ");
        }
        System.out.println("");
    }
    
    //sets orginal RLRecord from which all other are calculated, like this stats can be compared to an original copy
    void setOrig()
    {
        if(orig == null)
        {
            orig = getRecord();
        }
        else
        {
            System.out.println("original record already set!");
        }
    }
    
    //sets some basic template data for testing purposes
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
        orig = new RLRecord(918, 1872, 341, 2119, 4042, 941, 706, 0, 0, 1164, 0, 0, 1169, 0, 0);
        recordMap = hashMap;
        maxInt = 8;
        setupAvgStats();
        gamesPlayed = 8;
    }
    
    //returns an RLRecord retrieved from
    RLRecord getRecord()
    {
       //steam id: 76561198068821663
       //game id: 1
       //API Key: MF3US9NVKJ7D53EN2AO6FHFJCO7TZ8BP
       String jsonOutput= "Didn't assign";
       try
        {
            URL url = new URL("https://api.rocketleaguestats.com/v1/player?unique_id=76561198068821663&platform_id=1");
            //URL url = new URL("https://api.rocketleaguestats.com/v1/player?unique_id=Pouchetta&platform_id=1");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "MF3US9NVKJ7D53EN2AO6FHFJCO7TZ8BP");
            con.connect();
            BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) 
            {
                content.append(inputLine);
            }
            in.close();
            jsonOutput = content.toString();
        }
       catch(MalformedURLException e)
        {
            System.out.println("Malformed Error");
        }
       catch(ProtocolException e)
       {
           System.out.println("Protocol Error");
       }
       catch(IOException e)
       {
           System.out.println("IOException");
       }
       //Prints response to GET request>
       //System.out.println(jsonOutput);
       JSONParser parser = new JSONParser();
      
        String uniqueId = " ";
        String displayName = " ";
        String platform = " ";
        int wins = 0;
        int goals = 0;
        int mvps = 0;
        int saves = 0;
        int shots = 0;
        int assists = 0;
        int sRankPoints = 0;
        int sTier = 0;
        int sDiv = 0;
        int dRankPoints = 0;
        int dTier = 0;
        int dDiv = 0;
        int tRankPoints = 0;
        int tTier = 0;
        int tDiv = 0;
       try
       {
            JSONObject playerInfo = (JSONObject) parser.parse(jsonOutput);
            uniqueId = (String) playerInfo.get("uniqueId");
            displayName = (String) playerInfo.get("displayName");
            JSONObject platformInfo = (JSONObject) playerInfo.get("platform");
            platform = (String) platformInfo.get("name");
            JSONObject stats = (JSONObject) playerInfo.get("stats");
            wins = toIntExact((long)stats.get("wins"));
            goals =  toIntExact((long) stats.get("goals"));
            mvps = toIntExact((long) stats.get("mvps"));
            saves = toIntExact((long) stats.get("saves"));
            shots = toIntExact((long) stats.get("shots"));
            assists = toIntExact((long) stats.get("assists"));
            JSONObject rankedStats = (JSONObject) playerInfo.get("rankedSeasons");
            JSONObject season7 = (JSONObject) rankedStats.get("7"); 
            JSONObject soloStats = (JSONObject) season7.get("10");
            JSONObject duoStats = (JSONObject) season7.get("11");
            JSONObject stanStats = (JSONObject) season7.get("13");
            sRankPoints = toIntExact((long)soloStats.get("rankPoints"));
            dRankPoints = toIntExact((long)duoStats.get("rankPoints"));
            tRankPoints = toIntExact((long)stanStats.get("rankPoints"));
            sTier =  toIntExact((long)soloStats.get("tier"));
            dTier =  toIntExact((long)duoStats.get("tier"));
            tTier =  toIntExact((long)stanStats.get("tier"));
            sDiv =  toIntExact((long)soloStats.get("division"));
            dDiv =  toIntExact((long)duoStats.get("division"));
            tDiv =  toIntExact((long)stanStats.get("division"));
       }
       catch(ParseException e)
       {
           System.out.println("Parse Error");
       }
       
       RLRecord p1 = new RLRecord(wins, goals, mvps, saves, shots, assists, sRankPoints, sTier, sDiv, dRankPoints, dTier, dDiv, tRankPoints, tTier, tDiv);
       return p1;
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
    
    //calculates avgStats array from scratch, cycling through all held records
    void setupAvgStats()
    {
        if(maxInt != 0)
        {
            RLRecord record;
            avgStats = new double[]{0,0,0,0,0,0};
            for(int i = 0; i < maxInt; i++)
            {
                record = (RLRecord) recordMap.get(maxInt);
                avgStats[0] = avgStats[0] + (record.getWins()/((double) maxInt));
                avgStats[1] = avgStats[1] + (record.getGoals()/((double) maxInt));
                avgStats[2] = avgStats[2] + (record.getMvps()/((double) maxInt));
                avgStats[3] = avgStats[3] + (record.getSaves()/((double) maxInt));
                avgStats[4] = avgStats[4] + (record.getShots()/((double) maxInt));
                avgStats[5] = avgStats[5] + (record.getAssists()/((double) maxInt));
            }
        }
        else
        {
            System.out.println("No records initialized...");
        }
    }
    
    //adds a specified record to recordmap, recalculates avgStats array, increments maxInt
    void addRecordToMap(RLRecord record)
    {
        maxInt++;
        recordMap.put(maxInt, record);
        addLastRecordToAvg();
    }
    
    //adds the last record in the RecordMap to avgStats array, should only be called by addRecordToMap
    void addLastRecordToAvg()
    {
        double multiplier = ((maxInt - 1)/((double) maxInt));
        RLRecord record = (RLRecord) recordMap.get(maxInt);
        avgStats[0] = (avgStats[0]*multiplier) + (record.getWins()/(double) maxInt);
        avgStats[1] = (avgStats[1]*multiplier) + (record.getGoals()/(double) maxInt);
        avgStats[2] = (avgStats[2]*multiplier) + (record.getMvps()/(double) maxInt);
        avgStats[3] = (avgStats[3]*multiplier) + (record.getSaves()/(double) maxInt);
        avgStats[4] = (avgStats[4]*multiplier) + (record.getShots()/(double) maxInt);
        avgStats[5] = (avgStats[5]*multiplier) + (record.getAssists()/(double) maxInt);
    }
    
    //Converts serialized map in "records.ser" into substantiated HashMap object
    void deserializeMap()
    {
        final String dir = System.getProperty("user.dir") + "/records.ser";
        File f = new File(dir);
        //System.out.println(dir);
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
                recordMap = map;
                maxInt = Collections.max(map.keySet());
                System.out.println("Successfully deserialized.");
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
            catch(ClassNotFoundException c)
            {
                System.out.println("Class not found");
                c.printStackTrace();
            }
        }
        else
        {
            System.out.println("File not found!");
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
    Graphics2D drawGraph(int array[], Graphics2D win, int height, int width, int mapSize, int maxVal, int minVal, String title)
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
    int convertCo(int yLevel, int height)
    {
        return height - (20 + yLevel);
    }
    
    void printLastRec()
    {
        if(recordMap != null)
        {
            RLRecord rec = (RLRecord) recordMap.get(maxInt);
            if(rec != null) {System.out.println(rec.getWins());}
        }
        else
        {
            System.out.println("Map is null!");
        }
    }
}
