package org.wildcodeschool.myBlog.service;

import org.springframework.stereotype.Service;
import org.wildcodeschool.myBlog.dto.ImageDTO;
import org.wildcodeschool.myBlog.model.Image;
import org.wildcodeschool.myBlog.repository.ImageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<ImageDTO> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return images.stream().map(ImageDTO::mapFromEntity).collect(Collectors.toList());
    }

    public ImageDTO getImageById(Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            return null;
        }
        return ImageDTO.mapFromEntity(image);
    }

    public ImageDTO createImage(Image image) {
        Image savedImage = imageRepository.save(image);
        return ImageDTO.mapFromEntity(savedImage);
    }

    public ImageDTO updateImage(Long id, Image imageRequest) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            return null;
        }
        image.setUrl(imageRequest.getUrl());

        Image updatedImage = imageRepository.save(image);
        return ImageDTO.mapFromEntity(updatedImage);
    }

    public boolean deleteImage(Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            return false;
        }

        imageRepository.delete(image);
        return true;
    }
}
