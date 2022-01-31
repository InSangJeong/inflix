package kr.co.insang.CMS.controller;

import kr.co.insang.CMS.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms-api-v1")
public class VideoController {

    VideoService videoService;
    @Autowired
    public VideoController(VideoService videoService){
        this.videoService = videoService;
    }

    @GetMapping("/MoviePath/{title}")
    public String getPath(@PathVariable String title){
        return videoService.getPath(title);
    }
}
