package com.seichigo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.seichigo.domain.AnimeWorkVo;
import com.seichigo.domain.SeichiPlaceVo;

@Mapper
// SQL 실행 담당, 진짜 DB랑 대화하는 담당자
//테이블에 직접 INSERT / SELECT / UPDATE 하는 사람
public interface AnimeWorkMapper {

	//관리자가 아니메 등록하는 매퍼
	 @Insert("INSERT INTO anime_work (title, description, thumbnail, release_year) " +
	            "VALUES (#{title}, #{description}, #{thumbnail}, #{release_year})")
	    void insertAnimeWork(AnimeWorkVo vo);
	 
	 //모든 아니메 리스트 가져오기
	 @Select("SELECT * FROM anime_work ORDER BY work_id asc")
	    List<AnimeWorkVo> selectAllAnimeWorks();
	 
	 // 특정 작품(work_id)로 1개 가져오기
	 @Select("SELECT * FROM anime_work WHERE work_id = #{id}")
	 AnimeWorkVo selectWorkById(int id);

	 // 해당 작품에 연결된 성지 장소 리스트 가져오기
	 @Select("SELECT * FROM seichi_place WHERE work_id = #{workId}")
	 List<SeichiPlaceVo> selectPlacesByWorkId(int workId);
	 
	 // write_place 세부정보 글쓰기 
	 @Insert("INSERT INTO seichi_place (work_id, name, address, image, description, latitude, longitude, realimage) " +
		        "VALUES (#{work_id}, #{name}, #{address}, #{image}, #{description}, #{latitude}, #{longitude}, #{realimage})")
		void insertSeichiPlace(SeichiPlaceVo placeVo);

	 // viewdetail 세부정보 보기
	 @Select("SELECT * FROM seichi_place WHERE place_id = #{id}")
	 SeichiPlaceVo selectPlaceById(int id);


}
