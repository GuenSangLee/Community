package com.ktds.actionhistory.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.actionhistory.vo.ActionHistorySearchVO;
import com.ktds.actionhistory.vo.ActionHistoryVO;

public class ActionHistoryDaoImplForMysql extends SqlSessionDaoSupport  implements ActionHistoryDao {

	@Override
	public int insertActionHistory(ActionHistoryVO history) {
		return getSqlSession().insert("ActionHistoryDao.insertActionHistory", history);
	}

	@Override
	public int selectAllActionHistroyCount(ActionHistorySearchVO actionHistorySearchVO) {
		return getSqlSession().selectOne("ActionHistoryDao.selectAllActionHistroyCount", actionHistorySearchVO);
	}

	@Override
	public List<ActionHistoryVO> selectAllActionHistory(ActionHistorySearchVO actionHistroySearchVO) {
		return getSqlSession().selectList("ActionHistoryDao.selectAllActionHistory", actionHistroySearchVO);
	}

}
