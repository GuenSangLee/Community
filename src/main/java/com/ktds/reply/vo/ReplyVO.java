package com.ktds.reply.vo;

import com.ktds.community1.vo.CommunityVO;
import com.ktds.member.vo.MemberVO;

public class ReplyVO {
	private int id;
	private int userId;
	private String body;
	private String registDate;
	private int parentReplyId;
	private int communityId;

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}


	private MemberVO memberVO;
	private CommunityVO communityVO;
	private ReplyVO replyVO;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public int getParentReplyId() {
		return parentReplyId;
	}

	public void setParentReplyId(int parentReplyId) {
		this.parentReplyId = parentReplyId;
	}

	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}

	public CommunityVO getCommunityVO() {
		return communityVO;
	}

	public void setCommunityVO(CommunityVO communityVO) {
		this.communityVO = communityVO;
	}

	public ReplyVO getReplyVO() {
		return replyVO;
	}

	public void setReplyVO(ReplyVO replyVO) {
		this.replyVO = replyVO;
	}

}
