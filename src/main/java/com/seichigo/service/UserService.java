package com.seichigo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.seichigo.domain.UserVo;
import com.seichigo.mapper.UserMapper;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void membersave(UserVo userVo) {
		userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
		userMapper.insertUser(userVo);
	}
	
	public boolean checkIdExists(String username) {
		return userMapper.checkIdExists(username) > 0;
	}
	
	public UserVo findByUsername(String username) {
		return userMapper.findByUsername(username);
	}
	

}
