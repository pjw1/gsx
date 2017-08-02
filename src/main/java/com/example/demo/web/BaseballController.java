package com.example.demo.web;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseballController {

	@GetMapping("/baseball")
	public ModelAndView input(String inputValue) {
		int com[] = new int[3];
		int num[] = new int[3];
		int strike = 0;
		int ball = 0;

		shuffle(com);
		loop(strike, ball, num, com, inputValue);

		ModelAndView mav = new ModelAndView("baseball/result");
		mav.addObject("strike", strike);
		mav.addObject("ball", ball);
		return mav;
	}

	private static void shuffle(int[] com) {

		String[] array = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		Collections.shuffle(Arrays.asList(array));

		com[0] = Integer.parseInt(array[0]);
		com[1] = Integer.parseInt(array[1]);
		com[2] = Integer.parseInt(array[2]);

		System.out.printf("%d%d%d\n", com[0], com[1], com[2]);

	}

	private static void loop(int strike, int ball, int[] num, int[] com, String inputValue) {

		input(num, inputValue);
		strike = 0;
		ball = 0;
		strike = strike(strike, num, com);
		ball = ball(ball, num, com);
		

	}

	private static void input(int[] num, String inputValue) {
		String nums[];

		nums = inputValue.split("");
		num[0] = Integer.parseInt(nums[0]);
		num[1] = Integer.parseInt(nums[1]);
		num[2] = Integer.parseInt(nums[2]);

	}

	private static int strike(int strike, int[] num, int[] com) {
		for (int i = 0; i < 3; i++) {
			if (num[i] == com[i]) {
				strike++;
			}
		}
		return strike;
	}

	// use ArrayList.contains() -> 객체지향 사고방식. 메써드, 어레이리스트 학습.
	private static int ball(int ball, int[] num, int[] com) {
		for (int i = 0; i < 3; i++) {
			if (num[i] == com[(i + 1) % 3] || num[i] == com[(i + 2) % 3]) {
				ball++;
			}
		}
		return ball;
	}

}
