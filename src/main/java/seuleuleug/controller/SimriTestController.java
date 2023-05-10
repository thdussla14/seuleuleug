package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seuleuleug.domain.crawling.SimriTestDto;
import seuleuleug.service.SimriTestService;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/simritest")
public class SimriTestController {
    @Autowired
    private SimriTestService simriTestService;

    @GetMapping("")
    public List<SimriTestDto> getInfo() throws IOException {
        List<SimriTestDto> result = simriTestService.getInfo();
        // log.info("result : "+result);
        return result;
    }
}
