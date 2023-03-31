package com.study.servlet.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

//POST와 PUT 요청을 할때는 json으로
//요청을 받을때는 모두 json으로
//API는 하나의 기능 또는 기능의 집합체

@WebServlet("/user")
public class UserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	//GET 요청을 받을수 있음
	//url에는 무조건 GET요청이다.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		Gson gson = new Gson();
		
		System.out.println("GET 요청");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		
		
		Map<String,String> userMap = new HashMap<>();
		
		userMap.put("name", name);
		userMap.put("phone", phone);
		
		String userJson = gson.toJson(userMap);
		System.out.println(userJson);
		
//		System.out.println(request.getParameter("name"));
		/**
		 * 1. 주소창, src, href, replace 등으로 요청할 수 있음. 
		 * 2. 데이터를 전달하는 방법(Query String) 방법 -> http(s)://서버주소 : 포트/요청메세지?key=value&key=value
		 * 3. 
		 */
//		System.out.println(response.getCharacterEncoding());
		
		
//		response.addHeader("Content-Type", "text/html;charset=UTF-8");//Content-Type에 따라서 문자열로 볼건지 HTML코드로 볼건지 판단함.
//		response.addHeader("test", "testdata");
//		response.setContentType("text/html;charset=UTF-8"); //HTML 응답
		
		response.setContentType("application/json;charset=UTF-8");
		
	    PrintWriter out = response.getWriter();
//	    out.println("이름: " + name);
//	    out.println("연락처: " + phone);
	    
	    out.println(userJson);
	}
	
	//POST 요청을 받을 수 있음
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST 요청");
		request.setCharacterEncoding("UTF-8");
		
//		System.out.println(request.getParameter("address"));
		
		Gson gson = new Gson();
		
		ServletInputStream inputStream = request.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		
		//방법 1 json을 받는 방법
//		AtomicReference<String> jsonAtomic = new AtomicReference<>("");
//		bufferedReader.lines().forEach(line -> {
//			jsonAtomic.getAndUpdate(t -> t + line);
//		});
//		
//		String json = jsonAtomic.get().replaceAll(" ","");
//		System.out.println(json);
		
		//방법 2
		String json = "";
		String line = null;
		while((line = bufferedReader.readLine()) != null) {
			json += line;
		}
		json = json.replaceAll("    ", "");
		System.out.println(json);
		
		Map<String,String> jsonMap = gson.fromJson(json, Map.class);
		System.out.println(jsonMap);
		
		/**
		 *  1.<form action = "http://localhost:8080/servlet_Study_20230331/user" method="post">
		 *  	<input name="key" value="value">
		 *  	<button type="submit">요청</button>
		 *  </form> -> x-www-form-urlencoded
		 *  
		 */
		
	}
	/*
	 * CarInfo servlet
	 * mapping url: /car
	 * method: get, post
	 * 
	 * GET
	 * Map: id, model
	 * {id = 1, model = 쏘나타}
	 * {id = 2, model = k5}
	 * {id = 3, model = sm6}
	 * 
	 * 요청 데이터: id
	 * String id = getParameter("id")
	 * 
	 * 응답 데이터
	 * {"id":"2", "model":"k5"}
	 * 
	 * POST
	 * {"id"="1", "model":"쏘나타"}
	 * {"id"="2", "model":"K5"}
	 * {"id"="3", "model":"SM6"}
	 * 
	 * console
	 * id(1):쏘나타
	 * id(2):K5
	 * id(2):SM6
	 * 
	 * */
	
	//POST 요청을 하는 방법 3가지
	//1. form태그에 post로 변경하여 요청
	//2. 비동기 통신
	//3. testtool(postman)
}
