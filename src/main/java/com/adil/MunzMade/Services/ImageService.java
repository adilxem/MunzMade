package com.adil.MunzMade.Services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adil.MunzMade.Helper.Constants;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageService {

    @Autowired
    Cloudinary cloudinary;

    public String uploadImage(MultipartFile customerImage, String fileName) {



        try {

            byte[] data = new byte[customerImage.getInputStream().available()];

            customerImage.getInputStream().read(data);

            cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id", fileName));

            return this.getUrlFromPublicId(fileName);
        } 
        
        catch (IOException e) {

            e.printStackTrace();

            return null;
        }

    }

    public String getUrlFromPublicId(String publicId) {

        return cloudinary
        .url()
        .transformation(new Transformation<>()
        .width(Constants.customer_IMAGE_HEIGHT)
        .height(Constants.customer_IMAGE_HEIGHT)
        .crop(Constants.customer_IMAGE_CROP))
        .generate(publicId);
    }

}
