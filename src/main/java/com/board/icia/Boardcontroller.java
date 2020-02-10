package com.board.icia;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.icia.service.BoardMangement;




@Controller
public class Boardcontroller {
	@Autowired
	private BoardMangement bm;	//게시판 서비스 클래스
	
	ModelAndView mav;
	
	@RequestMapping(value = "/boardlist", method = RequestMethod.GET)	//method는 생략하면 get post 모두 가능
	public ModelAndView boardlist(Integer pageNum) { //null
		mav=bm.getBoardList(pageNum);//인터셉터에서 로그인여부 확인했기때문에 request 생략
		return mav;
	}
	
	@RequestMapping(value = "/contents", method = RequestMethod.GET)
	public ModelAndView contents(Integer bNum) { 
		mav=bm.getContents(bNum);
		return mav;
	}
	
	
	@RequestMapping(value = "/boarddelete", method = RequestMethod.GET)
	public ModelAndView boardDelete(Integer bNum,RedirectAttributes attr) { 
		mav=bm.boardDelete(bNum,attr);
		return mav;
	}

	
	//글쓰기 페이지로 이동
	@RequestMapping(value = "/writefrm", method = RequestMethod.GET)
	public ModelAndView writeFrm() { 
		mav=new ModelAndView();
		mav.setViewName("writeFrm");
		return mav;
	}
	
	@PostMapping(value="/boardwrite")
		public ModelAndView writerFrm(MultipartHttpServletRequest multi) {
		
		mav=new ModelAndView();
		mav=bm.boardWrite(multi);
		
		return mav;
	}
}
