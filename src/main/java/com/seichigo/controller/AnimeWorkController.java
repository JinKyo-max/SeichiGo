package com.seichigo.controller;

import com.seichigo.domain.AnimeWorkVo;
import com.seichigo.domain.SeichiPlaceVo;
import com.seichigo.service.AnimeWorkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/anime")
public class AnimeWorkController {

    private final String uploadDir = "C:/upload/"; // 이미지 저장 폴더

    @Autowired
    private AnimeWorkService service;

    /*** 1. 애니 작품 등록 폼 출력 ***/
    @GetMapping("/write")
    public String writeForm(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("writer", principal.getName());
        }
        return "portfolio/write"; // portfolio/write.html로 이동
    }

    /*** 2. 애니 작품 등록 처리 ***/
    @PostMapping("/write")
    public String writeSubmit(AnimeWorkVo vo, @RequestParam("thumbnailFile") MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.copy(file.getInputStream(), filePath);
                vo.setThumbnail("/upload/" + fileName); // ★ DB에는 /upload/경로 포함
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        service.register(vo); // 서비스로 등록
        return "redirect:/anime/list"; // 등록 후 목록으로 이동
       //redirect 쓰는 이유 :
        //사용자는 등록 후 GET /anime/list 를 새로 시작하므로 새로고침 시 중복 등록 방지 가능
    }

    /*** 3. 애니 작품 목록 출력 ***/
    @GetMapping("/list")
    public String seichiList(Model model) {
        model.addAttribute("animeList", service.getAllWorks());
        return "portfolio/list"; // portfolio/list.html
    }

    /*** 4. 애니 작품 상세 페이지 출력 ***/
    @GetMapping("/view")
    public String viewWork(@RequestParam("work_id") int id, Model model) {
        AnimeWorkVo work = service.getWorkById(id);
        List<SeichiPlaceVo> places = service.getPlacesByWorkId(id);
        
     // JSON 변환
        ObjectMapper mapper = new ObjectMapper();
        String placesJson = "";
        try {
            placesJson = mapper.writeValueAsString(places);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
     //이걸 추가한 이유는,
     //"SeichiPlaceVo 리스트 places 하나하나를 돌면서 address를 split해서, region에 세팅해주기 위해서
     
        //이 코드의 동작 순서
    // places 리스트를 for문으로 하나하나 꺼낸다.
    // address가 null이 아니면 split(" ") 으로 공백 기준으로 쪼갠다.
    // 쪼갠 결과에서 [0]번째 (즉, 제일 앞 단어) 를 region에 저장한다.
    // 결론: "전체 주소 중에서 첫 번째 지역명만 따로 뽑아서 place 객체의 region에 저장하는 작업이다."   
        
        // 지역(region) 필드 추출 처리
        for (SeichiPlaceVo place : places) {
            if (place.getAddress() != null && place.getAddress().contains(" ")) {
                String[] parts = place.getAddress().split(" ");
                place.setRegion(parts[0]); // 예: 東京都
            }

            // 위도/경도가 null이거나 문자열일 경우 대비
            if (place.getLatitude() == null || place.getLongitude() == null) {
                place.setLatitude(0.0);
                place.setLongitude(0.0);
            }
        }

        model.addAttribute("work", work);
        model.addAttribute("places", places);
        model.addAttribute("placesJson", placesJson);
        return "portfolio/view"; // portfolio/view.html
    }

    /*** 5. 장소 등록 폼 출력 (관리자만) ***/
    @GetMapping("/place-write")
    public String placeWriteForm() {
        return "portfolio/write_place"; // portfolio/write_place.html
    }

    /*** 6. 장소 등록 처리 (관리자만) ***/
    @PostMapping("/place-write")
    public String placeWriteSubmit(SeichiPlaceVo vo,
        @RequestParam("imageFile") MultipartFile imageFile,
        @RequestParam("realimageFile") MultipartFile realImageFile) {

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.copy(imageFile.getInputStream(), filePath);
                vo.setImage("/upload/" + fileName);
            }

            if (realImageFile != null && !realImageFile.isEmpty()) {
                String realFileName = UUID.randomUUID() + "_" + realImageFile.getOriginalFilename();
                Path realFilePath = Paths.get(uploadDir + realFileName);
                Files.copy(realImageFile.getInputStream(), realFilePath);
                vo.setRealimage("/upload/" + realFileName);  
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 에러 발생 시 등록 페이지로 다시 리다이렉트하거나 예외 처리 추가 가능
            return "redirect:/anime/place-write?error=true";
        }

        service.registerPlace(vo);
        return "redirect:/anime/view?work_id=" + vo.getWork_id();
    }

    /*** 7. 좌표 클릭시 상세 페이지로 이동 ***/
    @GetMapping("/detail")
    public String viewdetail(@RequestParam("place_id") int id, Model model) {
        SeichiPlaceVo place = service.getPlaceById(id); // 장소 1개 조회
        model.addAttribute("place", place);
        return "portfolio/viewdetail";
    }
    
    
}
