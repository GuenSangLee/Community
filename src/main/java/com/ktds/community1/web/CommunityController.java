package com.ktds.community1.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community1.service.CommunityService;
import com.ktds.community1.vo.CommunityVO;
import com.ktds.member.constants.Member;
import com.ktds.member.vo.MemberVO;
import com.ktds.util.DownloadUtil;

@Controller
public class CommunityController {

	private CommunityService communityService;

	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}	

	// 제일 첫 화면이 list페이지가 될거다
	@RequestMapping("/")
	public ModelAndView viewListPage(HttpSession session) {
//		if (session.getAttribute(Member.USER) == null) {
//			return new ModelAndView("redirect:/login");
//		}
		ModelAndView view = new ModelAndView();
		// /WEB-INF/view/community/list.jsp 가 만들어진다!
		view.setViewName("community/list");
		List<CommunityVO> communityList = communityService.getall();
		view.addObject("communityList", communityList);
		return view;
	}

	// @RequestMapping(value = "/write", method = RequestMethod.GET)
	@GetMapping("/write")
	public String writePage() {
		return "community/write";
	}

	// @RequestMapping(value = "/write", method = RequestMethod.POST)
	@PostMapping("/write")
	// commend 객체=VO를 통해서 멤버변수를 자동적으로 가지고 온다.
	public ModelAndView doWrite(@ModelAttribute("writeForm") @Valid CommunityVO communityVO, Errors errors,
			HttpServletRequest request) {

		HttpSession session = request.getSession();

//		if (communityVO.getTitle() == null || communityVO.getTitle().length() == 0) {
//			session.setAttribute("status", "emptyTitle");
//			return new ModelAndView("redirect:/write");
//		}
		ModelAndView view = new ModelAndView();
		if (errors.hasErrors()) {
			view.setViewName("community/write");
			view.addObject("communityVO", communityVO);
			return view;
		}
		// 작성자의 IP를 얻어오는 코드
		String requestorIp = request.getRemoteAddr();
		communityVO.setRequestIp(requestorIp);
		communityVO.save();
//		if (communityVO.getBody() == null || communityVO.getBody().length() == 0) {
//			session.setAttribute("status", "emptyBody");
//			return new ModelAndView("redirect:/write");
//		}
//		if (communityVO.getWriteDate() == null || communityVO.getWriteDate().length() == 0) {
//			session.setAttribute("status", "emptyDate");
//			return new ModelAndView("redirect:/write");
//		}

		boolean isSuccess = communityService.createCoomunity(communityVO);
		if (isSuccess) {
			return new ModelAndView("redirect:/");
		}
		session.setAttribute("status", "fail");
		return new ModelAndView("redirect:/write");
	}

	@RequestMapping("/view/{id}")
	public ModelAndView viewViewPage(HttpSession session, @PathVariable int id) {

//		if (session.getAttribute(Member.USER) == null) {
//			return new ModelAndView("redirect:/login");
//		}

		ModelAndView view = new ModelAndView();
		view.setViewName("community/view");
		CommunityVO community = communityService.getOne(id);
		view.addObject("community", community);

		return view;
	}

	@RequestMapping("/recommend/{id}")
	public String increaseRecommendCount(@PathVariable int id) {
		if (communityService.upRecommendCount(id) == 1) {
			return "redirect:/view/" + id;
		}
		return "redirect:/";
	}

	@RequestMapping("/read/{id}")
	public String increaseViewCount(@PathVariable int id) {
		if (communityService.upViewCount(id) == 1) {
			return "redirect:/view/" + id;
		}
		return "redirect:/";
	}
	
	@RequestMapping("/get/{id}")
	public void download(@PathVariable int id,
			HttpServletRequest request, HttpServletResponse response) {
		
		CommunityVO community = communityService.getOne(id);
		String filename = community.getDisplayFilename();
		
		DownloadUtil download = 
				new DownloadUtil("d:/uploadFiles/" + filename);
		try {
			download.download(request, response, filename);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	
	
//	게시글id로 게시글 삭제.
	@RequestMapping("/delete/{id}")
	public String deleteCommunity(@PathVariable int id) {
		if(communityService.deleteCommunity(id) >1 ) {
			return "redirect:/";
		}
		return "redirect:/";
	}

	
//	parameter에서 게시글 번호를 가져와 db에서 작성자 id를 꺼내어 session안에 담겨있는 로그인 아이디와 비교.
//	true면 게시글 삭제, false면 노삭제
	@RequestMapping("/delCommunity/{id}")
	public String delCommunity(@PathVariable int id, HttpSession session) {
		int commuUserId= communityService.getOne(id).getMemberVO().getId();
		MemberVO memberVO=(MemberVO) session.getAttribute(Member.USER);
		
		if( commuUserId == memberVO.getId()) {
			communityService.deleteCommunity(id);
			return "redirect:/";
		}
		
		return "redirect:/";
	}
	
	
	@RequestMapping(value="/modify/{id}", method=RequestMethod.GET)
	public ModelAndView viewModifyPage(@PathVariable int id, HttpSession session) {
		MemberVO memeber= (MemberVO) session.getAttribute(Member.USER);
		CommunityVO community= communityService.getOne(id);
		
		//유저가 글쓴이인지 체크.
		int userId= memeber.getId();
		if( userId != community.getUserId() ) {
			return new ModelAndView("error/404");
		}
		
		
		ModelAndView view= new ModelAndView();
		view.setViewName("community/write");
		view.addObject("communityVO", community);
		view.addObject("mode", "modify");
		
		return view;
	}
	@RequestMapping(value="/modify/{id}", method=RequestMethod.POST)
	public String doModifyPage(@PathVariable int id, HttpSession session, HttpServletRequest request,  @ModelAttribute("writeForm") @Valid CommunityVO communityVO, Errors errors) {
		MemberVO member= (MemberVO) session.getAttribute(Member.USER);
		CommunityVO originalVO= communityService.getOne(id);
		
		if(member.getId() != originalVO.getUserId()) {
			return "error/404";
		}
		
		if( errors.hasErrors()) {
			return "redirect:/modify/"+id;
		}
		CommunityVO newCommunity= new CommunityVO();
		newCommunity.setId(originalVO.getId());
		newCommunity.setUserId(member.getId());
		
		boolean isModify= false;
		
//		1.아이피 변경 체크.
		String ip= request.getRemoteAddr();
		if (!ip.equals( originalVO.getRequestIp())) {
			newCommunity.setRequestIp(ip);
			isModify= true;
		}
		
//		2. 제목 변경확인
		if(!originalVO.getTitle().equals(communityVO.getTitle())) {
			newCommunity.setTitle(communityVO.getTitle());
			isModify= true;
		}
//		3.내용체크
		if(!originalVO.getBody().equals(communityVO.getBody())) {
			newCommunity.setTitle(communityVO.getBody());
			isModify= true;
		}
//		4.file체크
//		4-1
//		if(!originalVO.getDisplayFilename().equals(communityVO.getDisplayFilename())) {
//			newCommunity.setTitle(communityVO.getTitle());
//			isModify= true;
//		}
//		4-2
		if(communityVO.getDisplayFilename().length() > 0) {
			File file= new File("/d:/uploadFiles/"+communityVO.getDisplayFilename());
			file.delete();
			//파일이 없는걸 표현.
			communityVO.setDisplayFilename("");
		}else {
			//원래 데이터 유지
			communityVO.setDisplayFilename(originalVO.getDisplayFilename());
		}
		
		communityVO.save();
//		변경여부 체크.
		if(isModify) {
			communityService.updateCommunity(newCommunity);
			
			return "redirect:/view/"+ id;
		}
		
		return "";
		
	}
}
