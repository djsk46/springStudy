package com.board.icia;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.board.icia.dto.Board;
import com.board.icia.dto.Reply;
import com.board.icia.service.BoardMangement;
import com.board.icia.service.MemberManagement;
import com.google.gson.Gson;

//@Contoroller
@RestController
//@SessionAttributes("mb")  //세션에 mav 저장하기
@RequestMapping(value="/rest")
public class BoardRestController {
	//lombok
	//@Setter(onMethod_=@Autowired)
	@Autowired
	private BoardMangement bm;
	
	ModelAndView mav;
	
////	gson 활용하지 않아도 json형태로 반환한다.
//	@RequestMapping(value="/replyinsert",produces = "application/json;charset=utf8")
	//메시지 컨버터 : 서버에서 클라이언트 데이터를 변환해서 보내준다.
//											//@RequestBody 
//	public @ResponseBody String replyInsert(Reply r,HttpServletRequest req) {
//		System.out.println("r_bNum="+r.getR_bnum());
//		System.out.println("r_contents="+r.getR_contents());
//		r.setR_id(req.getSession().getAttribute("id").toString());
//		List<Reply> rList=bm.replyInsert(r);
//		String json=new Gson().toJson(r);
//		return json;
//		
//		//서블릿에서 사용하는 방식을	@ResponseBody가 대신 해줌
////		res.setContentType("text/html;charser=utf-8");
////		PrintWriter out=res.getWriter();
////		out.print(json);	
//	}
	
	
	//jackson 활용 : 가장 많이 쓰는 메세지 컨버터이다.
	@RequestMapping(value="/replyinsert",produces = "application/json;charset=utf8")
	//@RequestBody 
	public @ResponseBody Map<String,List<Reply>> replyInsert(Reply r,HttpServletRequest req) {
		r.setR_id(req.getSession().getAttribute("id").toString());
		Map<String,List<Reply>> rMap=bm.replyInsertJackson(r);
		return rMap;	//jackson 역할 : Map--->json으로 변환
		//jackson이 필요없이 json으로 변환이 되지만 JsonFomet때문에 사용
		
}
	
//	@PostMapping(value = "/boardwrite", produces = "application/json;charset=utf8")
//	public String boardWrite(Board board,List<MultipartFile> files) {
//		//파일태그명과 일치할것.
//		String json=new Gson().toJson(files);
//		System.out.println("title ="+board.getB_title());
//		//for(int i=0;i<files.size();i++)
//		System.out.println("files="+files.get(0).getOriginalFilename());
//		System.out.println("files="+files.get(0).getOriginalFilename());
//		
//		return json;
//		
//	}
	
	@PostMapping(value = "/boardwrite", produces = "application/json;charset=utf8")
	public String boardWrite(MultipartHttpServletRequest multi) {
		//파일태그명과 일치할것.
		System.out.println("title ="+multi.getParameter("b_title"));
		System.out.println("fileCheck="+multi.getParameter("fileCheck"));
		List<MultipartFile> files=multi.getFiles("files");
		for(int i=0;i<files.size();i++) {
			System.out.println("files="+files.get(i).getOriginalFilename());
		}
		String json=new Gson().toJson(files);
		
		return json;
		
	}
	
	
	
}


