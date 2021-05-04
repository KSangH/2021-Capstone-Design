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

    public long imageUpload(MultipartFile file) throws IOException {
        Image image = Image.builder().mediatype(file.getContentType()).imagefile(file.getBytes()).build();
        image = imageRepository.save(image);
        return image.getImageid();
    }

    public Image getImageFile(long imageid) {
        Image image = imageRepository.findById(imageid).get();
        return image;
    }

}
