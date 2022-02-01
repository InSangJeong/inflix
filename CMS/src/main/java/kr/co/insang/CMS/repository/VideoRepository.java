package kr.co.insang.CMS.repository;

import kr.co.insang.CMS.entity.Video;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoRepository extends CrudRepository<Video, String> {
    List<Video> findAll();
}
