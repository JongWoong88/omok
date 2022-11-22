package project.omok.controller;

import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

import lombok.Data;

//DB연동하여 플레이어 정보 저장하는 객체
@Data
@Component
public class PlayerDTO {
    private String id;
    private int victory;
    private int defeat;
    private boolean ready;
    
    private double winRate;
    private boolean newbie;
    private int record;
    
    private int loginPlayer;
    private String msg;
    
    public PlayerDTO() {
    	this.id = "";
    }
    
    public double getWinRate() {
    	if (this.victory == 0 && this.defeat == 0) {
    		return 0;
    	} else {
	    	DecimalFormat df = new DecimalFormat("0.00");
	    	return Double.parseDouble(df.format(((double)victory / (double)(victory + defeat))*100));
    	}
    }
    
    public int getNewbie() {
    	if (victory+defeat < 20) return 0;
    	else return 1;
    }
    
    public int getRecord() {
    	return victory+defeat;
    }
    
    
}
