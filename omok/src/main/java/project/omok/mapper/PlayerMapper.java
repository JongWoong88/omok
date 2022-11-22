package project.omok.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import project.omok.controller.PlayerDTO;

@Mapper
public interface PlayerMapper {
	
	PlayerDTO select(PlayerDTO dto);
	int insert(PlayerDTO dto);
	int delete(PlayerDTO dto);
	int updateVic(PlayerDTO dto);
	int updateDef(PlayerDTO dto);
	double winRate(PlayerDTO dto);
	Map list(PlayerDTO dto);
	List waiting(PlayerDTO dto);
}
