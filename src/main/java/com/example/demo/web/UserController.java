package com.example.demo.web;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/form")
	public String form() {
		return "/users/form";
	}
	
	@GetMapping("/login")
	public String login() {
		return "/users/login";
	}
	
	@GetMapping("/login_failed")
	public String login_failed() {
		return "/users/login_failed";
	}
	

	/*@GetMapping("/{index}")
	public String show(@PathVariable int index, Model model) {
		model.addAttribute("user",users.get(index));
	}*/
	
	@GetMapping("/{id}")
	public ModelAndView show(@PathVariable Long id) {
		
		ModelAndView mav = new ModelAndView("/users/profile");
		User user = userRepository.findOne(id);
		mav.addObject("user", user);
		return mav;
	}
	
	@PostMapping("")
	public ModelAndView userCreate(User user) {
		userRepository.save(user);
		
		return new ModelAndView("redirect:/users");
	}
	
	@GetMapping("")
	public ModelAndView userList() {
		ModelAndView mav = new ModelAndView("/users/list");
		mav.addObject("users", userRepository.findAll());
		
		return mav;
	}
	
	@GetMapping("/{id}/form")
	public ModelAndView updateForm(@PathVariable Long id, HttpSession session) {
		Object tempUser = session.getAttribute("loginedUser");
		if(tempUser == null) {
			return new ModelAndView ("redirect:/");
		}
		
		User loginedUser = (User) tempUser;
		if(!loginedUser.matchId(id)) {
			return new ModelAndView ("redirect:/");
		}
		
		User user = userRepository.findOne(id);
		ModelAndView mav = new ModelAndView("/users/updateForm");
		mav.addObject("user", user);
		
		return mav;
	}
	
	@PostMapping("/{id}")
	public String update(@PathVariable Long id, User user) {
		User dbUser = userRepository.findOne(id);
		if (dbUser.update(user)) {
			userRepository.save(dbUser);
		}
		
		//아래 코드는 알맞게 동작 하는가?
		/*if (user.update(dbUser)) {
			userRepository.save(user);
		}*/
		
		//아래 코드를 더 객체지향적으로 만들어라. User.java 에 메소드를 만들어라 ->완료
		/*if (dbUser.getPassword().equals(user.getPassword())) {
			dbUser.setName(user.getName());
			dbUser.setEmail(user.getEmail());
			userRepository.save(dbUser);
		}*/
		return "redirect:/users";
	}
	
	//RequestMapping 어노테이션 추가하라
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		System.out.println("userId is " + userId + " password is " + password);
		
		User dbUser = userRepository.findByUserId(userId);
		if (dbUser == null) {
			return "/users/login_failed";
		}
		if(!dbUser.matchPassword(password)) {
			return "/users/login_failed";
		}
		
		session.setAttribute("loginedUser", dbUser);
		
		return"redirect:/index";
	}
	
}
