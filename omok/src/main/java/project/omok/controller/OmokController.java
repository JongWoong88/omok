package project.omok.controller;

import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import project.omok.mapper.PlayerMapper;

@Controller
public class OmokController {
	
	@Autowired
	PlayerController pctr;
	
	@Autowired
	Game game;
	
	@Autowired
	PlayerMapper mapper;
	
	@RequestMapping("/")
	public String omok(HttpSession session) {
		session.setAttribute("p1", new PlayerDTO());
		session.setAttribute("p2", new PlayerDTO());
		return "main";
	}
    
    //board에서 param으로 전달받은 좌표로 point 객체 검색하여 리턴, 비어있으면 null리턴
    //param을 직접적으로 전달받을 메소드
	@ResponseBody
	@RequestMapping(value="/putStone",produces="application/json;charset=UTF-8")
	public Game putStone(@RequestBody Point point, HttpSession session) {
		int row = point.getRowNo();
		int col = point.getColNo();
		Game game = (Game)session.getAttribute("game");
		if (game.checkGame(row, col)) {
			game.setWinner((game.getTurnCnt()+1)%2);
			if ((game.getTurnCnt()+1)%2 == 0) {
				game.setBlack(pctr.victory(game.getBlack()));
				game.setWhite(pctr.defeat(game.getWhite()));
			} else {
				game.setWhite(pctr.victory(game.getWhite()));
				game.setBlack(pctr.defeat(game.getBlack()));
			}
			session.setAttribute("p" + game.getBlack().getLoginPlayer(), game.getBlack());
			session.setAttribute("p" + game.getWhite().getLoginPlayer(), game.getWhite());
		} 
		game.putStone(row, col);
		return game;
	}
	
	//게임 시작
	@ResponseBody
	@RequestMapping(value="/start",produces="application/json;charset=UTF-8")
	public Game start(HttpSession session) {
		PlayerDTO p1 = (PlayerDTO)session.getAttribute("p1");
		PlayerDTO p2 = (PlayerDTO)session.getAttribute("p2");
		detBlack(p1, p2);
		
		game.setBoard(new ArrayList());
		game.getBoard().clear();
		game.setTurnCnt(1);
		game.setWinner(-1);
		game.setMsg(null);
		System.out.println("게임 시작");
		session.setAttribute("game", game);
		return game;
	}
	
	//기권
	@ResponseBody
	@RequestMapping(value="/surrender",produces="application/json;charset=UTF-8")
	public Game surrender(@RequestBody PlayerDTO player, HttpSession session) {
		int pno = player.getLoginPlayer();
		game = (Game)session.getAttribute("game");
		if (game.getBlack().getLoginPlayer() == pno) {
			game.setWhite(pctr.victory(game.getWhite()));
			game.setBlack(pctr.defeat(game.getBlack()));
		} else {
			game.setBlack(pctr.victory(game.getBlack()));
			game.setWhite(pctr.defeat(game.getWhite()));
		}
		session.setAttribute("p" + game.getBlack().getLoginPlayer(), game.getBlack());
		session.setAttribute("p" + game.getWhite().getLoginPlayer(), game.getWhite());
		
		PlayerDTO loser = (PlayerDTO)session.getAttribute("p" + pno);
		game.setMsg(loser.getId() + "님이 기권하였습니다.");
		return game;
	}
	
	//흑, 백 판단
	public void detBlack(PlayerDTO p1, PlayerDTO p2) {
		Random rand = new Random();
		if(p1.getNewbie() == 0 && p2.getNewbie() == 0) {
			if(p1.getRecord() > p2.getRecord()) {
				game.setBlack(p2);
				game.setWhite(p1);
			} else if (p1.getRecord() < p2.getRecord()) {
				game.setWhite(p2);
				game.setBlack(p1);
			} else {
				if(rand.nextBoolean() == true) {
					game.setWhite(p2);
					game.setBlack(p1);
				} else {
					game.setBlack(p2);
					game.setWhite(p1);
				}
			}
		} else if (p1.getNewbie() != p2.getNewbie()) {
			if(p1.getRecord() > p2.getRecord()) {
				game.setBlack(p2);
				game.setWhite(p1);
			} else {
				game.setWhite(p2);
				game.setBlack(p1);
			}
		} else {
			if(p1.getWinRate() > p2.getWinRate()) {
				game.setBlack(p2);
				game.setWhite(p1);
			} else if (p1.getWinRate() < p2.getWinRate()){
				game.setWhite(p2);
				game.setBlack(p1);
			} else {
				if(rand.nextBoolean() == true) {
					game.setWhite(p2);
					game.setBlack(p1);
				} else {
					game.setBlack(p2);
					game.setWhite(p1);
				}
			}
		}
	}
}
