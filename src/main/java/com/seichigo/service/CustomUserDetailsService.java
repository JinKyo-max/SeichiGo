package com.seichigo.service;

import com.seichigo.domain.UserVo;
import com.seichigo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo user = userMapper.findByUsername(username); // username=id와 매핑되게 만들기
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
            user.getId(),                             // ID
            user.getPassword(),                       // 암호화된 비밀번호
            Collections.singletonList(
            	    new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase())
            	)
           );//권한 
            //대문자 변환 (toUpperCase())
    }
}
