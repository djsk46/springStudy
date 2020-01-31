package com.board.icia.service;
//회원관리 서비스 클래스(model)

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private ModelAndView mav=new ModelAndView();
	@Autowired
	private HttpServletRequest request;
	
	public ModelAndView memberAccess(Member mb) {
		
		mav=new ModelAndView();
		String view=null;
		
		//스프링에선 복호화 메소드가 존재하지않음
		BCryptPasswordEncoder pwdEncoder=new BCryptPasswordEncoder();
		String pwdEncode=mDao.getSecurityPwd(mb.getM_id());
		System.out.println("pw="+pwdEncode);
		
		if(pwdEncode!=null) {
			if(pwdEncoder.matches(mb.getM_pwd(),pwdEncode)){ //암호된 비빌번호와 비교
				//로그인성공
				HttpSession session=request.getSession();
				session.setAttribute("id",mb.getM_id());
				mb=mDao.getMemberInfo(mb.getM_id());
				mav.addObject("mb", mb);
				view="boardList";
				//view="forward:/boardList";	//Forward: url
			}
			else {//비번 오류
				view="home";
				mav.addObject("check", 2);	//로그인 실패시
			}	
		}
		else {//아이디 오류
			view="home";
			mav.addObject("check", 2);	//로그인 실패시
		}
		mav.setViewName(view);
		
		return mav;
	}


	public ModelAndView memberjoin(Member mb) {
		mav=new ModelAndView();
		String view=null;
		
		//인코더 암호화  --디코더 복호화
		//스프링시큐리티는 암호화는 가능하지만 복호화는 불가능하다.
		BCryptPasswordEncoder pwdEncoder =new BCryptPasswordEncoder();
		//비번만 암호화해서 DB에 저장
		mb.setM_pwd(pwdEncoder.encode(mb.getM_pwd()));
		
		if(mDao.memberJoin(mb)) {
			System.out.println("true");
			view="home"; //회원가입 성공시
			mav.addObject("check",1);  //회원가입 성공
		}
		else {
			view="joinFrm"; //회원가입실패시	
		}
		mav.setViewName(view);
		return mav;
	}

	
	
	
	
}
