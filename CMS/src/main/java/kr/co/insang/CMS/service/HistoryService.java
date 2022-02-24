package kr.co.insang.CMS.service;

import kr.co.insang.CMS.dto.WatchHistoryDTO;
import kr.co.insang.CMS.entity.WatchHistory;
import kr.co.insang.CMS.repository.WatchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static kr.co.insang.CMS.entity.WatchHistory.toWatchHistoyDtoList;

@Service
@Transactional
public class HistoryService {
    WatchHistoryRepository watchHistoryRepository;


    @Autowired
    public HistoryService(WatchHistoryRepository watchHistoryRepository){
        this.watchHistoryRepository = watchHistoryRepository;
    }

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
            //WatchHistory result = watchHistoryRepository.save(history.toEntity());
            //id는 자동 생성.
            WatchHistory entity =new WatchHistory(history.getUserid(), history.getVideoid(), LocalDateTime.now().toString());
            WatchHistory result = watchHistoryRepository.save(entity);

            return "historyid:" + result.getHistoryid().toString();
        }catch (Exception e){
            return e.toString();
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
}
