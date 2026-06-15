package com.seichigo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.seichigo.domain.UserVo;

@Mapper
public interface UserMapper extends WebMvcConfigurer {
	
	//Mybatis Annotation 방식
	
	//회원저장
	@Insert("insert into users (id,password,nickname,email) values(#{id},#{password},#{nickname},#{email})")
	void insertUser(UserVo userVo);
	
	//아이디 사용여부
	@Select("select count(*) from users where id=#{id}")
	int checkIdExists(@Param("id") String id);
	
	@Select("select id,password,nickname,email,role,created_at from users where id=#{id}")
	UserVo findByUsername(String id);

	
	

}
