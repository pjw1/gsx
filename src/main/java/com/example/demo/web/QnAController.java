package com.example.demo.web;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.Qna;

@Controller
public class QnAController {
	ArrayList<Qna> qnas = new ArrayList<>();
	
	@PostMapping("/qnas")
	public ModelAndView qnaCreate(Qna qna) {
		qnas.add(qna);
		
		return new ModelAndView("redirect:/");
	}
	
	@GetMapping("/index")
	public ModelAndView qnaList() {
		ModelAndView mav = new ModelAndView("/index");
		mav.addObject("qnas", qnas);
		
		return mav;
	}	

}
