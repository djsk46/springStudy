package com.board.icia.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("member")
@Data
public class Member {
	private String id;
	private String pw;	
}
