package com.adil.MunzMade.Validators;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2; // 2MB

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        // to check the if the file is empty ---> user must upload an image
        
        // if (file == null || file.isEmpty()) {

        //     context.disableDefaultConstraintViolation();

        //     context.buildConstraintViolationWithTemplate("Error: File cannot be empty, upload an image!").addConstraintViolation();

        //     return false;
        // }


        if (file.getSize() > MAX_FILE_SIZE) {

            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate("Error: File size should not exceed 2MB!").addConstraintViolation();

            return false;
        }


        // to check the resolution of the image file:

        // try {

        //     BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

        //     if(bufferedImage.getHeight() > 1080 || bufferedImage.getWidth() > 1920) {

        //         context.disableDefaultConstraintViolation();

        //         context.buildConstraintViolationWithTemplate("Error: Image resolution should be withing 1920x1080");

        //     }
        // } 
        
        // catch (IOException e) {
                        
        //     e.printStackTrace();
        // }

        return true;

    }
}
