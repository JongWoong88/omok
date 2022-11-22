package project.omok.controller;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class Point {

	private int idx;	//수순
	private int rowNo;	//행번호
	private int colNo;	//열번호
	private int stone;	//흑 1, 백 0
	
}
