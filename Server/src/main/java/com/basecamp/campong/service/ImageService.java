package com.basecamp.campong.service;

import com.basecamp.campong.domain.Image;
import com.basecamp.campong.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    /**
     * image테이블에 이미지파일을 저장
     *
     * @param file : DB에 저장할 이미지파일
     * @return 등록된 이미지 id
     * @throws IOException
     */
    public long imageUpload(MultipartFile file) throws IOException {
        Image image = Image.builder().mediatype(file.getContentType()).imagefile(file.getBytes()).build();
        image = imageRepository.save(image);
        return image.getImageid();
    }

    /**
     * 이미지파일 다운로드
     *
     * @param imageid 다운로드받을 이미지파일의 id
     * @return 이미지파일
     */
    public Image getImageFile(long imageid) {
        Image image = imageRepository.findById(imageid).get();
        return image;
    }

}
