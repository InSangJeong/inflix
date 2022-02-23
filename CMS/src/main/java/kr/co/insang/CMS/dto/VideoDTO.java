package kr.co.insang.CMS.dto;

import kr.co.insang.CMS.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoDTO {
    private String videoid;
    private String title;
    private String runningtime;
    private String pathposter;
    private String pathsource;
    private String genre;

    public Video toEntity(){
        return Video.builder()
                .videoid(videoid)
                .title(title)
                .runningtime(runningtime)
                .pathposter(pathposter)
                .pathsource(pathsource)
                .genre(genre)
                .build();
    }

    static public List<Video> toListEntity(List<VideoDTO> videoDTOs){
        return videoDTOs.stream()
                .map(VideoDTO::toEntity)
                .collect(Collectors.toList());
    }

}
