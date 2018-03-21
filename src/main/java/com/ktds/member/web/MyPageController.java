package com.ktds.member.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community1.service.CommunityService;
import com.ktds.community1.vo.CommunityVO;
import com.ktds.member.constants.Member;
import com.ktds.member.service.MemberService;
import com.ktds.member.vo.MemberVO;

@Controller
public class MyPageController {
	private MemberService memberService;
	private CommunityService communityService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	@RequestMapping("/mypage/communities")
	public ModelAndView viewMyCommunities(HttpSession session){
		MemberVO member= (MemberVO) session.getAttribute(Member.USER);
		List<CommunityVO> myCommunities= communityService.readMyCommunities(member.getId());
		
		ModelAndView view= new ModelAndView();
		view.setViewName("member/mypage/myCommunities");
		view.addObject("myCommunities", myCommunities);
		
		return view;
	}
	@RequestMapping("/mypage/communities/delete")
	public String doDeleteMyCommunitiesaction(HttpSession session,@RequestParam List<Integer> delete) {
		MemberVO member= (MemberVO) session.getAttribute(Member.USER);
		communityService.deleteCommunities(delete, member.getId());

		return "redirect:/";
	}
}
