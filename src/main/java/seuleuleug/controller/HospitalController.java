package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
