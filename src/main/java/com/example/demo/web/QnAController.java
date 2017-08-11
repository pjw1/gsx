package com.example.demo.web;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.Qna;
import com.example.demo.domain.QnaRepository;
import com.example.demo.domain.User;

@Controller
public class QnAController {
	@Autowired
	QnaRepository qnas;
	
	@GetMapping("/qnas/form")
	public ModelAndView form(HttpSession session) {
		User loginedUser = (User) session.getAttribute("loginedUser");
		ModelAndView mav;
		if (loginedUser == null) {
			return new ModelAndView("redirect:/");
		}
		
		mav = new ModelAndView("/qnas/form");
		return mav; 
	}
	
	@PostMapping("/qnas")
	public ModelAndView qnaCreate(Qna qna) {
		qnas.save(qna);
		
		return new ModelAndView("redirect:/index");
	}
	
	@GetMapping("/index")
	public ModelAndView qnaList() {
		ModelAndView mav = new ModelAndView("/index");
		mav.addObject("qnas", qnas.findAll());
		
		return mav;
	}	
	
	@GetMapping("/qnas/show/{id}")
	public ModelAndView qnaShow(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView("/qnas/show");
		mav.addObject("qna", qnas.findOne(id));
		
		return mav;
	}

	
}
