package kr.co.insang.CMS.dto;

import kr.co.insang.CMS.entity.Video;
import kr.co.insang.CMS.entity.WatchHistory;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class WatchHistoryDTO {
    //Validation Annotation은 post 기준에서만 사용됩니다.
    @Null
    private Long historyid;
    @NotEmpty
    private String userid;
    @NotEmpty
    private Video video;
    @Null
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
