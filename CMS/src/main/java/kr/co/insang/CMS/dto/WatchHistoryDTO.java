package kr.co.insang.CMS.dto;

import kr.co.insang.CMS.entity.Video;
import kr.co.insang.CMS.entity.WatchHistory;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class WatchHistoryDTO {

    private Long historyid;
    private String userid;
    private Video video;
    private String watchtime;

    public WatchHistory toEntity(){
        return new WatchHistory(this.historyid, this.userid, this.video, this.watchtime);
    }

    public List<WatchHistory> toListEntity(List<WatchHistoryDTO> videoDTOs){
        return videoDTOs.stream()
                .map(WatchHistoryDTO::toEntity)
                .collect(Collectors.toList());
    }
}
