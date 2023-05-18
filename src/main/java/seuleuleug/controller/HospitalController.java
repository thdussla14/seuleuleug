package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seuleuleug.domain.hospital.HospitalDto;
import seuleuleug.domain.hospital.PageDto;
import seuleuleug.service.HospitalService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

    // 병원리스트 출력
    @GetMapping("/list")
    public PageDto get(PageDto pageDto) {
        PageDto result = hospitalService.get(pageDto);
        return result;
    }
    // 제휴병원 리스트 출력
    @GetMapping("/joinlist")
    public List<HospitalDto> joinList ( ) {
        return hospitalService.joinList();
    }
    // 병원 리스트 출력
    @GetMapping("/alllist")
    public List<HospitalDto> alllist () {
        return hospitalService.alllist();
    }

    // 병원 제휴 상태 변경
    @PutMapping("/change")
    public boolean changestate (@RequestBody HospitalDto hospitalDto) { return hospitalService.changestate(hospitalDto) ;}

}
