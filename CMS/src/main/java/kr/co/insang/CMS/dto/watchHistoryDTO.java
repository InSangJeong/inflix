package kr.co.insang.CMS.dto;

import kr.co.insang.CMS.entity.Video;
import kr.co.insang.CMS.entity.WatchHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class watchHistoryDTO {

    private String historyid;
    private String userid;
    private String videoid;
    private String watchingtate;
    private String watchtime;

    public WatchHistory toEntity(){
        return WatchHistory.builder()
                .historyid(historyid)
                .userid(userid)
                .videoid(videoid)
                .watchingtate(watchingtate)
                .watchtime(watchtime)
                .build();
    }

}
