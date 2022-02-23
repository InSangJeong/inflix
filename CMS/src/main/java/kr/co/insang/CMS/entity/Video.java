package kr.co.insang.CMS.entity;

import kr.co.insang.CMS.dto.VideoDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Video")
@NoArgsConstructor
@Getter
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String videoid;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String runningtime;
    @Column(nullable = false)
    private String pathposter;
    @Column(nullable = false)
    private String pathsource;
    @Column(nullable = false)
    private String genre;

    @Builder
    public Video(String videoid, String title, String runningtime, String pathposter, String pathsource, String genre){
        this.videoid = videoid;
        this.title =title;
        this.runningtime = runningtime;
        this.pathposter = pathposter;
        this.pathsource = pathsource;
        this.genre = genre;
    }

    public VideoDTO toDTO(){
        return VideoDTO.builder()
                .videoid(videoid)
                .title(title)
                .runningtime(runningtime)
                .pathposter(pathposter)
                .pathsource(pathsource)
                .genre(genre)
                .build();
    }
    static public List<VideoDTO> toVideoDtoList(List<Video> video){
        return video.stream()
                .map(Video::toDTO)
                .collect(Collectors.toList());
    }

}
