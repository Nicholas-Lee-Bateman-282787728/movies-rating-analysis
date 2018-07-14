package io.anhkhue.akphim.controllers.apis;

import io.anhkhue.akphim.services.CrawlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CrawlerController {

    private final CrawlService crawlService;

    public CrawlerController(CrawlService crawlService) {
        this.crawlService = crawlService;
    }

    @PostMapping("/admin/crawler")
    public ResponseEntity handleCrawlers(@RequestParam String status) {
        boolean turnedOn = status.equals("true");
        crawlService.controlCrawlers(turnedOn);
        if (CrawlService.isCrawling) {
            return ResponseEntity.status(HttpStatus.OK).body("Hệ thống đang thu thập dữ liệu");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Đã tạm ngưng việc thu thập dữ liệu<br/>");
        }
    }
}
