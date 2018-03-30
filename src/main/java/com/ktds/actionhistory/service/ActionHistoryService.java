package com.ktds.actionhistory.service;

import com.ktds.actionhistory.vo.ActionHistorySearchVO;
import com.ktds.actionhistory.vo.ActionHistoryVO;

import io.github.seccoding.web.pager.explorer.PageExplorer;

public interface ActionHistoryService {
	public boolean createActionHistory(ActionHistoryVO actionHistroyVO);
	
	public PageExplorer readAllActionHistory(ActionHistorySearchVO actionHistorySearchVO);
	
	
}
