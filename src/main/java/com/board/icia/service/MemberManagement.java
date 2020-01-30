package com.board.icia.service;
//회원관리 서비스 클래스(model)

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.board.icia.dao.IMemberDao;
import com.board.icia.dto.Member;
//@Component
@Service
public class MemberManagement {
	@Autowired
	private IMemberDao mDao;

	public ModelAndView access() {

		Member mb=new Member("cha","1111");
		System.out.println("결과:"+mDao.getLoginResult(mb));
		System.out.println(mb.toString());
		ModelAndView mav=new ModelAndView();
		mav.addObject("result",mDao.getLoginResult(mb));
		mav.setViewName("home");
		return mav;
		
	}
	
	
	
}
