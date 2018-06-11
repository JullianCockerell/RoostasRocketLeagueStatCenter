/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rrlsc;

public class RLRecord {
    final private int wins;
    final private int goals;
    final private int mvps;
    final private int saves;
    final private int shots;
    final private int assists;
    final private int sRankPoints;
    final private int sTier;
    final private int sDiv;
    final private int dRankPoints;
    final private int dTier;
    final private int dDiv;
    final private int tRankPoints;
    final private int tTier;
    final private int tDiv;
    
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
