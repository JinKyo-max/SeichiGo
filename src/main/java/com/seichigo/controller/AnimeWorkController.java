package com.seichigo.controller;

import com.seichigo.domain.AnimeWorkVo;
import com.seichigo.service.AnimeWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/anime")
public class AnimeWorkController {

    private final String uploadDir = "C:/upload/"; // 이미지 저장 경로

    @Autowired
    private AnimeWorkService service;

    // 등록 폼
    //브라우저에서 /anime/write 경로로 접근했을 때 등록 폼 화면을 보여줌.
    @GetMapping("/write")
    public String writeForm(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("writer", principal.getName());
        }
        return "portfolio/write"; // write.html을 사용할 경우
    }

    //등록 처리
    //write.html 폼에서 작성된 데이터를 POST 방식으로 전송하면 이 메서드가 처리함
    @PostMapping("/write")
    public String writeSubmit(
            AnimeWorkVo vo,
            @RequestParam("thumbnailFile") MultipartFile file) {

        String imagePath = null;

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.copy(file.getInputStream(), filePath);
                imagePath = "/upload/" + fileName; // DB에는 경로 저장
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        vo.setThumbnail(imagePath); // DB에 저장될 썸네일 경로
        service.register(vo);

        return "redirect:/anime/seichi-list";

        // 등록 후 목록으로 이동
        //@GetMapping("/anime/list") 를 처리하는 메서드를 다시 실행하라는 뜻
    }
    
    //아니메 리스트 페이지 출력
    @GetMapping("/seichi-list")
    public String seichiList(Model model) {
    	//List<AnimeWorkVo> list = service.getAllWorks();
    	//System.out.println("🔥 등록된 작품 수: " + list.size());
        model.addAttribute("animeList", service.getAllWorks());
        return "portfolio/list";
    }
    
    //작품 클릭 시 상세페이지로 이동
    @GetMapping("/view")
    public String view(@RequestParam("id") int id, Model model) {
        AnimeWorkVo vo = service.getWorkById(id);
        model.addAttribute("vo", vo);
        return "portfolio/view"; // view.html 출력
    }
    
    @GetMapping("/view")
    public String viewWork(@RequestParam("id") int id, Model model) {
        AnimeWorkVo work = service.getWorkById(id);
        List<SeichiPlaceVo> placeList = service.getPlacesByWorkId(id);

        model.addAttribute("work", work);
        model.addAttribute("places", placeList);
        return "portfolio/view";
    }


    
}
