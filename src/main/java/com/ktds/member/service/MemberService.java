package com.ktds.member.service;

import com.ktds.member.vo.MemberVO;

public interface MemberService {
	public boolean createMember(MemberVO member);
	public MemberVO readMember(MemberVO member);
	public boolean removeMember(int id);
	public boolean deleteMember(int id, String deleteFlag);
	public boolean readCountMemberEmail(String email);
	public boolean readIsMemberNickname(String nickname);
	
}
