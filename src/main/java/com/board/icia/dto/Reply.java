package com.board.icia.dto;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

@Alias("reply")
@Accessors(chain=true)
@Data
public class Reply {
	private int r_bnum;	//원글 번호
	private int r_num;	//댓글 번호
	private String  r_contents;	//댓글
	//메세지 컨버터 : 서버에서 클라이언트 데이터를 변환해서 보내준다.
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm")
	//property 에디터 : 클라이언트에서 서버로 데이터를 넘길때
	//@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm")
	//jackSon과 tTimestamp가 호환되지 않아서
	private Timestamp r_date;	//작성일  db에서 to_char를 사용하면 깨지지 않는다.
	private String r_id;	//작성자

}
