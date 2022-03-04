package kr.co.insang.comment.entity;

import com.sun.istack.NotNull;
import kr.co.insang.comment.config.DateTimeConfig;
import kr.co.insang.comment.dto.CommentDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name="comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(length = 255)
    @NotNull
    private String userId;
    @Column
    @NotNull
    private Long videoId;
    @Column
    @NotNull
    private String createdAt;
    @Column(length = 255)
    @NotNull
    private String context;

    @PrePersist
    public void createdAt() {
        createdAt = DateTimeConfig.getNow();
    }

    public void updateByDTO(CommentDTO cdto){
        if(cdto.getUserId() != null)
            this.userId = cdto.getUserId();
        if(cdto.getVideoId() != null)
            this.videoId = cdto.getVideoId();
        if(cdto.getContext() != null)
            this.context = cdto.getContext();
    }

    public CommentDTO toDTO(){
        return new CommentDTO(this.commentId, this.userId, this.videoId, this.createdAt, this.context);
    }

    static public List<CommentDTO> toDtoList(List<Comment> comments){
        return comments.stream()
                .map(Comment::toDTO)
                .collect(Collectors.toList());
    }
}
