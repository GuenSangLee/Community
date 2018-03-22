package com.ktds.reply.dao;

import java.util.List;

import com.ktds.reply.vo.ReplyVO;

public interface ReplyDao {
	public int insertReply(ReplyVO replyVO);

	public int updateReply(ReplyVO replyVO);

	public List<ReplyVO> selectAllReplies(int communityId);

	public ReplyVO selectOne(int replyId);

}
