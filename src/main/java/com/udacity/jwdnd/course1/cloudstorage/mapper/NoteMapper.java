package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userId = #{userid}")
    ArrayList<Note> getAllNote(int userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userId) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteId = #{noteid}")
    int update(String notetitle, String notedescription, int noteid);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteid}")
    int delete(int noteid);
}
