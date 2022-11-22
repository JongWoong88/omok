package project.omok.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

/*
 * 게임에 대한 정보 저장하는 객체
 * param받아서 지속적인 수정이 가해짐
 * 게임에 필요한 정보는 수순, 선공, 후공
 */
@Getter
@Setter
@Service
public class Game {
	private ArrayList<Point> board;
	private PlayerDTO black;
	private PlayerDTO white;
	private int turnCnt;
	private int winner; // -1=게임중, 0=흑 승, 1=백 승
	private String msg;
	
	public void putStone(int row, int col) {
		Point point = new Point();
		point.setColNo(col);
		point.setRowNo(row);
		if (getPoint(row, col) == null) {
			point.setStone(turnCnt % 2);
			point.setIdx(turnCnt);
			board.add(point);
			turnCnt++;
		}
	}
	
	//착수시 board에 point객체 저장, 저장시 좌표값, 흑백, 수순 정보 저장
	public Point getPoint(int row, int col) {
		for (int i=0; i < board.size(); i++) {
			if (board.get(i).getRowNo() == row && board.get(i).getColNo() == col) {
				return board.get(i);
			}
		}
		return null;
	}
	
    //board에서 게임정보 조회하여 승패 판단, main으로 param넘겨줄 매소드
    public boolean checkGame(int row, int col) {
    	return (checkGame1(row, col) || checkGame2(row, col) || checkGame3(row, col) || checkGame4(row, col));
    }
    
//    public int checkThree(Point point) {
//    	return checkThree1(point)+checkThree2(point)+checkThree3(point)+checkThree4(point);
//    }

    public boolean checkGame1(int row, int col) {
    	//현재턴 플레이어 돌, 이 돌을 중심으로 사방으로 5목인지 확인
    	int rowTemp = row;
    	int colTemp = col;
    	int cnt = 1;
    	//연속적으로 5개가 놓여있는지
    	while (rowTemp < 18) {
    		rowTemp++;
    		if (checkSame(row,col,rowTemp,colTemp) == 1) cnt++;
    		else break;
    	}
    	rowTemp = row;
    	while (rowTemp > 0) {
    		rowTemp--;
    		if (checkSame(row,col,rowTemp,colTemp) == 1) cnt++;
    		else break;
    	}
    	//cnt 체크
    	if (cnt == 5) return true;
    	else return false;
    }
    
    public boolean checkGame2(int row, int col) {
    	//현재턴 플레이어 돌, 이 돌을 중심으로 사방으로 5목인지 확인
    	int rowTemp = row;
    	int colTemp = col;
    	int cnt = 1;
    	//연속적으로 5개가 놓여있는지
    	while (colTemp < 18) {
    		colTemp++;
    		if (checkSame(row,col,rowTemp,colTemp) == 1) cnt++;
    		else break;
    	}
    	colTemp = col;
    	while (colTemp > 0) {
    		colTemp--;
    		if (checkSame(row,col,rowTemp,colTemp) == 1) cnt++;
    		else break;
    	}
    	//cnt 체크
    	if (cnt == 5) return true;
    	else return false;
    }
    
    public boolean checkGame3(int row, int col) {
    	//현재턴 플레이어 돌, 이 돌을 중심으로 사방으로 5목인지 확인
    	int rowTemp = row;
    	int colTemp = col;
    	int cnt = 1;
    	//연속적으로 5개가 놓여있는지
    	while (rowTemp < 18 && colTemp < 18) {
    		rowTemp++;
    		colTemp++;
    		if (checkSame(row,col,rowTemp,colTemp) == 1) cnt++;
    		else break;
    	}
    	rowTemp = row;
    	colTemp = col;
    	while (rowTemp > 0 && colTemp > 0) {
    		rowTemp--;
    		colTemp--;
    		if (checkSame(row,col,rowTemp,colTemp) == 1) cnt++;
    		else break;
    	}
    	//cnt 체크
    	if (cnt == 5) return true;
    	else return false;
    }
    
    public boolean checkGame4(int row, int col) {
    	//현재턴 플레이어 돌, 이 돌을 중심으로 사방으로 5목인지 확인
    	int rowTemp = row;
    	int colTemp = col;
    	int cnt = 1;
    	//연속적으로 5개가 놓여있는지
    	while (rowTemp < 18 && colTemp > 0) {
    		rowTemp++;
    		colTemp--;
    		if (checkSame(row,col,rowTemp,colTemp) == 1) cnt++;
    		else break;
    	}
    	rowTemp = row;
    	colTemp = col;
    	while (rowTemp > 0 && colTemp < 18) {
    		rowTemp--;
    		colTemp++;
    		if (checkSame(row,col,rowTemp,colTemp) == 1) cnt++;
    		else break;
    	}
    	//cnt 체크
    	if (cnt == 5) return true;
    	else return false;
    }
    
//  public int checkThree1(Point point) {
//    	int rowTemp = point.getRowNo();
//    	int colTemp = point.getColNo();
//    	int cnt = 1;
//    	int blank = 0;
//    	
//    	while (colTemp < 18) {
//    		colTemp++;
//    		if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == 1) cnt++;
//    		else if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == -1) {
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp-1) == 1) return 0;//열린3아님
//    			else break;
//    		} else {
//    			blank++;
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp-1) == 0) {
//    				blank = 0;
//    				break;
//    			} else if (blank == 2) break;
//    		}
//    	}
//    	colTemp = point.getColNo();
//    	while (colTemp > 0) {
//    		colTemp--;
//    		if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == 1) cnt++;
//    		else if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == -1) {
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp+1) == 1) return 0;//열린3아님
//    			else break;
//    		} else {
//    			blank++;
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp+1) == 0) {
//    				blank = 0;
//    				break;
//    			} else if (blank == 2) break;
//    		}
//    	}
//    	System.out.println("1:"+cnt);
//    	if (cnt == 3) return 1;
//    	else return 0;//열린3아님
//    }
//    
//    public int checkThree2(Point point) {
//    	int rowTemp = point.getRowNo();
//    	int colTemp = point.getColNo();
//    	int cnt = 1;
//    	int blank = 0;
//    	int i = 1;
//    	
//    	while (rowTemp < 18) {
//    		System.out.println("i1-"+i+" : "+cnt);
//    		i++;
//    		rowTemp++;
//    		if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == 1) cnt++;
//    		else if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == -1) {
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp-1,colTemp) == 1) return 0;//열린3아님
//    			else break;
//    		} else {
//    			blank++;
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp-1,colTemp) == 0) {
//    				blank = 0;
//    				break;
//    			} else if (blank == 2) break;
//    		}
//    	}
//    	rowTemp = point.getColNo();
//    	while (rowTemp > 0) {
//    		System.out.println("i2-"+i+" : "+cnt);
//    		i++;
//    		rowTemp--;
//    		if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == 1) cnt++;
//    		else if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == -1) {
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp+1,colTemp) == 1) return 0;//열린3아님
//    			else break;
//    		} else {
//    			blank++;
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp+1,colTemp) == 0) {
//    				blank = 0;
//    				break;
//    			} else if (blank == 2) break;
//    		}
//    	}
//    	System.out.println("2:"+cnt);
//    	if (cnt == 3) return 1;
//    	else return 0;//열린3아님
//    }
//    
//    public int checkThree3(Point point) {
//    	int rowTemp = point.getRowNo();
//    	int colTemp = point.getColNo();
//    	int cnt = 1;
//    	int blank = 0;
//    	
//    	while (colTemp < 18 && rowTemp < 18) {
//    		colTemp++;
//    		rowTemp++;
//    		if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == 1) cnt++;
//    		else if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == -1) {
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp-1,colTemp-1) == 1) return 0;//열린3아님
//    			else break;
//    		} else {
//    			blank++;
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp-1,colTemp-1) == 0) {
//    				blank = 0;
//    				break;
//    			} else if (blank == 2) break;
//    		}
//    	}
//    	rowTemp = point.getRowNo();
//    	colTemp = point.getColNo();
//    	while (colTemp > 0 && rowTemp > 0) {
//    		colTemp--;
//    		rowTemp--;
//    		if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == 1) cnt++;
//    		else if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == -1) {
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp+1,colTemp+1) == 1) return 0;//열린3아님
//    			else break;
//    		} else {
//    			blank++;
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp+1,colTemp+1) == 0) {
//    				blank = 0;
//    				break;
//    			} else if (blank == 2) break;
//    		}
//    	}
//    	System.out.println("3:"+cnt);
//    	if (cnt == 3) return 1;
//    	else return 0;//열린3아님
//    }
//    
//    public int checkThree4(Point point) {
//    	int rowTemp = point.getRowNo();
//    	int colTemp = point.getColNo();
//    	int cnt = 1;
//    	int blank = 0;
//    	
//    	while (colTemp > 0 && rowTemp < 18) {
//    		colTemp--;
//    		rowTemp++;
//    		if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == 1) cnt++;
//    		else if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == -1) {
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp-1,colTemp+1) == 1) return 0;//열린3아님
//    			else break;
//    		} else {
//    			blank++;
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp-1,colTemp+1) == 0) {
//    				blank = 0;
//    				break;
//    			} else if (blank == 2) break;
//    		}
//    	}
//    	colTemp = point.getColNo();
//    	rowTemp = point.getRowNo();
//    	while (colTemp < 18 && rowTemp > 0) {
//    		colTemp++;
//    		rowTemp--;
//    		if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == 1) cnt++;
//    		else if (checkSame(point.getRowNo(),point.getColNo(),rowTemp,colTemp) == -1) {
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp+1,colTemp-1) == 1) return 0;//열린3아님
//    			else break;
//    		} else {
//    			blank++;
//    			if (checkSame(point.getRowNo(),point.getColNo(),rowTemp+1,colTemp-11) == 0) {
//    				blank = 0;
//    				break;
//    			} else if (blank == 2) break;
//    		}
//    	}
//    	System.out.println("4:"+cnt);
//    	if (cnt == 3) return 1;
//    	else return 0;//열린3아님
//    }
    
    //빈칸 0, 다르면 -1, 같으면 1
    public int checkSame(int row, int col, int rowTemp, int colTemp) {
    	if (getPoint(rowTemp, colTemp) == null) return 0;
    	else if (getPoint(rowTemp, colTemp).getStone() == turnCnt % 2) return 1;
    	else return -1;
    }
    
}