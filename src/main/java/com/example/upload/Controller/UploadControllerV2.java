package com.example.upload.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Controller
@RequestMapping("/servlet/v2")
public class UploadControllerV2 {

    private static final Logger log = LoggerFactory.getLogger(UploadControllerV2.class);
    @Value("${file.dir}")
    private String filepath;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String uploadFile(HttpServletRequest request) throws ServletException, IOException {
        String itemName = request.getParameter("itemName");
        log.info("itemName={}", itemName);

        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            Collection<String> headerNames = part.getHeaderNames();
            for (String headerName : headerNames) {
                log.info("headerName={} : header ={}", headerName, part.getHeader(headerName));
            }

            log.info("submittedFileName={}", part.getSubmittedFileName());
            log.info("size={}", part.getSize());

            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            log.info("body={}", body);

            if (StringUtils.hasText(part.getSubmittedFileName())) {
                String fullpath = filepath + part.getSubmittedFileName();
                part.write(fullpath);
            }

        }

        return "upload-form";
    }
}
