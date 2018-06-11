
package rrlsc;

public class RRLSC {

    
    public static void main(String[] args) 
    {
        //testSerialize();
        testDeserialize();
    }
   
    static public void testSerialize()
    {
        RecordMap records = new RecordMap();
        records.setTemplateData();
        records.serializeMap(records.recordMap);
    }
    
    static public void testDeserialize()
    {
        RecordMap records = new RecordMap();
        records.deserializeMap();
        records.printLastRec();
    }
    
    static public void testStartWin()
    {
        RecordMap records = new RecordMap();
        records.setTemplateData();
        records.serializeMap(records.recordMap);
        records.genAllImages();
        StartUpWindow win = new StartUpWindow();
    }
    
}
