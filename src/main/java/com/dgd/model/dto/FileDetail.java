package com.dgd.model.dto;

import com.dgd.util.MultipartUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDetail {

    private String id;
    private String path;
    private String format;

    public static FileDetail multiPartOf(MultipartFile multipartFile){
        final String filed = MultipartUtil.createFileId();
        final String format = MultipartUtil.getFormat(multipartFile.getContentType());

        return FileDetail.builder()
                .id(filed)
                .format(format)
                .path(MultipartUtil.createPath(filed,format))
                .build();
    }
}
