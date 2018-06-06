
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
        RecordMap records = new RecordMap();
        records.setTemplateData();
        records.genAllImages();
        StartUpWindow win = new StartUpWindow();
    }
   
    
    
}
