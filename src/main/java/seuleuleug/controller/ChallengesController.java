package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seuleuleug.domain.challenges.ChallengeResultsDto;
import seuleuleug.domain.challenges.ChallengesDto;
import seuleuleug.domain.hospital.PageDto;
import seuleuleug.service.ChallengesService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/challenge")
public class ChallengesController {
    @Autowired
    ChallengesService challengesService;

    @GetMapping("")
    public PageDto get(PageDto pageDto){
        return challengesService.get(pageDto);
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

    // 관리자페이지에서 목록 뽑기
    @GetMapping("/admin")
    public List<ChallengesDto> getList( ){return challengesService.getList();}

    // 게시물 1개 보기
    @GetMapping("/detail")
    public ChallengesDto getDetail(@RequestParam int chno ){return challengesService.getDetail(chno);}

    // 오늘 챌린지 참여 목록
    @GetMapping("/results")
    public List<ChallengeResultsDto> getResult(@RequestParam int chno){log.info("chno"+chno); return challengesService.getResult(chno);}

    // 로그인한 사람의 참여 보기
    @GetMapping("/resultsinfo")
    public List<ChallengeResultsDto> getResultByMno(@RequestParam int chno , String memail   ){log.info("getResultByMno : "+chno+memail);return challengesService.getResultByMno(chno,memail);}

    @GetMapping("/results/admin")
    public List<ChallengeResultsDto> getResultAdmin(@RequestParam int chno){return challengesService.getResultAdmin(chno);}

    @PostMapping("/results")
    public boolean postResult(ChallengeResultsDto challengeResultsDto){log.info("challengeResultsDto : "+challengeResultsDto); return challengesService.postResult(challengeResultsDto);}

    @PutMapping("/results")
    public boolean putResult(@RequestBody ChallengeResultsDto challengeResultsDto){log.info("sno"+challengeResultsDto);return challengesService.putResult(challengeResultsDto);}
}
