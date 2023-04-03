package com.study.servlet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.study.servlet.dto.ResponseDto;
import com.study.servlet.entity.User;
import com.study.servlet.service.UserService;
import com.study.servlet.service.UserServiceImpl;

@WebServlet("/auth/signin")
public class SingIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService;
	private Gson gson;

	public SingIn() {
		userService = UserServiceImpl.getInstance();
		gson = new Gson();
	}

	// 인증은 모두 POST로 요청한다.(GET으로 하면 주소창에 노출이된다.
	// 인증은 BasicLogin(기본적으로 http가 제공), formLogin(form태그에 submit으로 제공), JWT 3가지가 있다.
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = userService.getUser(username);

		response.setContentType("application/json;utf-8");
		PrintWriter out = response.getWriter();

		if (user == null) {
			// 로그인 실패 1(아이디 찾을 수 없음)
			ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>(400, "사용자 인증 실패", false);
			out.println(gson.toJson(responseDto));
			return;
		}

		if (!user.getPassword().equals(password)) {
			ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>(400, "사용자 인증 실패", false);
			out.println(gson.toJson(responseDto));
			return;
		}

		// 로그인 성공
		// 인증된 사용자를 기억하기 위해 session을 사용한다.
		// JWT의 key값은 Client가 들고 있고 session은 Server가 들고 있다.
		HttpSession session = request.getSession();
		session.setAttribute("AuthenticationPrincipal", user.getUserId());
		
		ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>(200, "로그인 성공", true);
		out.println(gson.toJson(responseDto));
	}

}
