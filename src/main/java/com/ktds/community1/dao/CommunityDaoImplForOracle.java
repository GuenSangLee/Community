package com.ktds.community1.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.community1.vo.CommunitySearchVO;
import com.ktds.community1.vo.CommunityVO;

public class CommunityDaoImplForOracle extends SqlSessionDaoSupport implements CommunityDao{

	@Override
	public List<CommunityVO> selectAll(CommunitySearchVO communitySearchVO) {
		return getSqlSession().selectList("CommunityDao.selectAll", communitySearchVO);
	}

	@Override
	public CommunityVO selectOne(int id) {
		return getSqlSession().selectOne("CommunityDao.selectOne", id);
	}

	@Override
	public int incrementViewCount(int id) {
		return getSqlSession().update("CommunityDao.incrementViewCount", id);
	}

	@Override
	public int incrementRecommendCount(int id) {
		return getSqlSession().update("CommunityDao.incrementRecommendCount", id);
	}

	@Override
	public int insertCommunity(CommunityVO communityVO) {
		return getSqlSession().insert("CommunityDao.insertCommunity", communityVO);
	}
	// SqlSessionDaoSupport의 sqlSessionTemplate Bean 객체를 가지고 있음

	@Override
	public int deletCommunity(int id) {
		return getSqlSession().delete("CommunityDao.deletCommunity",id);
	}

	@Override
	public int deleteMyCommunities(int userID) {
		return getSqlSession().delete("CommunityDao.deleteMyCommunities", userID);
	}

	@Override
	public int updateCommunity(CommunityVO communityVO) {
		return getSqlSession().update("CommunityDao.updateCommunity", communityVO);
	}

	@Override
	public int selectMyCommunitiesCount(int userID) {
		return getSqlSession().selectOne("CommunityDao.selectMyCommunitiesCount",userID);
	}

	@Override
	public List<CommunityVO> selectMyCommunities(int userID) {
		return getSqlSession().selectList("CommunityDao.selectMyCommunities", userID);
	}

	@Override
	public int deleteCommunities(List<Integer> ids, int userId) {
		//넘기는 인자는 1개로 제한된다. 여러개를 넘기려면 map을 이용하자.
		Map<String, Object> params= new HashMap<String,Object>();
		params.put("ids", ids);
		params.put("userId", userId);
		return getSqlSession().delete("CommunityDao.deleteCommunities", params);
	}

	@Override
	public int selectCountAll(CommunitySearchVO communitySearchVO) {
		return getSqlSession().selectOne("CommunityDao.selectCountAll",communitySearchVO);
	}
}
