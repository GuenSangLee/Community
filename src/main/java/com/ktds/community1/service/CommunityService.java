package com.ktds.community1.service;

import java.util.List;

import com.ktds.community1.vo.CommunityVO;

public interface CommunityService {
	public List<CommunityVO> getall();
	public CommunityVO getOne(int id);
	public boolean createCoomunity(CommunityVO communityVO);
	public int upViewCount(int id);
	public int upRecommendCount(int id);
	public int deleteCommunity(int id);
	public boolean updateCommunity(CommunityVO communityVO);
	public int readMyCommunitiesCount(int userID);
	public List<CommunityVO> readMyCommunities(int userID);
	public boolean deleteCommunities(List<Integer> ids, int userId);
}
