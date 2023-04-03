package com.study.servlet.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.study.servlet.entity.User;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("GET");
		String userSearch = request.getParameter("username");

		Gson gson = new Gson();

		User user1 = new User("jsh", "1234", "JSH", "hhgg0017@gmail.com");
		User user2 = new User("1jsh", "1234", "1JSH", "hhgg00@gmail.com");
		User user3 = new User("2jsh", "1234", "2JSH", "hhgg0@gmail.com");
		List<User> userList = new ArrayList<>();
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);

		response.setContentType("application/json;charset=UTF-8");

		PrintWriter out = response.getWriter();

		userList.forEach(user -> {
			if (user.getUsername().equals(userSearch)) {
				String userJson = gson.toJson(user);
				System.out.println(userJson);
				out.println(userJson);
			}
		});
		
		User findUser = null;
		
		for(User user : userList) {
			if(user.getUsername().equals(userSearch)) {
				findUser = user;
				break;
			}
		}
		
		String userJson = gson.toJson(findUser);
		
		out.println(userJson);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("POST");
		request.setCharacterEncoding("UTF-8");

		Gson gson = new Gson();

		ServletInputStream inputStream = request.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		String requestJson = bufferedReader.lines().collect(Collectors.joining("\n"));

		System.out.println(requestJson);

		JsonObject responseData = new JsonObject();

		try {
			User newUser = gson.fromJson(requestJson, User.class);
			System.out.println(newUser.toString());
			responseData.addProperty("success", "true");
			responseData.addProperty("username", newUser.getUsername());
			responseData.addProperty("password", newUser.getPassword());
			responseData.addProperty("name", newUser.getName());
			responseData.addProperty("email", newUser.getEmail());
		} catch (NullPointerException e) {
			responseData.addProperty("statusCode", "400");
			responseData.addProperty("errorMessage", "not Found");
		} catch (Exception e) {
			// TODO: handle exception
		}

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(responseData.toString());
		
		
		}

}
