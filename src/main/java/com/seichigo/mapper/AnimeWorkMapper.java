package com.seichigo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.seichigo.domain.AnimeWorkVo;

@Mapper
public interface AnimeWorkMapper {

	//관리자가 아니메 등록하는 매퍼
	 @Insert("INSERT INTO anime_work (title, description, thumbnail, release_year) " +
	            "VALUES (#{title}, #{description}, #{thumbnail}, #{release_year})")
	    void insertAnimeWork(AnimeWorkVo vo);
	 
	 @Select("SELECT * FROM anime_work ORDER BY work_id asc")
	    List<AnimeWorkVo> selectAllAnimeWorks();
	 
	 @Select("SELECT * FROM anime_work WHERE work_id = #{id}")
	 AnimeWorkVo selectWorkById(int id);

	 @Select("SELECT * FROM seichi_place WHERE work_id = #{workId}")
	 List<SeichiPlaceVo> selectPlacesByWorkId(int workId);

}
