package kr.co.insang.CMS.service;

import kr.co.insang.CMS.dto.VideoDTO;
import kr.co.insang.CMS.entity.Video;
import kr.co.insang.CMS.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VideoService {
    VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }

    public String getPath(String video_id){
        Optional<Video> video = videoRepository.findById(video_id);
        if(video.isPresent())
            return video.get().getPath_source();
        else
            return "none";
    }

    public List<VideoDTO> getAllVideos(){
        List<Video> videos = videoRepository.findAll();
        return toListDTO(videos);
    }

    public List<VideoDTO> toListDTO(List<Video> videos){
        return videos.stream()
                .map(Video::toDTO)
                .collect(Collectors.toList());
    }
}