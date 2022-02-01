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
    private String video_id;
    private String title;
    private String runningtime;
    private String path_poster;
    private String path_source;

    public Video toEntity(){
        return Video.builder()
                .video_id(video_id)
                .title(title)
                .runningtime(runningtime)
                .path_poster(path_source)
                .path_source(path_source)
                .build();
    }

    public List<Video> toListEntity(List<VideoDTO> videoDTOs){
        return videoDTOs.stream()
                .map(VideoDTO::toEntity)
                .collect(Collectors.toList());
    }

}
