package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/files")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile
        ,@RequestParam("fileName") String fileName) throws IOException {
        System.out.println("file name: " + fileName);
        fileService.addFile(multipartFile, fileName);
        return "redirect:/home";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable(name = "fileId", required = true) int fileId){
        fileService.deleteFile(fileId);
        return "redirect:/home";
    }

    @GetMapping("/download/{fileId}")
    @ResponseBody
    public ResponseEntity<byte[]> downloadFile(@PathVariable(name = "fileId", required = true) int fileId){
        File fileData = fileService.getFileData(fileId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", fileData.getContentType());
        return new ResponseEntity<>(fileData.getFileData(), headers, HttpStatus.OK);
    }
}
