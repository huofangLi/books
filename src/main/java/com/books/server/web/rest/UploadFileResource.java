package com.books.server.web.rest;

import com.aliyun.oss.OSS;
import com.books.server.config.ApplicationProperties;
import com.books.server.service.dto.FileDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Api("文件上传接口")
public class UploadFileResource {

    @Autowired
    private ApplicationProperties applicationProper;

    @Autowired
    private OSS ossClient;

    @PostMapping("/file/upload")
    @ApiOperation(value = "上传至本地文件夹，需在yml文件中配置本地文件夹目录")
    public ResponseEntity<List<FileDTO>> uploadFile(@RequestParam("file") MultipartFile[] files) throws IOException {
        List<FileDTO> fileDTOList = new ArrayList<>();
        for (MultipartFile file : files) {
            String newFileName = UUID.randomUUID().toString();
            newFileName = newFileName + getExtraFileName(file.getOriginalFilename());
            file.transferTo(new File(applicationProper.getFileLocal().getPath() + newFileName));
            fileDTOList.add(new FileDTO(file.getOriginalFilename(), applicationProper.getFileLocal().getPrefix() + newFileName));
        }
        return ResponseEntity.ok(fileDTOList);
    }


    @PostMapping("/file/upload/aliyun")
    @ApiOperation(value = "上传至阿里云服务器")
    public ResponseEntity<List<FileDTO>> uploadAliyun(@RequestParam("file") MultipartFile[] files) throws IOException {
        List<FileDTO> fileDTOList = new ArrayList<>();
        for (MultipartFile file : files) {
            String newFileName = UUID.randomUUID().toString();
            newFileName = newFileName + getExtraFileName(file.getOriginalFilename());
            ossClient.putObject(applicationProper.getOss().getBucketName(), newFileName, file.getInputStream());
            fileDTOList.add(new FileDTO(file.getOriginalFilename(), applicationProper.getOss().getPrefix() + newFileName));
        }
        return ResponseEntity.ok(fileDTOList);
    }

    private String getExtraFileName(String fileName) {
        int i = fileName.lastIndexOf(".");
        if (i != -1) {
            return fileName.substring(i);
        }
        return fileName;
    }
}
