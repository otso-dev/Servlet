package com.study.servlet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.study.servlet.dto.RequestDto;
import com.study.servlet.dto.ResponseDto;
import com.study.servlet.entity.User;
import com.study.servlet.service.UserService;
import com.study.servlet.service.UserServiceImpl;

/*
 * GET 요청
 * UserList에서 username을 통해 사용자 정보 찾기
 * 응답데이터 JSON(User)
 * 
 * POST 요청
 * 요청 데이터 JSON(User)
 * JSON을 User 객체로 변환 후 ToString으로 출력
 * 응답 데이터 "success: true"
 * */

@WebServlet("/auth")
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService;
	private Gson gson;

	public Auth() {
		userService = UserServiceImpl.getInstance();
		gson = new Gson();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("GET");
		String userSearch = request.getParameter("username");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("POST");
		User user = RequestDto.<User>convertRequestBody(request, User.class);
		
		boolean duplicatedflag = userService.duplicatedUsername(user.getUsername());
		
		response.setContentType("application/json:charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(duplicatedflag) {
			ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>(400,"duplicated username", duplicatedflag);
			out.println(gson.toJson(responseDto));
			return;
		}
		
		ResponseDto<Integer> responseDto = new ResponseDto<Integer>(201, "sign up", userService.addUser(user));
		out.println(gson.toJson(responseDto));

	}

}
