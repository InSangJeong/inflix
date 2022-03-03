package kr.co.insang.CMS.entity;

import kr.co.insang.CMS.dto.VideoDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
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

    //지금은 단방향으로 충분.
    //@OneToMany(mappedBy = "video")
    //private List<WatchHistory> histories = new ArrayList<>();


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
        return new VideoDTO(this.videoid, this.title, this.runningtime, this.pathposter, this.pathsource, this.genre);
    }
    static public List<VideoDTO> toVideoDtoList(List<Video> video){
        return video.stream()
                .map(Video::toDTO)
                .collect(Collectors.toList());
    }

}
