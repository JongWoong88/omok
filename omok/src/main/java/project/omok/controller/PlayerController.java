package project.omok.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import project.omok.mapper.PlayerMapper;

@Controller
@RequestMapping("/player")
public class PlayerController {
	
	@Autowired
	PlayerMapper mapper;

	// 1. 게임 참여(id로 조회 후 플레이어 정보 응답, 세션에 저장)
	@ResponseBody
	@RequestMapping(value="/join",produces="application/json;charset=UTF-8")
	public PlayerDTO join(@RequestBody PlayerDTO dto, HttpSession session) {
		PlayerDTO p1 = (PlayerDTO)session.getAttribute("p1");
		PlayerDTO p2 = (PlayerDTO)session.getAttribute("p2");
		PlayerDTO result = mapper.select(dto);
		if ( !dto.getId().equals(p1.getId()) && !dto.getId().equals(p2.getId())) {
			if (result != null) {
				if (dto.getLoginPlayer() == 1) {
					session.setAttribute("p1", result);
					if (!p2.getId().equals("")) result.setReady(true);
					result.setLoginPlayer(dto.getLoginPlayer());
				} else {
					session.setAttribute("p2", result);
					if (!p1.getId().equals("")) result.setReady(true);
					result.setLoginPlayer(dto.getLoginPlayer());
				}
			} else {
				result = new PlayerDTO();
				result.setMsg("아이디를 찾을 수 없습니다.");
			}
		} else {
			result.setMsg(result.getId() + "님은 이미 로그인 되어있습니다.");
		}
		System.out.println("p1: " + ((PlayerDTO)session.getAttribute("p1")).getId());
		System.out.println("p2: " + ((PlayerDTO)session.getAttribute("p2")).getId());
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/logout",produces="application/json;charset=UTF-8")
	// 2. 게임 퇴장(세션에서 삭제)
	public PlayerDTO logout(@RequestBody PlayerDTO player, HttpSession session) {
		int pno = player.getLoginPlayer();
		PlayerDTO result = (PlayerDTO)session.getAttribute("p" + pno);
		result.setMsg(result.getId() + "님 로그아웃");
		result.setReady(false);
		session.setAttribute("p" + pno, new PlayerDTO());
		
		System.out.println("p1: " + ((PlayerDTO)session.getAttribute("p1")).getId());
		System.out.println("p2: " + ((PlayerDTO)session.getAttribute("p2")).getId());
		return result;
	}

	// 3. 플레이어 추가(id로 db에 인서트)
	@ResponseBody
	@RequestMapping(value="/insertPlayer",produces="application/json;charset=UTF-8")
	public PlayerDTO insert(@RequestBody PlayerDTO dto) {
		try {
			if (dto.getId().equals("")) {
				dto.setMsg("");
			} else {
				int result = mapper.insert(dto);
				if (result == 1) dto.setMsg("등록 성공:)");
			}
		} catch (Exception e) {
			dto.setMsg("중복된 ID입니다");
		}
		return dto;
	}
	

	@ResponseBody
	@RequestMapping(value="/deletePlayer",produces="application/json;charset=UTF-8")
	// 4. 플레이어 삭제(id로 db에서 딜리트)
	public PlayerDTO delete(@RequestBody PlayerDTO dto, HttpSession session) {
		PlayerDTO p1 = (PlayerDTO)session.getAttribute("p1");
		PlayerDTO p2 = (PlayerDTO)session.getAttribute("p2");
		if (dto.getId().equals("")) {
			dto.setMsg("");
			return dto;
		} else if(!dto.getId().equals(p1.getId()) && !dto.getId().equals(p2.getId())) {
			if(mapper.delete(dto) == 0) {
				dto.setMsg("없는 ID입니다");
			}else {
				dto.setMsg("탈퇴 성공:(");
			}
			return dto;
		} else {
			dto.setMsg("로그인 중입니다.");
			return dto;
		}
	}
	
	// 5. 게임승리시(id로 조회 후 데이터 수정)
	public PlayerDTO victory(PlayerDTO dto) {
		PlayerDTO result = mapper.select(dto);
		result.setVictory(result.getVictory()+1);
		mapper.updateVic(result);
		result.setLoginPlayer(dto.getLoginPlayer());
		return result;
	}
	
	// 6. 게임패배시(id로 조회 후 데이터 수정)
	public PlayerDTO defeat(PlayerDTO dto) {
		PlayerDTO result = mapper.select(dto);
		result.setDefeat(result.getDefeat()+1);
		mapper.updateDef(result);
		result.setLoginPlayer(dto.getLoginPlayer());
		return result;
	}
}