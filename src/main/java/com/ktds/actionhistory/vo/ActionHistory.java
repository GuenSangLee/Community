package com.ktds.actionhistory.vo;

public class ActionHistory {
	static public interface ReqType{
		public static final String VIEW = "view";
		public static final String MEMBER= "member";
		public static final String COMMUNITY= "community";
		public static final String UPLOAD= "upl";
		public static final String DOWNLOAD= "dwn";
	}
	
	static public interface Log{
		public static final String VIEW = "View : %s, Method : %s";
		public static final String LOGIN = "Login : %s";
		public static final String LOGOUT= "Logout : %s";
		public static final String REGIST= "Regist : Email(%s), Nickname(%s), Result(%s)";
		public static final String WRITE= "Write : Subject(%s), Body(%s)";
		public static final String UPDATE= "Udate : Subject(%s), Body(%s)";
		public static final String DELETE= "Delete : ID(%d), Subject(%s), Body(%s)";
		public static final String READ= "Read : ID(%d), Subject(%s), Body(%s)";
		public static final String RECOMMEND= "Recommend : ID(%d)";
		public static final String WRITE_REPLY= "Write Reply : CommunityID(%d), Body(%s)";
		
		public static final String DWONLOAD= "Download : CommunityID(%d), Filename(%s)";
		public static final String UPLOAD= "Uplpad : Filename(%s)";
		
		
	}
}
