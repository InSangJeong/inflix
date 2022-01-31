package kr.co.insang.stream.controller;

import kr.co.insang.stream.service.RESTService;
import kr.co.insang.stream.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/stream-api-v1")
public class StreamController {

    private StreamService streamService;
    private RESTService restService;


    @Autowired
    public StreamController(StreamService streamService, RESTService restService){
        this.streamService = streamService;
        this.restService = restService;
    }

    @GetMapping("/video/{subject}")
    public StreamingResponseBody StreamVideo(@PathVariable String subject) throws Exception {
        // 1. Movie Service에게 영상 경로 요청하기
        String Path = restService.GetMoviePath(subject);

        if(Path.equals("None") || Path.equals(""))
        {
            ;//영상 없음.
        }
        else
        {
            // 2. 영상 스트리밍.(스트리밍 방식 일단 영상 다운로드하게 했는데 대용량일땐..?프론트엔드 개발할때 더상세히 정해야할듯.)
            return streamService.stream(Path);
        }
        return null;
    }
}
