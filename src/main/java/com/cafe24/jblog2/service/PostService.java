package com.cafe24.jblog2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe24.jblog2.repository.PostDao;
import com.cafe24.jblog2.vo.PostVo;

@Service
public class PostService {
	
	@Autowired
	private PostDao postDao;

	
	
	// 예상되지않은값에대한 처리 해줘야함!!!
	public List<PostVo> getPostList(String pathNo1, Optional<Integer> pathNo2, Optional<Integer> pathNo3) {
		String blogId=pathNo1;
		int categoryNo=-1;
		int postNo=-1;
 		if(pathNo2.isPresent()) categoryNo= (pathNo2.get());
 		if(pathNo3.isPresent()) postNo= (pathNo3.get());
 		List<PostVo> list=null;
 		System.out.println("**************postService getPostList "+blogId+"   "+categoryNo+"  "+postNo);
 		if(postNo!=-1) { // 글번호가 정해져있어, 글을 기준으로 이전글 리스트
 			list=postDao.getPostListByPostNo(postNo);
 		}else { // 카테고리번호의 유무에 따라 전체글 또는 카테고리내 최신글 리스트
 			list=postDao.getPostList(blogId,categoryNo);
 			System.out.println("**************postService getPostList");
 			for (PostVo postVo : list) {
				System.out.println(postVo);
			}
 		}
		return list;

	}



	public PostVo getPostByNo(int no) {
		return postDao.getPostByNo(no);
	}



	public void deleteAllPostByCategoryNo(int no) {
		postDao.deleteAllPostByCategoryNo(no);
	}



	public void writePost(PostVo postVo) {
		postDao.writePost(postVo);

	}

}
