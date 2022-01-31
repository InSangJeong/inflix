package kr.co.insang.stream.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;

@Service
public class StreamService {

    public StreamingResponseBody stream(String filePath) throws Exception {
        File file = new File(filePath);
        final InputStream is = new FileInputStream(file);
        return os -> {
            readAndWrite(is, os);
        };
    }

    private void readAndWrite(final InputStream is, OutputStream os) throws IOException {
        byte[] data = new byte[2048];
        int read = 0;
        while ((read = is.read(data)) > 0) {
            os.write(data, 0, read);
        }
        os.flush();
    }



}
