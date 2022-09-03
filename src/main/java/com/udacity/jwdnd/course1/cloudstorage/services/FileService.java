package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class FileService {
    private FileMapper fileMapper;

    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public void addFile(MultipartFile uploadFile, String fileName) throws IOException {
        File file = new File();
        file.setFileName(fileName);
        file.setFileSize(uploadFile.getSize()+"");
        file.setContentType(uploadFile.getContentType());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  authentication.getPrincipal().toString();
        file.setUserId(userService.getUserIdByUsername(username));

        file.setFileData(uploadFile.getBytes());
        System.out.println(file.toString());
        fileMapper.insert(file);
    }

    public ArrayList<File> getAllFile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  authentication.getPrincipal().toString();
        Integer userId = userService.getUserIdByUsername(username);
        if(userId == 0){
            return new ArrayList<>();
        }
        return fileMapper.getAllFile(userId);
    }
    public boolean deleteFile(int fileId){
        int deletedFile = fileMapper.delete(fileId);
        if(deletedFile == 1){
            return true;
        }
        return  false;
    }

    public File getFileData(int fileId){
        return fileMapper.getFileData(fileId);
    }
}
