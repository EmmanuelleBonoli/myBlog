package org.wildcodeschool.myBlog.service;

import org.springframework.stereotype.Service;
import org.wildcodeschool.myBlog.dto.ImageDTO;
import org.wildcodeschool.myBlog.exception.ResourceNotFoundException;
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
        Image image = imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'image avec l'id " + id + " n'a pas été trouvé"));
        return ImageDTO.mapFromEntity(image);
    }

    public ImageDTO createImage(Image image) {
        Image savedImage = imageRepository.save(image);
        return ImageDTO.mapFromEntity(savedImage);
    }

    public ImageDTO updateImage(Long id, Image imageRequest) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'image avec l'id " + id + " n'a pas été trouvé"));
        image.setUrl(imageRequest.getUrl());

        Image updatedImage = imageRepository.save(image);
        return ImageDTO.mapFromEntity(updatedImage);
    }

    public boolean deleteImage(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'image avec l'id " + id + " n'a pas été trouvé"));

        imageRepository.delete(image);
        return true;
    }
}
