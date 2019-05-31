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
import com.cafe24.jblog2.service.BlogService;
import com.cafe24.jblog2.service.CategoryService;
import com.cafe24.jblog2.service.FileuploadService;
import com.cafe24.jblog2.service.PostService;
import com.cafe24.jblog2.vo.BlogVo;
import com.cafe24.jblog2.vo.CategoryVo;
import com.cafe24.jblog2.vo.PostVo;

@Controller
@RequestMapping("/{pathNo1:^(?!assets).*$}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PostService postService;
	 
	
	// 글쓰기 
	@RequestMapping(value="/admin/write",method=RequestMethod.POST)
	public String writePost (@PathVariable(value="pathNo1") String pathNo1,@ModelAttribute PostVo postVo, Model model) {
		 System.out.println(postVo+" .... WRITE POST");
		 postService.writePost(postVo);
		return  "redirect:/"+pathNo1;
	}
	// 글쓰기  폼으로 
	@RequestMapping("/admin/write")
	public String write (@PathVariable(value="pathNo1") String pathNo1,Model model) {
		System.out.println("write| /admin/write GET");
		List<CategoryVo> list=categoryService.getCategoryList(pathNo1);
		model.addAttribute("list",list);
		return  "blog/blog-admin-write";
	}
	
	// 카테고리 삭제  / 그 카테고리 내 글들도 모두 삭제
	@ResponseBody
	@RequestMapping("/admin/deleteCategory")
	public JSONResult deleteCategory (String no) {
		categoryService.deleteCategory(Integer.parseInt(no));
		postService.deleteAllPostByCategoryNo(Integer.parseInt(no));
		System.out.println("deleteCategoty| /admin/deleteCategory ");
		return   JSONResult.success("success");
	}
	  
	
	// 번호를 눌렀을 때 글 가져오기
	@ResponseBody
	@RequestMapping("/post")
	public JSONResult getPostByNo (String no) {
		PostVo postVo=	postService.getPostByNo(Integer.parseInt(no));
		System.out.println("getPostByNo| /post");
		return   JSONResult.success(postVo);
	}
	
	// 카테고리 추가시 
	@ResponseBody
	@RequestMapping(value= {"/admin/insertCategoty"  },method = RequestMethod.POST)
	public JSONResult insertCategoty(@PathVariable(value="pathNo1") String pathNo1, @ModelAttribute CategoryVo vo) {
		System.out.println("insertCategoty| /admin/insertCategoty");
		vo.setBlog_id(pathNo1);
		System.out.println(vo+"..insera");
		categoryService.insertCategory(vo);
		return   JSONResult.success("success");
	}
	
	// 블로그 정보 수정시
	@RequestMapping(value= {"/admin/update"  },method = RequestMethod.POST)
	public String update(@PathVariable(value="pathNo1") String pathNo1, @ModelAttribute BlogVo blogVo,
			 @RequestParam(value="logo-file")  MultipartFile multipartFile, Model model
			) {
		System.out.println("update|  /admin/update");
		blogService.updateBlog(blogVo,multipartFile);
		model.addAttribute("blogVo",blogVo);
 		return "redirect:/"+pathNo1;
	}
	
	// 블로그 아이디에 해당하는 모든 카테고리 가져오기 (AJAX)
	@ResponseBody
	@RequestMapping(value= {"/admin/getCategoryList"  })
	public JSONResult getCategoryList(@PathVariable(value="pathNo1") String pathNo1) {
		System.out.println("getCategoryList|  /admin/getCategoryList");
		List<CategoryVo> list=categoryService.getCategoryList(pathNo1);
		return JSONResult.success(list);
	}
	
	// 카테고리 수정하는 뷰로 가기 
	@RequestMapping(value= {"/admin/category"  })
	 public String adminBlogForm(@PathVariable(value="pathNo1") String pathNo1 ,Model model
			 ) {
		System.out.println("adminBlogForm|  /admin/category");

		//List<CategoryVo> list=categoryService.getCategoryList(pathNo1);
		//model.addAttribute("list",list);
		 return "blog/blog-admin-category";
	 }
	
	@RequestMapping(value= {"/admin/basic"  })
	public String adminBlogBasic(@PathVariable(value="pathNo1") String pathNo1 ,Model model
			) {
		System.out.println("adminBlogBasic|  /admin/basic");

		BlogVo blogVo=blogService.getBlog(pathNo1);
		model.addAttribute("blogVo",blogVo);
		return "blog/blog-admin-basic";
	}
	@RequestMapping(value= {"/admin/basic"  },method = RequestMethod.POST)
	public String adminBlog(@PathVariable(value="pathNo1") String pathNo1 ,Model model
			) {
		System.out.println("adminBlog|  /admin/basic");

		BlogVo blogVo=blogService.getBlog(pathNo1);
		model.addAttribute("blogVo",blogVo);
		return "blog/blog-admin-basic";
	}
	
	@RequestMapping(value= {"","/{pathNo2}","/{pathNo2}/{pathNo3}"})
	public String blog(@PathVariable(value="pathNo1") String pathNo1
			,@PathVariable Optional<Integer> pathNo2
			,@PathVariable Optional<Integer> pathNo3
			,Model model
			) {
		System.out.println("blog| /{pathNo2}\",\"/{pathNo2}/{pathNo3}");

		if(pathNo2.isPresent())
		System.out.println("사진은 들어오면안됭..ㅠ_ㅠ "+pathNo1+"/"+pathNo2);
		BlogVo blogVo=blogService.getBlog(pathNo1);
		if(blogVo==null) {
			model.addAttribute("result","fail");
		}
		List<CategoryVo> categoryVo=categoryService.getCategoryList(pathNo1);
		List<PostVo> postVo=postService.getPostList(pathNo1,pathNo2,pathNo3);
		model.addAttribute("categoryVo",categoryVo);
		model.addAttribute("postVo",postVo);
		model.addAttribute("blogVo",blogVo);
		return "blog/blog-main";
	}
	
	
}
