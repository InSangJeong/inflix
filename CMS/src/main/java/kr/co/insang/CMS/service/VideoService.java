package kr.co.insang.CMS.service;

import kr.co.insang.CMS.dto.VideoDTO;
import kr.co.insang.CMS.entity.Video;
import kr.co.insang.CMS.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static kr.co.insang.CMS.entity.Video.toVideoDtoList;

@Service
@Transactional
public class VideoService {

    private VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }


    public String getVideoPathbyid(String videoid){
        Optional<Video> video = videoRepository.findById(videoid);
        if(video.isPresent())
            return video.get().getPathsource();
        else
            return "none";
    }

    public List<VideoDTO> getAllVideos(){
        List<Video> videos = videoRepository.findAll();
        return toVideoDtoList(videos);
    }

    public List<VideoDTO> getRandomVideos(int cnt){

        //영상을 다 불러오는게아니라 사이즈만 가져오는 방법이 있을듯한데...
        int maxCntVideos = videoRepository.findAll().size();

        if(0< cnt && cnt < maxCntVideos){
            List<Video> videos = videoRepository.findRandomVideos(cnt);
            return toVideoDtoList(videos);
        }
        return null;
    }

    public VideoDTO getVideoInfoById(String videoid){
        Optional<Video> video = videoRepository.findById(videoid);
        if(video.isPresent())
            return video.get().toDTO();
        else
            return null;
    }

    public String getImagePathByid(String videoid){
        Optional<Video> video = videoRepository.findById(videoid);
        if(video.isPresent())
            return video.get().getPathposter();
        else
            return "none";
    }
}
