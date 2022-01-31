package kr.co.insang.CMS.service;

import kr.co.insang.CMS.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class VideoService {
    VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }

    public String getPath(String title){
        return "";
    }
}
