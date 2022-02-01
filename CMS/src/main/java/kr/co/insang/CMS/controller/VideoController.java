package kr.co.insang.CMS.controller;

import kr.co.insang.CMS.dto.VideoDTO;
import kr.co.insang.CMS.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cms-api-v1")
public class VideoController {

    VideoService videoService;
    @Autowired
    public VideoController(VideoService videoService){
        this.videoService = videoService;
    }

    @GetMapping("/MoviePath/{video_id}")
    public String getPath(@PathVariable String video_id){
        return videoService.getPath(video_id);
    }
    @GetMapping("/allmovies")
    public List<VideoDTO> getAllVideos(){
        return videoService.getAllVideos();
    }

}
