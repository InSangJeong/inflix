package kr.co.insang.CMS.service;

import kr.co.insang.CMS.dto.WatchHistoryDTO;
import kr.co.insang.CMS.entity.Video;
import kr.co.insang.CMS.entity.WatchHistory;
import kr.co.insang.CMS.repository.VideoRepository;
import kr.co.insang.CMS.repository.WatchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static kr.co.insang.CMS.entity.WatchHistory.toWatchHistoyDtoList;

@Service
@Transactional
public class HistoryService {
    @Autowired
    WatchHistoryRepository watchHistoryRepository;

    @Autowired
    VideoRepository videoRepository;

    public List<WatchHistoryDTO> getHistorybyUserid(String userid){
        try{
            List<WatchHistory> history = watchHistoryRepository.findAllByuserid(userid);


            if(!history.isEmpty()){
                return toWatchHistoyDtoList(history);
            }
            else
                return null;
        }catch (Exception e){
            return null;
        }

    }

    public String createHistory(WatchHistoryDTO history){
        try{
            Optional<Video> video = videoRepository.findById(history.getVideo().getVideoid());
            if(video.isPresent()){

                history.setVideo(video.get());

                ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH-mm-ss");
                history.setWatchtime(nowSeoul.format(formatter));

                WatchHistory historyentity = history.toEntity();
                WatchHistory result = watchHistoryRepository.save(historyentity);
                return "historyid:" + result.getHistoryid().toString();
            }
            return "Video of History is null!";
        }catch (Exception e){
            return "Video of History is null!  " + e.toString()  ;
        }
    }
    public boolean deleteHistorybyid(String historyid){
        try{
            watchHistoryRepository.deleteById(Long.parseLong(historyid));
            return true;
        }catch (Exception e){
            //logger e..
            return false;
        }
    }
    public boolean deleteHistorybyUserid(String userid){
        try{
            //삭제한 엔티티수가 나옴.
            Long debug = watchHistoryRepository.deleteByuserid(userid);
            return true;
        }catch (Exception e){
            //logger e..
            return false;
        }
    }
}
