package com.study.servlet.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/car")
public class CarInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> carMap = new HashMap<>();

		String id = request.getParameter("id");
		String findModel = carMap.get(id);

		carMap.put("1", "쏘나타");
		carMap.put("2", "K5");
		carMap.put("3", "SM6");

		JsonObject responseData = new JsonObject();

		if (findModel == null) {
			responseData.addProperty("statusCode", "400");
			responseData.addProperty("errorMessage", "not Found");
		} else {
			responseData.addProperty("id", id);
			responseData.addProperty("model", findModel);
		}

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(responseData.toString());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("POST 요청");
		request.setCharacterEncoding("UTF-8");

		ServletInputStream inputStream = request.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		String requestJson = bufferedReader.lines().collect(Collectors.joining("\n"));
		Gson gson = new Gson();
		System.out.println(requestJson);
		List<Map<String, String>> requestData = gson.fromJson(requestJson, List.class);

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		requestData.forEach(carMap -> {
			System.out.println("id(" + carMap.get("id") + ")" + carMap.get("model"));
			out.println("id(" + carMap.get("id") + "): " + carMap.get("model"));
		});
	}

}
