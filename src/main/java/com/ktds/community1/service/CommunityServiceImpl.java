package com.ktds.community1.service;

import java.util.ArrayList;
import java.util.List;

import com.ktds.community1.dao.CommunityDao;
import com.ktds.community1.vo.CommunityVO;

public class CommunityServiceImpl implements CommunityService{
	// 멤버변수 추가
	private CommunityDao communityDao;
	// setter 추가 setCommunity + ctrl + space
	public void setCommunityDao(CommunityDao communityDao) {
		this.communityDao = communityDao;
	}
	
	@Override
	public List<CommunityVO> getall() {
		return communityDao.selectAll();
	}

	@Override
	public boolean createCoomunity(CommunityVO communityVO) {
		
		//html에서 /n(new line) 을 실행하기 위한 코드
		String body = communityVO.getBody();
		// \n -> <br/>
		body = body.replace("\n", "<br/>");
		communityVO.setBody(body);
		
		
		
		int insertCount = communityDao.insertCommunity(communityVO);
		
		return insertCount > 0;
	}

	@Override
	public CommunityVO getOne(int id) {
		return communityDao.selectOne(id);
		
	}

	@Override
	public int upRecommendCount(int id) {
		if ( communityDao.incrementRecommendCount(id)>0 ) {
			return 1;
		}
		return 0;
	}

	@Override
	public int upViewCount(int id) {
		if (communityDao.incrementViewCount(id)>0) {
			return 1;
		}
		return 0;
	}
	// 금지어 만들기
	
	public boolean filter(String str) {
		List<String> blackList = new ArrayList<String>();
		blackList.add("욕1");
		blackList.add("욕2");
		blackList.add("욕3");
		blackList.add("욕4");
		blackList.add("욕5");
		
		//str ==> 남편은 욕1이에요. 를 split해준다
		String[] splitString = str.split(" ");
		
		for ( String word : splitString) {
			
			for ( String blackString : blackList ) {
				if ( blackString.contains(word) ) {
					return true;
				}
				
			}
		}
		return false;
	}

	@Override
	public int deleteCommunity(int id) {
		if(communityDao.deletCommunity(id) > 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public boolean updateCommunity(CommunityVO communityVO) {
		return communityDao.updateCommunity(communityVO) > 0;
	}

	@Override
	public int readMyCommunitiesCount(int userID) {
		return communityDao.selectMyCommunitiesCount(userID);
	}

	@Override
	public List<CommunityVO> readMyCommunities(int userID) {
		return communityDao.selectMyCommunities(userID);
	}

	@Override
	public boolean deleteCommunities(List<Integer> ids, int userId) {
		return communityDao.deleteCommunities(ids, userId) > 0;
	}	
}
