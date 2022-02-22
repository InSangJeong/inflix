package kr.co.insang.CMS.service;

import kr.co.insang.CMS.dto.watchHistoryDTO;
import kr.co.insang.CMS.entity.WatchHistory;
import kr.co.insang.CMS.repository.WatchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class HistoryService {
    WatchHistoryRepository watchHistoryRepository;


    @Autowired
    public HistoryService(WatchHistoryRepository watchHistoryRepository){
        this.watchHistoryRepository = watchHistoryRepository;
    }

    public List<watchHistoryDTO> getHistorybyUserid(String userid){
        try{
            List<WatchHistory> His = watchHistoryRepository.findAll();
            List<WatchHistory> History = watchHistoryRepository.findAllByuserid(userid);

            if(!History.isEmpty())
                return null;
            else
                return null;
        }catch (Exception e){
            return null;
        }

    }

}
