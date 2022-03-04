package kr.co.insang.comment.service;

import kr.co.insang.comment.dto.CommentDTO;
import kr.co.insang.comment.entity.Comment;
import kr.co.insang.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public List<CommentDTO> getCommentByUserid(String userid) {
        List<Comment> comments = commentRepository.findAllByuserId(userid);
        if(comments!=null){
            if(comments.size()!=0){
                List<CommentDTO> CommentDTOs = Comment.toDtoList(comments);
                return CommentDTOs;
            }
        }
        return null;
    }

    public List<CommentDTO> getCommentByVideoID(Long videoid) {
        List<Comment> comments = commentRepository.findAllByvideoId(videoid);
        if(comments!=null){
            if(comments.size()!=0){
                List<CommentDTO> CommentDTOs = Comment.toDtoList(comments);
                return CommentDTOs;
            }
        }
        return null;
    }

    @Transactional
    public boolean saveComment(CommentDTO commentDTO) {
        try{
            Comment comment = commentDTO.toEntity();
            commentRepository.save(comment);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Transactional
    public boolean updateComment(CommentDTO commentDTO) {
        try{
            if(commentDTO.getCommentId() != null){
                Optional<Comment> oComment = commentRepository.findById(commentDTO.getCommentId());
                if(oComment.isPresent()) {
                    Comment comment = oComment.get();
                    comment.updateByDTO(commentDTO);
                    commentRepository.save(comment);
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    @Transactional
    public boolean deleteCommentByCommenID(Long commentid) {
        try{
            commentRepository.deleteById(commentid);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Transactional
    public boolean deleteCommentByUserID(String userid) {
        try{
            commentRepository.deleteByuserId(userid);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Transactional
    public boolean deleteCommentByVideoID(Long videoid) {
        try{
            commentRepository.deleteByvideoId(videoid);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
