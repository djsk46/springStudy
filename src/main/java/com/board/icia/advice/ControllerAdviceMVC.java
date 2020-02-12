package com.board.icia.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.icia.exception.PageExeption;

import lombok.extern.log4j.Log4j;

@ControllerAdvice
@Log4j
public class ControllerAdviceMVC {
	@ExceptionHandler(PageExeption.class)
	public String except(PageExeption ex, RedirectAttributes attr) {
		attr.addFlashAttribute("msg",ex.getMessage());
		return "redirect:/boardlist";
	}
}
