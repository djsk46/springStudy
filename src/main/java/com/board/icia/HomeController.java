package com.board.icia;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.board.icia.dao.IMemberDao;
import com.board.icia.dto.Member;
import com.board.icia.service.MemberManagement;
import com.google.gson.Gson;

@Controller
public class HomeController {	
	@Autowired
	private MemberManagement mm;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	ModelAndView mav=new ModelAndView();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		logger.info("로그인 화면으로 이동");
		mav=new ModelAndView();
		mav.setViewName("home");
		return mav;
	}
	//로그인
	@RequestMapping(value = "/access", method = RequestMethod.POST)
	public ModelAndView access(Member mb, HttpServletRequest req) {
	
		System.out.println("id="+mb.getM_id());
		System.out.println("pw="+mb.getM_pwd());
		mav=mm.memberAccess(mb,req);
		return mav;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ModelAndView logout(HttpServletRequest req) {
		req.getSession().invalidate(); //세션초기화
		mav=new ModelAndView();
		mav.setViewName("redirect:home");
		return mav;
	}
	
	@RequestMapping(value = "/myinfo", method = RequestMethod.GET, produces = "application/json;charset=utf8")
	public @ResponseBody String myinfo(HttpServletRequest req) {
		Member mb=mm.getMyInfo(req);

		String json=new Gson().toJson(mb);
		
		return json;
	}
	
	
	//회원가입페이지
	@RequestMapping(value = "/joinfrm", method = RequestMethod.GET)
	public ModelAndView joinFrm() {
		
		mav=new ModelAndView();
		mav.setViewName("joinFrm");
		
		
		return mav;
	}

	
	
	//회원가입
	@RequestMapping(value = "/memberjoin", method = RequestMethod.POST)
	public ModelAndView memberjoin(Member mb) {
		
		mav=mm.memberjoin(mb);
		
		
		return mav;
	}
	
	@GetMapping(value="/member/{dept}/{emp}")
	public String pathVariavle(@PathVariable int dept, @PathVariable String emp) {
		System.out.println("dept="+dept);
		System.out.println("emp="+emp);
		return "home";
	}
	
	
}//controller End
