
package rrlsc;

public class RRLSC {

    
    public static void main(String[] args) 
    {
        RecordMap records = new RecordMap();
        records.setTemplateData();
        records.genAllImages();
        StartUpWindow win = new StartUpWindow();
    }
   
    
}
