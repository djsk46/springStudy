package com.board.icia.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.icia.dao.IBoardDao;
import com.board.icia.dto.Board;
import com.board.icia.dto.Reply;
import com.board.icia.userClass.DbException;
import com.board.icia.userClass.Paging;
import com.board.icia.userClass.UploadFile;

@Service
public class BoardMangement {

	@Autowired
	private IBoardDao bDao;
	@Autowired
	private UploadFile upload;

	ModelAndView mav;

	public ModelAndView getBoardList(Integer pageNum) {
		List<Board> bList = null;
		int pNum = (pageNum == null) ? 1 : pageNum;
		mav = new ModelAndView();
		String view = null;
		System.out.println("현재 페이지 : " + pNum);

		// 로그인시에만 게시판리스트를 보여줌
		// if (req.getSession().getAttribute("id") != null) {
		bList = bDao.getBoardList(pNum);
		if (bList != null) {
			System.out.println("size=" + bList.size());
			mav.addObject("bList", bList);
			mav.addObject("paging", getPaging(pNum));
			view = "boardList";// jsp
		} else {
			view = "home";
		}
		mav.setViewName(view);

		return mav;
	}

	private String getPaging(int pNum) {
		int maxNum = bDao.getBoardCount(); // 전체 글의 개수
		int listCount = 10; // 10 // 페이지당 나타낼 글의 갯수
		int pageCount = 2; // 2 // 페이지그룹당 페이지 갯수
		String boardName = "boardlist"; // 게시판의 종류(게시판이 여러개일때 url)
		Paging paging = new Paging(maxNum, pNum, listCount, pageCount, boardName);
		return paging.makeHtmlPaging();
	}

	public ModelAndView getContents(Integer bNum) {
		mav = new ModelAndView();
		String view = null;

		// if(req.getSession().getAttribute("id")!=null){
		Board board = bDao.getContents(bNum);
		mav.addObject("board", board);
		System.out.println("board=" + board);
		List<Reply> rList = bDao.getReplyList(bNum);
		mav.addObject("rList", rList);
		view = "boardContentsAjax"; // jsp
		// }
		// else {
		// view="home";
		// }
		mav.setViewName(view);
		return mav;
	}

//	public List<Reply> replyInsert(Reply r) {
//		String json=null;
//		if(bDao.replyInsert(r)) {
//			List<Reply> rList=bDao.getReplyList(r.getR_bnum());
//			//json=new Gson().toJson(rList);
//			//System.out.println("json="+json);
//		}
//		else {
//			//json=null;
//		}
//		return rList;
//	}

	public Map<String, List<Reply>> replyInsertJackson(Reply r) {
		Map<String, List<Reply>> rMap = null;
		if (bDao.replyInsert(r)) {
			List<Reply> rList = bDao.getReplyList(r.getR_bnum());
			rMap = new HashMap<String, List<Reply>>();
			rMap.put("rList", rList);
			System.out.println("rMap=" + rMap);
		} else {
			rMap = null;
		}
		return rMap;
	}

	// RedirectAttributes는 Redirect전 session영역에 저장한 뒤 redirect 후 즉시 삭제한다.
	// 삭제직전 session영역에 저장했던 데이터는 request객체에 저장한다.
	// addFlashAttribute :post방식(session에 저장후 1번 사용하면 삭제함)
	// attr.addAttribute : get방식 (session에 저장후 request객체에 저장 후 삭제함)
	@Transactional
	public ModelAndView boardDelete(Integer bNum, RedirectAttributes attr) throws DbException {
		System.out.println("bNum=" + bNum);
		mav = new ModelAndView();
		boolean r = bDao.replyDelete(bNum);
		boolean a = bDao.aticleDelete(bNum);

		if (a == false) { // 0개 원글을 삭제한 경우 예외발생시켜서 롤백
			throw new DbException();
		}
		if (r && a) {
			System.out.println("삭제 트랜잭션 성공");
			attr.addFlashAttribute("bNum", bNum); // post방식
			// attr.addAttribute("bNum",bNum); //get방식
		} else {
			System.out.println("삭제 트랜잭션 실패");
		}
		// mav.addObject("bNum",bNum); //get방식으로
		mav.setViewName("redirect:boardlist");
		return mav;
	}

	@Transactional
	public ModelAndView boardWrite(MultipartHttpServletRequest multi) {
		// 1개의 file Tag를 이용해서 여러파일을 첨부할 때
//		List<MultipartFile> file=multi.getFiles("files");
//		System.out.println("파일 개수="+file.size());
//		for(int i=0;i<file.size();i++) {
//			String f=file.get(0).getOriginalFilename();
//			System.out.println("file===="+f);
//		}
		mav = new ModelAndView();
		String view = null;
		String title = multi.getParameter("b_title");
		String contents = multi.getParameter("b_contents");
		int check = Integer.parseInt(multi.getParameter("fileCheck"));
		String id = (String) multi.getSession().getAttribute("id");
		System.out.println("check =" + check); // 첨부 : 1
		System.out.println("id=" + id);

		Board board = new Board();
		board.setB_title(title).setB_contents(contents).setB_id(id);
		boolean b = bDao.boardWrite(board);
		
		boolean f = false;
		
		if (b)	//글쓰기 성공
			view = "redirect:/boardlist";
		else
			view = "writeFrm"; // jsp
		
		if (check == 1) {	//첨부파일 여부
			//int bnum = bDao.getCurBoardNum(); // 현재 글 번호
			f = upload.fileUp(multi, board.getB_num());

			if (f) {
				view = "redirect:/boardlist"; // url
			} else {
				view = "writeFrm"; // jsp
			}
		}
		

		mav.setViewName(view);
		return mav;
	}

}
