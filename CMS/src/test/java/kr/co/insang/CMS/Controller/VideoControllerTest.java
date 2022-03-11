package kr.co.insang.CMS.Controller;

import kr.co.insang.CMS.controller.VideoController;
import kr.co.insang.CMS.service.HistoryService;
import kr.co.insang.CMS.service.VideoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(VideoController.class)
public class VideoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoService videoService;
    @MockBean
    private HistoryService historyService;

    @Test
    @DisplayName("Get Video Path")
    public void getHistoryByUsername() throws Exception {
        //given
        String failID3="notexistUser";
        String successID="test";

        //when //then
        mockMvc.perform(get("/cms/history/" + failID3)).andExpect(status().isNoContent());
        mockMvc.perform(get("/cms/history/" + successID)).andExpect(status().isOk());

    }
}
