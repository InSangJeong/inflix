package kr.co.insang.CMS.service;

import kr.co.insang.CMS.dto.WatchHistoryDTO;
import kr.co.insang.CMS.entity.WatchHistory;
import kr.co.insang.CMS.repository.WatchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public boolean deleteHistorybyid(String historyid){
        try{
            watchHistoryRepository.deleteById(historyid);
            return true;
        }catch (Exception e){
            //logger e..
            return false;
        }

    }
}
