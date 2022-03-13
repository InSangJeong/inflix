package kr.co.insang.CMS.Controller;

import kr.co.insang.CMS.controller.VideoController;
import kr.co.insang.CMS.dto.VideoDTO;
import kr.co.insang.CMS.repository.VideoRepository;
import kr.co.insang.CMS.repository.WatchHistoryRepository;
import kr.co.insang.CMS.service.HistoryService;
import kr.co.insang.CMS.service.VideoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(VideoController.class)
@ContextConfiguration(classes = VideoController.class)
@TestPropertySource(properties = {  //@Value로 Properties 값 받아오는거 Test에 적용하려면 이렇게 추가.
        "resourcePath=../../../videos/"
})
public class VideoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoService videoService;
    @MockBean
    private HistoryService historyService;


    @Test
    @DisplayName("Get Video by video ID")
    public void getVideo() throws Exception {
        //given
        String failID3="Fail";
        String successID="2";

        //when (Mockito Service 이므로 결과값을 임의로 준다. Service Test는 따로 단위테스트를 진행했음을 기대함.)
        given(videoService.getVideoPathbyid(failID3)).willReturn("none");
        given(videoService.getVideoPathbyid(successID)).willReturn("/Concert.mp4");

        // then
        //익셉션으로 잘 빠져나오는지 확인하려면 아래처럼 사용.
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            mockMvc.perform(get("/cms/moviepath/" + failID3)).andExpect(status().isNoContent());
        });
        mockMvc.perform(get("/cms/moviepath/" + successID)).andExpect(status().isOk());

    }
    @Test
    @DisplayName("Get Video Name by video ID")
    public void GetVideoName() throws Exception {
        String Success = "2";
        String FailNotFound = "aaa";

        given(videoService.getVideoInfoById(Success)).willReturn(new VideoDTO());
        given(videoService.getVideoInfoById(FailNotFound)).willReturn(null);

        mockMvc.perform(get("/cms/videoname/" + Success)).andExpect(status().isOk());
        mockMvc.perform(get("/cms/videoname/" + FailNotFound)).andExpect(status().isNotFound());


    }

    @Test
    @DisplayName("Get Video DTO by video ID")
    public void GetVideoInfo() throws Exception {
        String Success = "2";
        String FailNotFound = "aaa";

        given(videoService.getVideoInfoById(Success)).willReturn(new VideoDTO("temp","temp","temp","temp","temp","temp"));
        given(videoService.getVideoInfoById(FailNotFound)).willReturn(null);

        mockMvc.perform(get("/cms/videoinfo/" + Success)).andExpect(status().isOk())
                .andExpect(jsonPath("$.videoid", notNullValue()));
        mockMvc.perform(get("/cms/videoinfo/" + FailNotFound)).andExpect(status().isNotFound());


    }
    @Test
    @DisplayName("Get Videos Randomly")
    public void GetRandomVideo() throws Exception{
        //given
        String Success = "5";
        String Fail1 = "-1";
        String Fail2 = "15";//Max is 10.
        List<VideoDTO> mockVideos = new ArrayList<>();
        mockVideos.add(new VideoDTO("temp","","","","",""));
        //when
        given(videoService.getRandomVideos(Integer.parseInt(Success))).
                willReturn(mockVideos);
        given(videoService.getRandomVideos(3)).willReturn(null);
        //then
        mockMvc.perform(get("/cms/randomvideos/"+Integer.parseInt(Success))).andExpect(status().isOk());
        Assertions.assertThrows(Exception.class, () -> {
                    mockMvc.perform(get("/cms/randomvideos/" + Fail1)).andExpect(status().isBadRequest());
                    mockMvc.perform(get("/cms/randomvideos/" + Fail2)).andExpect(status().isBadRequest());
                });
        mockMvc.perform(get("/cms/randomvideos/3")).andExpect(status().isNoContent());

    }
    @Test
    @DisplayName("Get Imgae Path by VideoID")
    public void GetImagePathByVideoID() throws Exception{
        String Success = "5";
        String Fail1 = "No Exist.";

        given(videoService.getImagePathByid(Success)).willReturn("/snap/Concert.png");
        given(videoService.getImagePathByid(Fail1)).willReturn("none");

        mockMvc.perform(get("/cms/imagepath/"+Success)).andExpect(status().isOk());
        mockMvc.perform(get("/cms/imagepath/"+Fail1)).andExpect(status().isNoContent());
    }



/*
    @Test
    @DisplayName("Get History")
    public void getHistoryByUsername() throws Exception {
        //given
        String failID3="notexistUser";
        String successID="test";

        //when //then
        mockMvc.perform(get("/cms/history/" + failID3)).andExpect(status().isNoContent());
        mockMvc.perform(get("/cms/history/" + successID)).andExpect(status().isOk());

    }*/
}
