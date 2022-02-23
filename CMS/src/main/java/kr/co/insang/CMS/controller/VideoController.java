package kr.co.insang.CMS.controller;

import kr.co.insang.CMS.dto.VideoDTO;
import kr.co.insang.CMS.dto.WatchHistoryDTO;
import kr.co.insang.CMS.entity.Video;
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
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Resource> getVideoPath(@PathVariable("videoid") String videoid){
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

    @GetMapping("/videoname/{videoid}")
    public ResponseEntity<String> getVideoName(@PathVariable("videoid") String videoid){
        VideoDTO video = videoService.getVideoInfoById(videoid);
        if(video!=null){
            return new ResponseEntity<String>(video.getTitle(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("fail", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/videoinfo/{videoid}")
    public ResponseEntity<VideoDTO> getVideoInfo(@PathVariable("videoid") String videoid){
        VideoDTO video = videoService.getVideoInfoById(videoid);
        if(video!=null){
            return new ResponseEntity<VideoDTO>(video, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<VideoDTO>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<List<WatchHistoryDTO>> getWatchHistory(@PathVariable("userid") String userid){
        List<WatchHistoryDTO> result = historyService.getHistorybyUserid(userid);
        if(result != null){
            return new ResponseEntity<List<WatchHistoryDTO>>(result, HttpStatus.OK);
        }
        return new ResponseEntity<List<WatchHistoryDTO>>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/history/{historyid}")
    public ResponseEntity<String> deleteHistory(@PathVariable("historyid") String historyid){
        if(historyService.deleteHistorybyid(historyid)){
            return new ResponseEntity<String>("Success", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("fail to delete history...", HttpStatus.ACCEPTED);
        }
    }
}
