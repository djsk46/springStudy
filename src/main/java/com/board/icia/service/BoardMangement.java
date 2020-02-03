package com.board.icia.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.board.icia.dao.IBoardDao;
import com.board.icia.dto.Board;

@Service
public class BoardMangement {

	@Autowired
	private IBoardDao bDao;
	ModelAndView mav;

	public ModelAndView getBoardList(Integer pageNum, HttpServletRequest req) {
		List<Board> bList = null;
		int pNum = (pageNum == null) ? 1 : pageNum;
		mav = new ModelAndView();
		String view = null;
		System.out.println("현재 페이지 : " + pNum);

		// 로그인시에만 게시판리스트를 보여줌
		//if (req.getSession().getAttribute("id") != null) {
			bList = bDao.getBoardList(pNum);
			if (bList != null) {
				System.out.println("size=" + bList.size());
				mav.addObject("bList", bList);
				view = "boardList";// jsp
			}
		 else {
			view = "home";
		}
		mav.setViewName(view);

		return mav;
	}

	public ModelAndView getContents(Integer bNum, HttpServletRequest req) {
		mav=new ModelAndView();
		String view=null;
		
		//if(req.getSession().getAttribute("id")!=null){
			view="boardList";
		//}
		//else {
			//view="home";
		//}
		mav.setViewName(view);
		return mav;
	}


}
