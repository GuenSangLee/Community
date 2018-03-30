package com.ktds.member.web;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.actionhistory.vo.ActionHistory;
import com.ktds.actionhistory.vo.ActionHistory.ReqType;
import com.ktds.actionhistory.vo.ActionHistoryVO;
import com.ktds.community1.service.CommunityService;
import com.ktds.member.constants.Member;
import com.ktds.member.service.MemberService;
import com.ktds.member.vo.MemberVO;

@Controller
public class MemberController {
	
	private MemberService memberService;
	private CommunityService communityService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewLoginPage(HttpSession session) {
		// 로그인을 했다는 상태정보를 가져와서 했다면 리스트를 보여주고 아니면 로그인페이지로 돌아간다.
		if (session.getAttribute(Member.USER) != null) {
			return "redirect:/";
		}
		return "member/login";
	}
	
	@RequestMapping(value = "/regist")
	public String viewRegistPage() {
		return "member/regist";
	}
	
	@RequestMapping("/remove/{id}")
	public String removeMemeber(@PathVariable int id, HttpSession session) {
		if (memberService.removeMember(id))
			session.invalidate();
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public String doRegistAction ( @ModelAttribute("registForm")
										@Valid MemberVO memberVO, Errors errors
										,HttpServletRequest request
										,Model model) {
		
		
		if ( errors.hasErrors() ) {
			return "member/regist";
		}
		ActionHistoryVO history = (ActionHistoryVO) request.getAttribute("actionHistory");
		//static으로 지정한 ReqType중 알맞는 타입을 가져온다.
		history.setReqType(ActionHistory.ReqType.MEMBER);
		
		//handler에서 미리 지정하여 쓸모 없어짐.
//		history.setIp(request.getRemoteAddr());
		
		if ( memberService.createMember(memberVO) ) {
			//회원 가입 성공시, 로그 남기기
//				log형식을 미리 만들어둔 형식으로 format.
			String log = String.format(ActionHistory.Log.REGIST, memberVO.getEmail(), memberVO.getNickname(), "true");
			history.setLog(log);
			
			model.addAttribute("actionHistory", history);
			return "redirect:/login";
			
		}
//		회원 가입 실패시, 로그남기기
		String log = String.format(ActionHistory.Log.REGIST, memberVO.getEmail(), memberVO.getNickname(), "false");
		history.setLog(log);
		
		model.addAttribute("actionHistory", history);
		return "member/regist";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLoginAction(@ModelAttribute("loginForm") @Valid MemberVO memberVO, Errors errors,
			HttpServletRequest request, @RequestAttribute ActionHistoryVO actionHistory) {
		HttpSession session = request.getSession();
		
		actionHistory.setReqType(ActionHistory.ReqType.MEMBER);
		String log = String.format(ActionHistory.Log.LOGIN, memberVO.getEmail());
		actionHistory.setLog(log);
		
		MemberVO loginMember = memberService.readMember(memberVO);
		
		if ( loginMember != null ) {
			session.setAttribute(Member.USER, loginMember);
			return "redirect:/";
			
		}
		return "redirect:/login";
	}
	
	@RequestMapping("/logout")
	public String doLogoutAction(HttpSession session, Model model, HttpServletRequest request
			,@RequestAttribute ActionHistoryVO actionHistory) {
		ActionHistoryVO history= (ActionHistoryVO) request.getAttribute("actionHistory");
		/* 처음 사용한 방법.
		history.setReqType(ActionHistory.ReqType.MEMBER);
		MemberVO member= (MemberVO) session.getAttribute(Member.USER);
//		String log= String.format(ActionHistory.Log.LOGOUT, member.getEmail());
		String log= String.format(ActionHistory.Log.LOGOUT, ((MemberVO) session.getAttribute(Member.USER)).getEmail());;
		history.setLog(log);
		model.addAttribute("actionHistory", history);
		
		session.invalidate();
		*/
		
		//@RequestAttribute 사용 (spring 4.0 이상)
		actionHistory.setReqType(ReqType.MEMBER);
		String log= String.format(ActionHistory.Log.LOGOUT, ((MemberVO) session.getAttribute(Member.USER)).getEmail());;		
		actionHistory.setLog(log);
		
		session.invalidate();
		
		
		return "redirect:/login";
	}
	
//	탈퇴시 비번 리체크.
	@RequestMapping("/delete/process1")
	public String viewVerifyPage() {
	
		return "member/delete/process1";	
	}
	
//	정상적인 접속방법이 아니라, URL로 강제 접근할 시, 따로 처리
//	required- 넘어오는 값이 없다면,
	@RequestMapping("/delete/process2")
	public ModelAndView viewDeleteMyCommunityPage(@RequestParam(required = false, defaultValue = "") String password, HttpSession session) {
		ModelAndView view= new ModelAndView();
		
//		password의 값이 널(빈공간)이면 error(404) 
		if (password.length() == 0) {
			return new ModelAndView("error/404");
		}
		MemberVO member = (MemberVO)session.getAttribute(Member.USER);
		member.setPassword(password);
		
		MemberVO verifyMember= memberService.readMember(member);
		if(verifyMember == null) {
			return new ModelAndView("redirect:/delete/process1");
		}
		
		//내가 작성한 게시글 개수
		int myCommunitiesCount= communityService.readMyCommunitiesCount(verifyMember.getId());
		view.setViewName("member/delete/process2");
		view.addObject("myCommunitiesCount",myCommunitiesCount);

		//		UUID.randomUUID() nanoSecond 단위 난수.
		String uuid= UUID.randomUUID().toString();
		session.setAttribute("__TOKEN__", uuid);
		view.addObject("token", uuid);
		
		return view;
		
	}
	
	@RequestMapping("/account/delete/{deleteFlag}")
	public String doDeleteAction(HttpSession session, @PathVariable String deleteFlag,
			 @RequestParam(required=false, defaultValue="") String token) {
		MemberVO member= (MemberVO) session.getAttribute(Member.USER);
		
		String sessionToken= (String) session.getAttribute("__TOKEN__");
		if(sessionToken == null || !sessionToken.equals(token)) {
			return "error/404";
		}
		if(member == null) {
			return "redirect:/login";
		}
		
		int id= member.getId();
		if( memberService.deleteMember(id, deleteFlag)) {
			session.invalidate();
		}
		return "member/delete/delete";
	}
	
	//ResponseBody는 넘기려는 데이터를 JSON형태로 넘긴다. (라이브러리 추가할 것(pom.xml))
	  //JSON의 특징. 모든 언어에서 읽을 수 있다.
	@RequestMapping("/api/exists/email")
	@ResponseBody
	public Map<String, Boolean> apiIsExistsEmail(@RequestParam String email){
		boolean isExists= memberService.readCountMemberEmail(email);
		
		Map<String, Boolean> response= new HashMap<String, Boolean>();
		response.put("response", isExists);
		
		return response;
	}
	
	@RequestMapping("/api/exists/nickname")
	@ResponseBody
	public Map<String, Boolean> apiIsExistsNickname(@RequestParam String nickname){
		boolean isExists= memberService.readIsMemberNickname(nickname);
		System.out.println(isExists);
		Map<String, Boolean> response= new HashMap<String, Boolean>();
		System.out.println(response);
		response.put("data", isExists);
		
		return response;
	}
}
