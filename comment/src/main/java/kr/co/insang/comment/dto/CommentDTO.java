package kr.co.insang.comment.dto;


import kr.co.insang.comment.config.DateTimeConfig;
import kr.co.insang.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentDTO {
    private Long commentId;
    private String userId;
    private Long videoId;
    private String createdAt;
    private String context;

    public Comment toEntity(){
        if(this.createdAt==null || this.createdAt.equals(""))
            return new Comment(this.commentId, this.userId, this.videoId, DateTimeConfig.getNow(), this.context);
        else
            return new Comment(this.commentId, this.userId, this.videoId, this.createdAt, this.context);
    }

}
