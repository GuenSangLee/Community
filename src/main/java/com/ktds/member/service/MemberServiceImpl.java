package com.ktds.member.service;

import com.ktds.community1.dao.CommunityDao;
import com.ktds.member.dao.MemberDao;
import com.ktds.member.util.SHA256Util;
import com.ktds.member.vo.MemberVO;

public class MemberServiceImpl implements MemberService {
	private MemberDao memberDao;
	private CommunityDao communityDao;

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public void setCommunityDao(CommunityDao communityDao) {
		this.communityDao = communityDao;
	}

	@Override
	public boolean createMember(MemberVO member) {
		String salt = SHA256Util.generateSalt();
		member.setSalt(salt);
		String password = member.getPassword();
		member.setPassword(SHA256Util.getEncrypt(password, salt));

		return memberDao.insertMember(member) > 0;
	}

	@Override
	public MemberVO readMember(MemberVO member) {
		// 1.사용자 ID의 salt가져오기
		String salt = memberDao.selectSalt(member.getEmail());
		if (salt == null) {
			salt = "";
		}
		// 2.salt로 비번 계산.
		String password = member.getPassword();
		password = SHA256Util.getEncrypt(password, salt);
		member.setPassword(password);

		// 3.암호 체크.

		return memberDao.selectMember(member);
	}

	@Override
	public boolean removeMember(int id) {
		return memberDao.deleteMember(id) > 0;
	}

	@Override
	public boolean deleteMember(int id, String deleteFlag) {
		if (deleteFlag.equals("y")) {
			communityDao.deleteMyCommunities(id);
		}
		return memberDao.deleteMember(id) > 0;
	}

	@Override
	public boolean readCountMemberEmail(String email) {
		return memberDao.selectCountMemberEmail(email) > 0;
	}

	@Override
	public boolean readIsMemberNickname(String nickname) {
		return memberDao.selectIsMemberNickname(nickname) > 0;
	}

}
