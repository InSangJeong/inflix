package kr.co.insang.CMS.entity;

import kr.co.insang.CMS.dto.watchHistoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private String watchingtate;
    @Column(nullable = false)
    private String watchtime;



    public watchHistoryDTO toDto(){
        return watchHistoryDTO.builder()
                .historyid(this.historyid)
                .userid(this.userid)
                .videoid(videoid)
                .watchingtate(watchingtate)
                .watchtime(watchtime)
                .build();
    }


}
