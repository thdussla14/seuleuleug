package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import seuleuleug.domain.crawling.SimriTestDto;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SimriTestService {
    private String url = "";

    @PostConstruct
    public List<SimriTestDto> getInfo() throws IOException {
        url = "http://aiselftest.com/";
        List<SimriTestDto> simriTestDtoList = new ArrayList<>();

        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("#sec1 .row li");
        //log.info("Simritest : "+elements);

        for(Element element : elements){
            //log.info("Simritest : "+element);
            //log.info("Simritest stitle : "+element.select("a").html());
            //log.info("Simritest sulr : "+element.select("a").attr("abs:href"));

            simriTestDtoList.add(
                SimriTestDto.builder()
                        .stitle(element.select("a").html())
                        .surl(element.select("a").attr("abs:href"))
                        .build()
            );
        }
        return simriTestDtoList;
    }


}
