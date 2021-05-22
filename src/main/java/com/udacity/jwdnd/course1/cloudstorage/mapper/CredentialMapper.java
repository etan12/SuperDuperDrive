package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE USERID = #{userId}")
    List<Credential> getCredentialsByUserId(int userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    void createCredential(Credential createdCredential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password}, userid = #{userId}" +
            "WHERE credentialid = #{credentialId}")
    void updateCredential(Credential createdCredential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void deleteCredential(String credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE CREDENTIALID = #{credentialId}")
    Credential getCredentialByCredentialId(Integer credentialId);
}
