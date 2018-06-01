/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rrlsc;

public class RLRecord {
    private int wins;
    private int goals;
    private int mvps;
    private int saves;
    private int shots;
    private int assists;
    private int sRankPoints;
    private int sTier;
    private int sDiv;
    private int dRankPoints;
    private int dTier;
    private int dDiv;
    private int tRankPoints;
    private int tTier;
    private int tDiv;
    
    public RLRecord(int wins, int goals, int mvps, int saves, int shots, int assists, int sRankPoints, int sTier, int sDiv, int dRankPoints, int dTier, int dDiv, int tRankPoints, int tTier, int tDiv)
    {
        this.wins = wins;
        this.goals = goals;
        this.mvps = mvps;
        this.saves = saves;
        this.shots = shots;
        this.assists = assists;
        this.sRankPoints = sRankPoints;
        this.sTier = sTier;
        this.sDiv = sDiv;
        this.dRankPoints = dRankPoints;
        this.dTier = dTier;
        this.dDiv = dDiv;
        this.tRankPoints = tRankPoints;
        this.tTier = tTier;
        this.tDiv = tDiv;
    }

    public int getWins(){return wins;}
    public int getGoals(){return goals;}
    public int getMvps(){return mvps;}
    public int getSaves(){return saves;}
    public int getShots(){return shots;}
    public int getAssists(){return assists;}
    public int getSRP(){return sRankPoints;}
    public int getST(){return sTier;}
    public int getSD(){return sDiv;}
    public int getDRP(){return dRankPoints;}
    public int getDT(){return dTier;}
    public int getDD(){return dDiv;}
    public int getTRP(){return tRankPoints;}
    public int getTT(){return tTier;}
    public int getTD(){return tDiv;}
    
    
    public String toString()
    {
        return  "Stats:\n     wins: " + wins + 
                "\n     goals: " + goals +
                "\n     mvps: " + mvps +
                "\n     saves: " + saves +
                "\n     shots: " + shots +
                "\n     assists: " + assists +
                "\nSolos:\n     Rank Points: " + sRankPoints + 
                "\n     Tier: " + sTier +
                "\n     Division: " + sDiv +
                "\nDuos:\n     Rank Points:" + dRankPoints +
                "\n     Tier: " + dTier +
                "\n     Division: " + dDiv +
                "\nStandard:\n     Rank Points:" + tRankPoints +
                "\n     Tier: " + tTier +
                "\n     Division: " + tDiv; 
    }
    
}
