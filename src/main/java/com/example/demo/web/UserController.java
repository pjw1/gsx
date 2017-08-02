package com.example.demo.web;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.User;

@Controller
public class UserController {
	ArrayList<User> users = new ArrayList<>();
	
	@GetMapping("/users/{index}")
	public ModelAndView show(@PathVariable int index) {
		User user = users.get(index);
		
		ModelAndView mav = new ModelAndView("/baseball/profile");
		mav.addObject("user", user);
		return mav;
	}
	
	
	@PostMapping("/users")
	public ModelAndView create(User user) {
		users.add(user);
		System.out.println("size : " + users.size());
		
		return new ModelAndView("redirect:/users");
	}
	
	@GetMapping("/users")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView("/baseball/list");
		mav.addObject("users", users);
		
		return mav;
	}
}
