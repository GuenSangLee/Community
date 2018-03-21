package com.ktds.member.dao;




import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.member.vo.MemberVO;

public class MemberDaoImplForOracle extends SqlSessionDaoSupport implements MemberDao {

	//TNS Listener Error
	//1. 시작 - Oracle - Stop DB > Start DB
	
	@Override
	public int insertMember(MemberVO memberVO) {
		return getSqlSession().insert("MemberDao.insertMember", memberVO);

	}

	@Override
	public MemberVO selectMember(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("MemberDao.selectMember", memberVO);
	}

	@Override
	public int deleteMember(int id) {
		return getSqlSession().delete("MemberDao.deleteMember", id);
	}

	@Override
	public String selectSalt(String email) {
		return getSqlSession().selectOne("MemberDao.selectSalt",email);
	}

	@Override
	public int selectCountMemberEmail(String email) {
		return getSqlSession().selectOne("MemberDao.selectCountMemberEmail", email);
	}

	@Override
	public int selectIsMemberNickname(String nickname) {
		return getSqlSession().selectOne("MemberDao.selectIsMemberNickname", nickname);
	}

}
