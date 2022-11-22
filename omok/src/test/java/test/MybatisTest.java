package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;
import oracle.security.o3logon.a;
import project.omok.config.RootConfig;
import project.omok.controller.PlayerDTO;
import project.omok.mapper.PlayerMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
@Log4j
public class MybatisTest {
	
	@Autowired
	PlayerDTO dto;
	
	@Autowired
	PlayerMapper mapper;

	@Test
	public void test() { // select
		dto.setId("test");
		mapper.select(dto);
		dto.getVictory();
		dto.getDefeat();
		log.info(dto);
	}
	
	//@Test
	public void test2() { // insert
		dto.setId("미쉘");
		mapper.insert(dto);
	}
	
	//@Test
	public void test3() { // delete
		dto.setId("미쉘");
		mapper.delete(dto);
	}
	
	//@Test
	public void test4() { // updateVic
		dto.setId("김남길");
		PlayerDTO result = mapper.select(dto);
		result.setVictory(result.getVictory()+1);
		mapper.updateVic(result);
	}
	
	//@Test
	public void test5() { // updateDef
		dto.setId("김남길");
		PlayerDTO result = mapper.select(dto);
		result.setDefeat(result.getDefeat()+1);
		mapper.updateDef(result);
	}
	
	//@Test
	public void test6() { // 승률
		//System.out.println(dto.getWinRate("이두현"));
		dto.setId("이두현");
		PlayerDTO result = mapper.select(dto);
		double odds = (result.getVictory()+result.getDefeat());
	 	odds = (double)result.getVictory()*100/odds;
		System.out.println("승률:"+(String.format("%.2f",odds)+"%"));
	}
	
	//@Test 
	public void test7() { // 뉴비판독기
		dto.setId("김남길");
		PlayerDTO result = mapper.select(dto);
		System.out.println(result.getNewbie());
		
	}
	
	//@Test
	public void test8() { // 선공결정1. 20판 미만 판수
		dto.setId("이대희");
		PlayerDTO p1 = mapper.select(dto);
		dto.setId("이두현");
		PlayerDTO p2 = mapper.select(dto);
		if(p1.getNewbie() < 1 && p2.getNewbie() < 1) {
		} else if (p1.getRecord() > p2.getRecord()) { 
			System.out.println(p2.getId()+"선공");
		} else {
			System.out.println(p1.getId()+"선공");
		}
	}
	  
	//@Test
	public void test9() { // 플레이어전적
		dto.setId("이대희");
		PlayerDTO p1 = mapper.select(dto);
		dto.setId("이두현");
		PlayerDTO p2 = mapper.select(dto);
		System.out.println("이름:"+p1.getId()
			+"\n승리: "+p1.getVictory()+"\t패배: "+p1.getDefeat()
			+"\n승률: "+(String.format("%.2f",p1.getWinRate())+"%"));
		System.out.println("이름:"+p2.getId()
			+"\n승리: "+p2.getVictory()+"\t패배: "+p2.getDefeat()
			+"\n승률: "+(String.format("%.2f",p2.getWinRate())+"%"));
	}
	
//	@Test
	public void test10() { // 선공결정2. 
		dto.setId("이대희");
		PlayerDTO p1 = mapper.select(dto);
		dto.setId("이두현	");
		PlayerDTO p2 = mapper.select(dto);
		if(p1.getNewbie() == 0 && p2.getNewbie() == 0 ) {
			if(p1.getWinRate() > p2.getWinRate()) {
				System.out.println(p2.getId());
			} else {
				System.out.println(p1.getId());
			}
		} 
	}
}









