package kr.co.insang.CMS.entity;

import kr.co.insang.CMS.dto.WatchHistoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private Long historyid;
    @Column(nullable = false)
    private String userid;
    @Column(nullable = false)
    private String videoid;
    @Column(nullable = false)
    private String watchtime;


    public WatchHistory(String userid, String videoid, String watchtime){
        this.userid=userid;
        this.videoid=videoid;
        this.watchtime=watchtime;
    }
    public WatchHistoryDTO toDTO(){
        return WatchHistoryDTO.builder()
                .historyid(this.historyid)
                .userid(this.userid)
                .videoid(videoid)
                .watchtime(watchtime)
                .build();
    }

    static public List<WatchHistoryDTO> toWatchHistoyDtoList(List<WatchHistory> watchHistory){
        return watchHistory.stream()
                .map(WatchHistory::toDTO)
                .collect(Collectors.toList());
    }

}
