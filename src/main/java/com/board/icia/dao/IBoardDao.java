package com.board.icia.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.board.icia.dto.Bfile;
import com.board.icia.dto.Board;
import com.board.icia.dto.Member;
import com.board.icia.dto.Reply;

public interface IBoardDao {
	List<Board> getBoardList(int pNum);

	Board getContents(Integer bNum);

	List<Reply> getReplyList(Integer bNum);
	
	@Select("SELECT COUNT(*) FROM BLIST_1")
	int getBoardCount();

	boolean replyInsert(Reply r);
	
	@Delete("DELETE FROM R WHERE R_BNUM=#{bNum}")
	boolean replyDelete(Integer bNum);
	@Delete("DELETE FROM B WHERE B_NUM=#{bNum}")
	boolean aticleDelete(Integer bNum);
	//@Insert("INSERT INTO B VALUES(BOARD_SEQ.NEXTVAL,'#{board.getB_title()}','#{board.getB_contents()}','#{board.getB_id()}',DEFAULT,DEFAULT)")
	boolean boardWrite(Board board);

	boolean fileInsert(Map<String, String> fMap);

	List<Bfile> getBfList(Integer bNum);

	@Delete("delete from bf where bf_bnum=#{bNum}")
	boolean fileDelete(Integer bNum);

	//List<Member> myBatisTest(String cName, Integer search);
	List<Member> myBatisTest(@Param("cName") String cName, @Param("point")Integer search);

	//@Select("SELECT BOARD_SEQ.CURRVAL FROM DUAL")
	//int getCurBoardNum();

}
