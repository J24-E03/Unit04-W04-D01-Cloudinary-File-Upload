package com.dci.full_mvc.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    public final Cloudinary cloudinary;

//    method to upload our image
    public Map<String,String> uploadImage(MultipartFile file){

        Map<String,Object> options = ObjectUtils.asMap(
                "resource_type","images",
                "allowed_formats", new String[]{"jpg","png","jpeg"}
        );

        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(file,options);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        String url = (String) uploadResult.get("secure_url");
        String publicId = (String) uploadResult.get("public_id");

        return Map.of(
                "url",url,
                "publicId",publicId
        );


    }
}
