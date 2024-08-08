package com.example.demo.helloworld;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/helloworld")
@RequiredArgsConstructor

public class HelloWorldController {

    @GetMapping("/sayHellow")
    public ResponseEntity<byte[]> sayHello() throws IOException {
        String rangeHeader = "bytes=1000-1999";
        // Get the total size of the video file
        String path = "./src/main/resources/storage/som.mp4";

        File file = new File(path);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
        buf.read(bytes, 0, bytes.length);
        buf.close();
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header("Content-Type", "video/" + "mp4")
                .header("Accept-Ranges", "bytes")
                .header("Content-Length", "" + bytes.length)
                .header("Content-Range", "bytes " + 0 + "-" +
                        bytes.length + "/" + bytes.length)
                .body(bytes);
    }
}
