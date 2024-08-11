package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid= #{userid} ")
    List<File> findByUserid(Integer userid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND filename = #{filename}")
    File findByUseridAndFilename(Integer userid, String filename);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int save(File file);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File findByFileId(Integer fileId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteById(int fileId);


}
