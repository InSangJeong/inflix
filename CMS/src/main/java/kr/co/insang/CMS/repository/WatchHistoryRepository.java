package kr.co.insang.CMS.repository;


import jdk.internal.org.jline.reader.History;
import kr.co.insang.CMS.entity.Video;
import kr.co.insang.CMS.entity.WatchHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WatchHistoryRepository extends CrudRepository<WatchHistory, String> {
    List<WatchHistory> findAll();
    List<WatchHistory> findAllByuserid(String userid);
}
