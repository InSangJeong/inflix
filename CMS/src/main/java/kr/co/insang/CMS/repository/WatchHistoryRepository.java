package kr.co.insang.CMS.repository;

import kr.co.insang.CMS.entity.WatchHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WatchHistoryRepository extends CrudRepository<WatchHistory, Long> {
    List<WatchHistory> findAll();
    List<WatchHistory> findAllByuserid(String userid);
}
