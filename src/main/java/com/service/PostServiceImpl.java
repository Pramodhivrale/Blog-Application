package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.PostEntity;
import com.entity.UserRegistrationEntity;
import com.repo.PostDatarepo;
import com.repo.UserRepo;
import com.response.AddPostData;

import jakarta.servlet.http.HttpSession;

@Service
public class PostServiceImpl implements PostService
{
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private PostDatarepo postDatarepo;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public String addPost(AddPostData addPostData) 
	{
		Integer userId = (Integer)httpSession.getAttribute("UserId");
		
		PostEntity postEntity=new PostEntity();
		
		Optional<UserRegistrationEntity> byId = userRepo.findById(userId);
		UserRegistrationEntity userRegistrationEntity = byId.get();
		
		BeanUtils.copyProperties(addPostData, postEntity);
		postEntity.setUser(userRegistrationEntity);
		
		PostEntity save = postDatarepo.save(postEntity);
		
		
		if(save == null ) 
		{
			return "Post is not added !";
		}
		
		return "Post Added SuccesFully !!";
	}

	@Override
	public List<PostEntity> getAllPost() {
		List<PostEntity> posts = postDatarepo.findAll();
		return posts;
	}

	@Override
	public PostEntity getPost(Integer id) {
		
		Optional<PostEntity> byId = postDatarepo.findById(id);
		PostEntity postEntity = byId.get();
		if(postEntity==null) {
			System.out.println("Null");
		}
		else {
			System.out.println(postEntity.getTitle());
		}
		
		return postEntity;
	}

}