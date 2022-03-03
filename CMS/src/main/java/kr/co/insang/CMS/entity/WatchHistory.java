package kr.co.insang.CMS.entity;

import kr.co.insang.CMS.dto.WatchHistoryDTO;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name="watchhistory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyid;
    @Column(nullable = false)
    private String userid;

    @ManyToOne
    @JoinColumn(name="videoid", nullable = false)
    private Video video;

    //@Column(nullable = false)
    //private String videoid;

    @Column(nullable = false)
    private String watchtime;


    public WatchHistory(String userid, String watchtime){
        this.userid=userid;
        this.watchtime=watchtime;
    }
    public WatchHistoryDTO toDTO(){
        return new WatchHistoryDTO(this.historyid, this.userid, this.video, this.watchtime);
    }

    static public List<WatchHistoryDTO> toWatchHistoyDtoList(List<WatchHistory> watchHistory){
        return watchHistory.stream()
                .map(WatchHistory::toDTO)
                .collect(Collectors.toList());
    }

}
