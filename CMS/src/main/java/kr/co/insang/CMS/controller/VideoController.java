package kr.co.insang.CMS.controller;

import kr.co.insang.CMS.dto.VideoDTO;
import kr.co.insang.CMS.dto.watchHistoryDTO;
import kr.co.insang.CMS.service.HistoryService;
import kr.co.insang.CMS.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@PropertySource("classpath:application-resource.properties")
@RestController
@RequestMapping("/cms")
public class VideoController {
    @Value("${resourcePath}")
    String resourcePath;

    VideoService videoService;
    HistoryService historyService;
    @Autowired
    public VideoController(VideoService videoService, HistoryService historyService){
        this.videoService = videoService;
        this.historyService = historyService;
    }

    @GetMapping("/moviepath/{videoid}")
    public ResponseEntity<Resource> getVideoPath(@PathVariable String videoid){
        try {
            String fileName = videoService.getVideoPathbyid(videoid);
            String path = resourcePath + fileName;
            Resource resource = new FileSystemResource(path);
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + "");
            headers.setContentType(MediaType.parseMediaType("video/mp4"));
            return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Resource>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/videolist")
    public List<VideoDTO> getVideoList(){
        return videoService.getAllVideos();
    }

    @GetMapping("/imagepath/{videoid}")
    public ResponseEntity<Resource> getImageByid(@PathVariable("videoid") String videoid) {
        try {
            String path = videoService.getImagePathByid(videoid);
            FileSystemResource resource = new FileSystemResource(resourcePath + path);
            if (!resource.exists()) {
                return new ResponseEntity<Resource>(HttpStatus.BAD_REQUEST);
                //throw new NotFoundImageException();
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(path);
            header.add("Content-Type", Files.probeContentType(filePath));
            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {

           return new ResponseEntity<Resource>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/history/{userid}")
    public ResponseEntity<List<watchHistoryDTO>> getWatchHistory(@PathVariable("userid") String userid){
        List<watchHistoryDTO> result = historyService.getHistorybyUserid(userid);

        return null;
    }
}
