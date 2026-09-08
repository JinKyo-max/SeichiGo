package com.seichigo.domain;

import lombok.Data;

@Data
public class SeichiPlaceVo {
	// 성지 장소 자세한 정보

	private int place_id;// INT AUTO_INCREMENT PRIMARY KEY,장소 ID (PK)
	private int work_id;// anime_work,연결된 애니메이션 작품 ID (FK)
	private String name;// VARCHAR(255) NOT NULL,장소 이름
	private String address;// VARCHAR(255),장소 주소
	private String image;// VARCHAR(1024), -- 장소 사진
	private String realimage; // VARCHAR(1024), -- 실제 이미지
	private String description;// TEXT,
	private Double latitude;// -- 위도
	private Double longitude;// -- 경도

	// region 추가
//추가한 이유는 "주소(address) 중에서 특정 부분(지역명)을 따로 저장하기 위해서"
//address는 "東京都 渋谷区 道玄坂" 이런 식으로 길게 되어 있지?
//우리는 화면(view.html)에서 "東京都" 또는 "渋谷区" 같은 "지역명"만 따로 뽑아서 보여주고 싶어.
//그런데 address 전체를 매번 split해서 처리하면 복잡하고 성능에도 안 좋아.
//그래서 미리 Java 코드 안에서 region(지역명) 을 뽑아서 저장하는 거야.
//💬 요약
//➔ "주소에서 지역명만 미리 추출해서 저장해두려고 region을 만든 것이다."
	
    private String region;
}
