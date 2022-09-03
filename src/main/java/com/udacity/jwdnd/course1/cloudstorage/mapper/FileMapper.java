package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    ArrayList<File> getAllFile(int userId);

    @Insert("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int delete(int fileId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileData(int fileId);
}
