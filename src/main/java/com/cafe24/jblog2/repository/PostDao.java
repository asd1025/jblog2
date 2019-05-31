package com.cafe24.jblog2.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog2.vo.PostVo;
import com.cafe24.jblog2.vo.UsersVo;

@Repository
public class PostDao {
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SqlSession sqlSession;


	/*
	 * public void insert(PostVo vo) { sqlSession.insert("",vo); }
	 */
	
//	public PostVo getPostByPostNo(int no) {
//		return  sqlSession.selectOne("post.getPostByPostNo",no);
//	}
	/**
	 *  카테고리가 정해지면, 카테고리 내 최신글 리스트
	 *  카테고리가 정해지지 않는 다면, 전체글의 최신글 리스트
	 * */
	public List<PostVo> getPostList(String blogId, int categoryNo) {
		  Map<String, Object> map=new HashMap<String, Object>();
		  map.put("blogId", blogId);
		  map.put("categoryNo", categoryNo );
		  return  sqlSession.selectList("post.getPostList",map);
	}
	
	/**
	 *  글 번호가 속해있는 카테고리 내에, 그 글의 시점을 기준으로 이전글들의 리스트
	 * */
	public List<PostVo> getPostListByPostNo(int postNo) {
		  return  sqlSession.selectList("post.getPostListByPostNo",postNo);
	}

	public PostVo getPostByNo(int no) {
		return sqlSession.selectOne("post.getPostByNo",no);
	}

	public void deleteAllPostByCategoryNo(int no) {
			sqlSession.delete("post.deleteAllPostByCategoryNo",no);
	}

	public void writePost(PostVo postVo) {
		sqlSession.insert("post.writePost",postVo);
		
	}

	 
}
