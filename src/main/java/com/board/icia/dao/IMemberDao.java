package com.board.icia.dao;



import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.board.icia.dto.Member;
public interface IMemberDao {
	
	public boolean getLoginResult(Member mb);
	
	public boolean memberJoin(Member mb);
	
	public String getSecurityPwd(String m_id);
	
	//@Select("SELECT * FROM MINFO WHERE M_ID=#{m_id}")
	public Member getMemberInfo(String m_id);

	public boolean hashMapTest(Map<String, String> hMap);

	public Map<String, String> hashMapTest2(String id);

	@Select("SELECT * FROM M WHERE M_ID=#{id}")
	public Member getMyInfo(String id);
	
} //interface End
