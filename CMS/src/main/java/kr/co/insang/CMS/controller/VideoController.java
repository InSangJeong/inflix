package kr.co.insang.CMS.controller;

import kr.co.insang.CMS.dto.VideoDTO;
import kr.co.insang.CMS.dto.WatchHistoryDTO;
import kr.co.insang.CMS.service.HistoryService;
import kr.co.insang.CMS.service.VideoService;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@PropertySource("classpath:application-resource.properties")
@Validated
@RestController
@RequestMapping("/cms")
public class VideoController{
    @Value("${resourcePath}")
    private String resourcePath;
    final static int maxVideoCount=10;

    private VideoService videoService;
    private HistoryService historyService;

    public VideoController(VideoService videoService, HistoryService historyService){
        this.videoService = videoService;
        this.historyService = historyService;

    }

    @GetMapping("/moviepath/{videoid}")
    public ResponseEntity<Resource> getVideoPath(@PathVariable("videoid") @NotEmpty @Length(min=1, max=255) String videoid){
        try {
            String fileName = videoService.getVideoPathbyid(videoid);
            StringBuilder path = new StringBuilder(resourcePath);
            path.append(fileName);
            Resource resource = new FileSystemResource(path.toString());
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            headers.setContentType(MediaType.parseMediaType("video/mp4"));
            return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Resource>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/videoname/{videoid}")
    public ResponseEntity<String> getVideoName(@NotEmpty @Length(min=1, max=255) @PathVariable("videoid") String videoid){
        VideoDTO video = videoService.getVideoInfoById(videoid);
        if(video!=null){
            return new ResponseEntity<String>(video.getTitle(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("fail", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/videoinfo/{videoid}")
    public ResponseEntity<VideoDTO> getVideoInfo(@NotEmpty @Length(min=1, max=255) @PathVariable("videoid") String videoid){
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

    @GetMapping("/randomvideos/{cntvideo}")
    public ResponseEntity<List<VideoDTO>> getRandomVideos(@NotNull @Range(min= 1, max=10)@PathVariable("cntvideo")int cntVideo){
        List<VideoDTO> videos = videoService.getRandomVideos(cntVideo);
        if(videos != null)
            return new ResponseEntity<List<VideoDTO>>(videos, HttpStatus.OK);

      //DB에 Video가 없음.
      return new ResponseEntity<List<VideoDTO>>(HttpStatus.NO_CONTENT);
    };


    @GetMapping("/imagepath/{videoid}")
    public ResponseEntity<Resource> getImageByid(@NotEmpty @Length(min=1, max=255) @PathVariable("videoid") String videoid) {
        try {
            String path = videoService.getImagePathByid(videoid);
            FileSystemResource resource = new FileSystemResource(resourcePath + path);
            if (!resource.exists()) {
                return new ResponseEntity<Resource>(HttpStatus.NO_CONTENT);
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = Paths.get(path);
            header.add("Content-Type", Files.probeContentType(filePath));
            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Resource>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/history/{userid}")
    public ResponseEntity<List<WatchHistoryDTO>> getWatchHistory(@NotEmpty @Length(min=1, max=255) @PathVariable("userid") String userid){
        List<WatchHistoryDTO> result = historyService.getHistorybyUserid(userid);
        if(result != null){
            return new ResponseEntity<List<WatchHistoryDTO>>(result, HttpStatus.OK);
        }
        return new ResponseEntity<List<WatchHistoryDTO>>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/history")
    public ResponseEntity<String> addHistory(@Valid @RequestBody WatchHistoryDTO historyDTO) {
        String result = historyService.createHistory(historyDTO);

        if(result.startsWith("historyid:")){
            return new ResponseEntity<String>(result,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>(result,HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/history/{historyid}")
    public ResponseEntity<String> deleteHistory(@NotEmpty @PathVariable("historyid") Long historyid){
        if(historyService.deleteHistorybyid(historyid)){
            return new ResponseEntity<String>("Success", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("fail to delete history...", HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/allhistory/{userid}")
    public ResponseEntity<String> deleteHistorybyUserid(@NotEmpty  @Length(min=1, max=255) @PathVariable("userid") String userid){
        if(historyService.deleteHistorybyUserid(userid)){
            return new ResponseEntity<String>("Success", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("fail to delete History.", HttpStatus.ACCEPTED);
        }
    }
}
