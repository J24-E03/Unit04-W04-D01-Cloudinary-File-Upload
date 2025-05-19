package com.dci.full_mvc.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    public final Cloudinary cloudinary;

//    method to upload our image
    public Map<String,String> uploadImage(MultipartFile file){

        long maxFileSize = 5 * 1024 * 1024; // 5MB limit

        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds the limit of 5MB.");
        }


//        allowed types to post to my image upload server
        Map<String, Object> options = new HashMap<>();
        options.put("resource_type", "image");
        options.put("allowed_formats", new String[]{"jpg", "png", "jpeg","pdf"});


//        uploading of the image to cloudinary
        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(file.getBytes(),options);
        } catch (IOException e) {
            System.out.println("Issue with upload");
            throw new RuntimeException(e);
        }



        String url = (String) uploadResult.get("secure_url");
        String publicId = (String) uploadResult.get("public_id");

        System.out.println(uploadResult.values());

        return Map.of(
                "url", url,
                "publicId", publicId
        );
    }


//    deletes the image from cloudinary
    public void deleteImage(String publicId){
        try {
            cloudinary.uploader().destroy(publicId,ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
