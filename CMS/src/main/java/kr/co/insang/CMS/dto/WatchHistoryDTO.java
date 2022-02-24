package kr.co.insang.CMS.dto;

import kr.co.insang.CMS.entity.WatchHistory;
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
public class WatchHistoryDTO {

    private Long historyid;
    private String userid;
    private String videoid;
    private String watchtime;

    public WatchHistory toEntity(){
        return WatchHistory.builder()
                .historyid(historyid)
                .userid(userid)
                .videoid(videoid)
                .watchtime(watchtime)
                .build();
    }
    public List<WatchHistory> toListEntity(List<WatchHistoryDTO> videoDTOs){
        return videoDTOs.stream()
                .map(WatchHistoryDTO::toEntity)
                .collect(Collectors.toList());
    }
}
