package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE USERID = #{userId}")
    List<File> getFilesByUserId(int userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void uploadFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    void deleteFile(String fileId);

    @Select("SELECT * FROM FILES WHERE FILEID = #{fileId}")
    File getFilesByFileId(int fileId);

    @Select("SELECT * FROM FILES WHERE FILENAME = #{fileName}")
    List<File> getFilesByFileName(String fileName);
}
