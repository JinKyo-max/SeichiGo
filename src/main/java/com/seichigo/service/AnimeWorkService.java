package com.seichigo.service;

import com.seichigo.domain.AnimeWorkVo;
import com.seichigo.domain.SeichiPlaceVo;
import com.seichigo.mapper.AnimeWorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// 컨트롤러와 DB의 중간 다리
public class AnimeWorkService {

    @Autowired
    private AnimeWorkMapper mapper;

    public void register(AnimeWorkVo vo) {
        mapper.insertAnimeWork(vo);
    }

    public List<AnimeWorkVo> getAllWorks() {
        return mapper.selectAllAnimeWorks();
    }
    
    public AnimeWorkVo getWorkById(int id) {
        return mapper.selectWorkById(id);
    }

    public List<SeichiPlaceVo> getPlacesByWorkId(int workId) {
        return mapper.selectPlacesByWorkId(workId);
    }
    
    //write_place
    public void registerPlace(SeichiPlaceVo placeVo) {
        mapper.insertSeichiPlace(placeVo);
    }
    
    //viewdetail 
    public SeichiPlaceVo getPlaceById(int id) {
        return mapper.selectPlaceById(id);
    }


}
