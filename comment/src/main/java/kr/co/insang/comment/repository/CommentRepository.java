package kr.co.insang.comment.repository;

import kr.co.insang.comment.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAllByuserId(String userId);

    List<Comment> findAllByvideoId(Long videoId);

    void deleteByuserId(String userId);

    void deleteByvideoId(Long videoId);
}
