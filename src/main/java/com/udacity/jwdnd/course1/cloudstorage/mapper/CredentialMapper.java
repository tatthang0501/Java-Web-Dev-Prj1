package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    ArrayList<Credential> getAllCredential(int userId);

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userId) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialId = #{credentialId}")
    int update(String url, String username, byte[] key, String password, int credentialId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int delete(int credentialId);
}
