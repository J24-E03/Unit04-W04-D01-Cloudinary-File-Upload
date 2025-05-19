package com.dci.full_mvc.controller;


import com.dci.full_mvc.model.ImageMetaData;
import com.dci.full_mvc.repository.ImageMetaDataRepository;
import com.dci.full_mvc.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageUploadService imageUploadService;
    private final ImageMetaDataRepository imageMetaDataRepository;


    @GetMapping("/upload")
    public String showUploadForm(){
        return "upload/upload-form";
    }


    @PostMapping("/upload")
    public String handleUpload(@RequestParam("image") MultipartFile image, Model model){

        System.out.println("In controller method");
        Map<String,String> uploadedData =  imageUploadService.uploadImage(image);

        String imageUrl = uploadedData.get("url");
        String publicId = uploadedData.get("publicId");

        ImageMetaData imageObj = new ImageMetaData();
        imageObj.setImageUrl(imageUrl);
        imageObj.setPublicId(publicId);

        System.out.println(imageObj);

        imageMetaDataRepository.save(imageObj);

        model.addAttribute("imageUrl", imageUrl);


        return "upload/upload-result";
    }
}
