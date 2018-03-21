package com.ktds.member.dao;

import com.ktds.member.vo.MemberVO;

public interface MemberDao {

	public int insertMember(MemberVO memberVO);

	public MemberVO selectMember(MemberVO memberVO);

	public int selectCountMemberEmail(String email);

	public int selectIsMemberNickname(String nickname);

	public String selectSalt(String email);

	public int deleteMember(int id);

}
