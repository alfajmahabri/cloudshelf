package com.example.cloudshelf.controller;

import com.example.cloudshelf.service.Download;
import com.example.cloudshelf.service.Head;
import com.example.cloudshelf.service.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class AwsController {

    @Autowired
    private Download download;

    @Autowired
    private Upload upload;

    @Autowired
    private Head head;

    @GetMapping("/download/{key}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String key){
      Resource resource = download.getObject(key);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_OCTET_STREAM)
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
              .body(resource);
    }

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFile(){
        upload.uploadFile("e.jpg","C:/Users/mahab/Downloads/e.png");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles(){
        List<String> files = head.listObjects();
        return ResponseEntity.ok(files);
    }

}
