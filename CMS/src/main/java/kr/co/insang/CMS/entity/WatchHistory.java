package kr.co.insang.CMS.entity;

import kr.co.insang.CMS.dto.WatchHistoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "watchhistory")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class WatchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String historyid;
    @Column(nullable = false)
    private String userid;
    @Column(nullable = false)
    private String videoid;
    @Column(nullable = false)
    private String watchingstate;
    @Column(nullable = false)
    private String watchtime;



    public WatchHistoryDTO toDTO(){
        return WatchHistoryDTO.builder()
                .historyid(this.historyid)
                .userid(this.userid)
                .videoid(videoid)
                .watchingstate(watchingstate)
                .watchtime(watchtime)
                .build();
    }

    static public List<WatchHistoryDTO> toWatchHistoyDtoList(List<WatchHistory> watchHistory){
        return watchHistory.stream()
                .map(WatchHistory::toDTO)
                .collect(Collectors.toList());
    }

}
