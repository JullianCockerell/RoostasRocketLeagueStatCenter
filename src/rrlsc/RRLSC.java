
package rrlsc;

public class RRLSC {

    
    public static void main(String[] args) 
    {
        //testSerialize();
        //testDeserialize();
        //testStartWin();
        //testRecordAvg();
        testGetRecord();
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
    
    static public void testRecordAvg()
    {
        RecordMap record1 = new RecordMap();
        RecordMap record2 = new RecordMap();
        
        record2.setTemplateData();
        record2.recordMap.put(record2.maxInt + 1, new RLRecord(7,7,7,7,7,7,7,7,7,7,7,7,7,7,7));
        record2.maxInt++;
        record2.setupAvgStats();
        
        record1.setTemplateData();
        record1.setupAvgStats();
        record1.addRecordToMap(new RLRecord(7,7,7,7,7,7,7,7,7,7,7,7,7,7,7));
        
        record1.printAvgs();
        record2.printAvgs();
    }
    
    static public void testGetRecord()
    {
        RecordMap rec = new RecordMap();
        RLRecord testRecord = rec.getRecord();
        System.out.println(testRecord.toString());
    }
    
}
