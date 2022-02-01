package kr.co.insang.CMS.entity;

import kr.co.insang.CMS.dto.VideoDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Video")
@NoArgsConstructor
@Getter
public class Video {

    @Id
    @Column(nullable = false)
    private String video_id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String runningtime;
    @Column(nullable = false)
    private String path_poster;
    @Column(nullable = false)
    private String path_source;

    @Builder
    public Video(String video_id, String title, String runningtime, String path_poster, String path_source){
        this.video_id = video_id;
        this.title =title;
        this.runningtime = runningtime;
        this.path_poster = path_poster;
        this.path_source = path_source;
    }

    public VideoDTO toDTO(){
        return VideoDTO.builder()
                .video_id(video_id)
                .title(title)
                .runningtime(runningtime)
                .path_poster(path_source)
                .path_source(path_source)
                .build();
    }


}
