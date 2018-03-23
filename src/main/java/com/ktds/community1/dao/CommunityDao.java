package com.ktds.community1.dao;

import java.util.List;

import com.ktds.community1.vo.CommunitySearchVO;
import com.ktds.community1.vo.CommunityVO;

public interface CommunityDao {
	public int selectCountAll(CommunitySearchVO communitySearchVO);
	
	public List<CommunityVO> selectAll(CommunitySearchVO communitySearchVO);

	public CommunityVO selectOne(int id);

	public int incrementViewCount(int id);

	public int incrementRecommendCount(int id);

	public int insertCommunity(CommunityVO communityVO);

	public int deletCommunity(int id);

	public int deleteMyCommunities(int userID);

	public int updateCommunity(CommunityVO communityVO);

	public int selectMyCommunitiesCount(int userID);

	public List<CommunityVO> selectMyCommunities(int userID);
	
	public int deleteCommunities(List<Integer> ids, int userId);
}