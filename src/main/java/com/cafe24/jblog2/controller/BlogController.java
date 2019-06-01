package com.cafe24.jblog2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog2.dto.JSONResult;
import com.cafe24.jblog2.security.Auth;
import com.cafe24.jblog2.security.Auth.Role;
import com.cafe24.jblog2.service.BlogService;
import com.cafe24.jblog2.service.CategoryService;
import com.cafe24.jblog2.service.FileuploadService;
import com.cafe24.jblog2.service.PostService;
import com.cafe24.jblog2.vo.BlogVo;
import com.cafe24.jblog2.vo.CategoryVo;
import com.cafe24.jblog2.vo.PostVo;

@Controller
@RequestMapping("/{id:^(?!assets).*$}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PostService postService;
	
	// 번호를 눌렀을 때 글 가져오기
	@ResponseBody
	@RequestMapping("/post")
 	public JSONResult getPostByNo (String no) {
		PostVo postVo=	postService.getPostByNo(Integer.parseInt(no));
		System.out.println("getPostByNo| /post");
		return   JSONResult.success(postVo);
	}
	
 	@RequestMapping(value= {"","/{categoryNo}","/{categoryNo}/{postNo}"})
	public String blog(@PathVariable(value="id") String id
			,@PathVariable Optional<Integer> categoryNo
			,@PathVariable Optional<Integer> postNo
			,Model model
			) {
		System.out.println("blog| /{categoryNo}\",\"/{categoryNo}/{postNo}");

		if(categoryNo.isPresent())
		System.out.println("사진은 들어오면안됭..ㅠ_ㅠ "+id+"/"+categoryNo);
		BlogVo blogVo=blogService.getBlog(id);
		if(blogVo==null) {
			model.addAttribute("result","fail");
		}
		List<CategoryVo> categoryVo=categoryService.getCategoryList(id);
		List<PostVo> postVo=postService.getPostList(id,categoryNo,postNo);
		model.addAttribute("categoryVo",categoryVo);
		model.addAttribute("postVo",postVo);
		model.addAttribute("blogVo",blogVo);
		return "blog/blog-main";
	}
	
	
}
