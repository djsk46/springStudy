package com.board.icia;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.board.icia.dto.Reply;
import com.board.icia.service.BoardMangement;
import com.google.gson.Gson;

//@Contoroller
@RestController
@RequestMapping(value="/rest")
public class BoardRestController {
	//lombok
	//@Setter(onMethod_=@Autowired)
	@Autowired
	private BoardMangement bm;
	
	ModelAndView mav;
	//gson
//	@RequestMapping(value="/replyinsert",produces = "application/json;charset=utf8")
//											//@RequestBody 
//	public @ResponseBody String replyInsert(Reply r,HttpServletRequest req) {
//		System.out.println("r_bNum="+r.getR_bnum());
//		System.out.println("r_contents="+r.getR_contents());
//		r.setR_id(req.getSession().getAttribute("id").toString());
//		String json=bm.replyInsert(r);
//		//String json=new Gson().toJson(r);
//		return json;
//		
//		//서블릿에서 사용하는 방식을	@ResponseBody가 대신 해줌
////		res.setContentType("text/html;charser=utf-8");
////		PrintWriter out=res.getWriter();
////		out.print(json);
//		
//	}
	
	
	//jackson
	@RequestMapping(value="/replyinsert",produces = "application/json;charset=utf8")
	//@RequestBody 
	public @ResponseBody Map<String,List<Reply>> replyInsert(Reply r,HttpServletRequest req) {
		r.setR_id(req.getSession().getAttribute("id").toString());
		Map<String,List<Reply>> rMap=bm.replyInsertJackson(r);
		return rMap;	//jackson 역할 : Map--->json으로 변환
		
}
	
}


