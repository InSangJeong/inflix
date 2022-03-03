package kr.co.insang.CMS.repository;

import kr.co.insang.CMS.entity.Video;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends CrudRepository<Video, String> {
    List<Video> findAll();

    @Query(nativeQuery = true, value = "select * from video order BY RAND() LIMIT :maxCnt")
    List<Video> findRandomVideos(@Param("maxCnt")int cnt);


}
