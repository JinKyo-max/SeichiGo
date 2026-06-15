package com.seichigo.configue;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AdminPasswordGenerator {
	
	//관리자 계정 비밀번호 처리
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encoded = encoder.encode("1234");
		System.out.println("관리자 비밀번호 해시: " + encoded);

    }

}
