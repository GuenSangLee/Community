package com.ktds.member.service;

import com.ktds.community1.dao.CommunityDao;
import com.ktds.member.dao.MemberDao;
import com.ktds.member.vo.MemberVO;

public class MemberServiceImpl implements MemberService{
	private MemberDao memberDao;
	private CommunityDao communityDao;
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	public void setCommunityDao(CommunityDao communityDao) {
		this.communityDao= communityDao;
	}
	
	@Override
	public boolean createMember(MemberVO member) {
		return memberDao.insertMember(member) > 0;
	}

	@Override
	public MemberVO readMember(MemberVO member) {
		return memberDao.selectMember(member);
	}

	@Override
	public boolean removeMember(int id) {
		return memberDao.deleteMember(id) > 0;
	}

	@Override
	public boolean deleteMember(int id, String deleteFlag) {
		if(deleteFlag.equals("y")) {
			communityDao.deleteMyCommunities(id);
		}
		return memberDao.deleteMember(id) > 0;
	}

}
