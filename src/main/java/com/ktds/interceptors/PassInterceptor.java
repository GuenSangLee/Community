package com.ktds.interceptors;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ktds.actionhistory.service.ActionHistoryService;
import com.ktds.actionhistory.vo.ActionHistory;
import com.ktds.actionhistory.vo.ActionHistoryVO;
import com.ktds.member.constants.Member;
import com.ktds.member.vo.MemberVO;

public class PassInterceptor extends HandlerInterceptorAdapter{
	
	private ActionHistoryService actionHistoryService;
	//@RequesetAttribute 사용.
	private ActionHistoryVO history2;
	
	public void setActionHistoryService(ActionHistoryService actionHistoryService) {
		this.actionHistoryService = actionHistoryService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		
		if( member == null) {
			member = new MemberVO();
		}
		
		ActionHistoryVO history = new ActionHistoryVO();
		history.setReqType(ActionHistory.ReqType.VIEW);
		history.setIp(request.getRemoteAddr());
		history.setUserId(member.getId());
		history.setEmail(member.getEmail());
		
		String log= String.format(ActionHistory.Log.VIEW, request.getRequestURI(),request.getMethod());
		history.setLog(log);
		
		actionHistoryService.createActionHistory(history);
		
/*		//Controller에게 ip를 포함한 ActionHistoryVO 전달.(반복해야할 일들을 미리 행한뒤 Controller에 넘겨준다.)
		//model 사용 방법.  mapping 파라미터에 Model model 추가.
		ActionHistoryVO history2 = new ActionHistoryVO();
		history2.setIp(request.getRemoteAddr());
		history2.setUserId(member.getId());
		history2.setEmail(member.getEmail());
		
		request.setAttribute("actionHistory", history2);
	*/	
		history2= new ActionHistoryVO();
		history2.setIp(request.getRemoteAddr());
		history2.setUserId(member.getId());
		history2.setEmail(member.getEmail());
		request.setAttribute("actionHistory", history2);
		
		
		return true;
	}
	
	
	//Controller가 작동한 뒤에 실행된다.
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if(modelAndView == null) {
			return;
		}
		/* model 사용.
		Map<String,Object> model= modelAndView.getModel();
		ActionHistoryVO history= (ActionHistoryVO) model.get("actionHistory");
		*/
		if(history2 != null && history2.getReqType() != null) {
			actionHistoryService.createActionHistory(history2);
		}
	}
}
