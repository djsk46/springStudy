package com.board.icia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.board.icia.dto.Board;
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

}
