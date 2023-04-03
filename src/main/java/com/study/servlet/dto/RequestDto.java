package com.study.servlet.dto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

public class RequestDto<T> {

	
	//제네릭은 생성될 때 타입이 결정된다 그래서 static에서 쓸려면 아래의 방법으로 씀.
	public static <T> T convertRequestBody(HttpServletRequest request, Class<?> c) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		String requestBody = bufferedReader.lines().collect(Collectors.joining("\n"));
		
		Gson gson = new Gson();
		
		T object = (T) gson.fromJson(requestBody, c);
		return object;
	}

}
