package com.seichigo.service;

import com.seichigo.domain.AnimeWorkVo;
import com.seichigo.mapper.AnimeWorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

}
