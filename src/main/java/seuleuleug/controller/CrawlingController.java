package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seuleuleug.domain.crawling.CrawlingDto;
import seuleuleug.service.CrawlingService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/crawling")
public class CrawlingController {
    @Autowired
    CrawlingService crawlingService;

    @GetMapping("")
    public List<CrawlingDto> getInfo() throws Exception {
        List<CrawlingDto> infoList = crawlingService.getInfo();
        return infoList;
    }

    @GetMapping("detail")
    public List<CrawlingDto> getInfoDetail() throws Exception {
        List<CrawlingDto> infoList = crawlingService.getInfoDetail();
        return infoList;
    }

}
