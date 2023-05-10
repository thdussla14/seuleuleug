package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seuleuleug.domain.challenges.ChallengesDto;
import seuleuleug.service.ChallengesService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/challenge")
public class ChallengesController {
    @Autowired
    ChallengesService challengesService;

    @GetMapping("")
    public List<ChallengesDto> get(){
        return challengesService.get();
    }

    @PostMapping("")
    public boolean post(ChallengesDto challengesDto){
        log.info("Posting challenge"+challengesDto);
        return challengesService.post(challengesDto);
    }

    @PutMapping("")
    public boolean put(@RequestBody ChallengesDto challengesDto){
        return challengesService.put(challengesDto);
    }

    @DeleteMapping("")
    public boolean delete(int chno){
        return challengesService.delete(chno);
    }
}
