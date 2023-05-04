package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import seuleuleug.domain.crawling.CrawlingDto;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CrawlingService {
    private static String URL = "";

    @PostConstruct
    public List<CrawlingDto> getInfo() throws IOException {
        URL = "https://www.gov.kr/portal/rcvfvrSvc/svcFind/svcSearchAll?query=%EC%A0%95%EC%8B%A0&startCount=0";
        List<CrawlingDto> newsList = new ArrayList<>();

        Document document = Jsoup.connect(URL).get();
        Elements contentNum = document.select(".guide-desc").select("span");
        int cnt = 0;
        for(Element content : contentNum){
            if(!content.html().equals("\'정신\'")){
                cnt = Integer.parseInt(content.html().replace("개",""))/12;
            }
            //log.info("cnt : "+cnt);
        }

        for(int i = 0 ; i<=cnt ; i++){
            URL = "https://www.gov.kr/portal/rcvfvrSvc/svcFind/svcSearchAll?query=%EC%A0%95%EC%8B%A0&startCount="+12*i;
            document = Jsoup.connect(URL).get();

            Elements contents = document.select("#viewpanel22 .card-box");
            //log.info("crawligcontents"+contents);

            for(Element content : contents){
                //log.info("Crawling : " + content);
                //log.info("Crawling card-tag : " + content.select("em").html());
                //log.info("Crawling card_head : " + content.select("a").html().replace("<span class=\"keyword\">정신</span>","정신"));
                //log.info("Crawling card_head_href : " + content.select("a").attr("abs:href"));
                //log.info("Crawling card_head_info : " + content.select("p").html().replace("<span class=\"keyword\">정신</span>","정신"));
                //log.info("Crawling card-tag : " + Arrays.toString(  content.select(".card-text").html().split("\n") ) );

                CrawlingDto dto = CrawlingDto
                        .builder()
                        .card_tag(content.select("em").html())
                        .card_head(content.select("a").html().replace("<span class=\"keyword\">정신</span>","정신"))
                        .card_head_href(content.select("a").attr("abs:href"))
                        .card_head_info(content.select("p").html().replace("<span class=\"keyword\">정신</span>","정신"))
                        .card_body1(content.select(".card-text").html().split("\n")[0])
                        .card_body2(content.select(".card-text").html().split("\n")[1])
                        .card_body3(content.select(".card-text").html().split("\n")[2])
                        .card_body4(content.select(".card-text").html().split("\n")[3])
                        .card_body5(content.select(".card-text").html().split("\n")[4])
                        .build();
                newsList.add(dto);
                //log.info("dto : " + dto);
            }
            log.info("newsList : " + newsList.size());
        }
        return newsList;
    }

    public List<CrawlingDto> getInfoDetail() throws Exception {
        List<CrawlingDto> infoList = new ArrayList<>();
        return infoList;
    }

}
