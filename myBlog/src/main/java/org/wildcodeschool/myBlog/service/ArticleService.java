package org.wildcodeschool.myBlog.service;

import org.springframework.stereotype.Service;
import org.wildcodeschool.myBlog.dto.ArticleCreationDTO;
import org.wildcodeschool.myBlog.dto.ArticleDTO;
import org.wildcodeschool.myBlog.dto.AuthorContributionArticleCreationDTO;
import org.wildcodeschool.myBlog.dto.ImageArticleCreationDTO;
import org.wildcodeschool.myBlog.exception.ResourceNotFoundException;
import org.wildcodeschool.myBlog.model.*;
import org.wildcodeschool.myBlog.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final AuthorRepository authorRepository;
    private final ArticleAuthorRepository articleAuthorRepository;

    public ArticleService(
            ArticleRepository articleRepository,
            CategoryRepository categoryRepository,
            ImageRepository imageRepository,
            AuthorRepository authorRepository,
            ArticleAuthorRepository articleAuthorRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.authorRepository = authorRepository;
        this.articleAuthorRepository = articleAuthorRepository;
    }

    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(ArticleDTO::mapFromEntity).collect(Collectors.toList());
    }

    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'article avec l'id " + id + " n'a pas été trouvé"));
        return ArticleDTO.mapFromEntity(article);
    }

    public ArticleDTO createArticle(ArticleCreationDTO articleCreateDTO) {
        Article article = ArticleCreationDTO.convertToEntity(articleCreateDTO);

        // Gestion de la catégorie
        if (articleCreateDTO.categoryId() != null) {
            Category category = categoryRepository.findById(articleCreateDTO.categoryId()).orElseThrow(() -> new ResourceNotFoundException("La catégorie de l'article en création n'a pas été trouvé."));
            article.setCategory(category);
        }

        // Gestion des images
        if (articleCreateDTO.images() != null && !articleCreateDTO.images().isEmpty()) {
            List<Image> validImages = new ArrayList<>();
            for (ImageArticleCreationDTO image : articleCreateDTO.images()) {
                if (image.id() != null) {
                    Image existingImage = imageRepository.findById(image.id()).orElse(null);
                    if (existingImage != null) {
                        validImages.add(existingImage);
                    } else {
                        throw new ResourceNotFoundException("L'image de l'article en création n'a pas été trouvée.");
                    }
                } else {
                    Image imageToCreate = ImageArticleCreationDTO.convertToEntity(image);
                    Image savedImage = imageRepository.save(imageToCreate);
                    validImages.add(savedImage);
                }
            }
            article.setImages(validImages);
        }

        Article savedArticle = articleRepository.save(article);

        // Gestion des auteurs
        if (articleCreateDTO.authors() != null) {
            for (AuthorContributionArticleCreationDTO articleAuthorDTO : articleCreateDTO.authors()) {

                Author author = authorRepository.findById(articleAuthorDTO.authorId()).orElseThrow(() -> new ResourceNotFoundException("L'auteur de l'article " + savedArticle.getId() + " n'a pas été trouvé."));

                ArticleAuthor articleAuthor = new ArticleAuthor();
                articleAuthor.setId(articleAuthorDTO.authorId());
                articleAuthor.setAuthor(author);
                articleAuthor.setArticle(savedArticle);
                articleAuthor.setContribution(articleAuthor.getContribution());

                articleAuthorRepository.save(articleAuthor);
            }
        }

        return ArticleDTO.mapFromEntity(savedArticle);
    }

    public ArticleDTO updateArticle(Long id, Article articleDetails) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'article avec l'id " + id + " n'a pas été trouvé"));
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        article.setUpdatedAt(LocalDateTime.now());

        // Mise à jour de la catégorie
        if (articleDetails.getCategory() != null) {
            Category category = categoryRepository.findById(articleDetails.getCategory().getId()).orElseThrow(() -> new ResourceNotFoundException("La catégorie de l'article " + id + " n'a pas été trouvée."));
            article.setCategory(category);
        }

        // Mise à jour des images
        if (articleDetails.getImages() != null) {
            List<Image> validImages = new ArrayList<>();
            for (Image image : articleDetails.getImages()) {
                if (image.getId() != null) {
                    Image existingImage = imageRepository.findById(image.getId()).orElse(null);
                    if (existingImage != null) {
                        validImages.add(existingImage);
                    } else {
                        throw new ResourceNotFoundException("L'image de l'article " + id + " n'a pas été trouvée.");
                    }
                } else {
                    Image savedImage = imageRepository.save(image);
                    validImages.add(savedImage);
                }
            }
            article.setImages(validImages);
        } else {
            article.getImages().clear();
        }

        // Mise à jour des auteurs
        if (articleDetails.getArticleAuthors() != null) {
            for (ArticleAuthor oldArticleAuthor : article.getArticleAuthors()) {
                articleAuthorRepository.delete(oldArticleAuthor);
            }

            List<ArticleAuthor> updatedArticleAuthors = new ArrayList<>();

            for (ArticleAuthor articleAuthorDetails : articleDetails.getArticleAuthors()) {
                Author author = articleAuthorDetails.getAuthor();
                author = authorRepository.findById(author.getId()).orElseThrow(() -> new ResourceNotFoundException("L'auteur de l'article " + id + " n'a pas été trouvé."));

                ArticleAuthor newArticleAuthor = new ArticleAuthor();
                newArticleAuthor.setAuthor(author);
                newArticleAuthor.setArticle(article);
                newArticleAuthor.setContribution(articleAuthorDetails.getContribution());

                updatedArticleAuthors.add(newArticleAuthor);
            }

            for (ArticleAuthor articleAuthor : updatedArticleAuthors) {
                articleAuthorRepository.save(articleAuthor);
            }

            article.setArticleAuthors(updatedArticleAuthors);
        }

        Article updatedArticle = articleRepository.save(article);
        return ArticleDTO.mapFromEntity(updatedArticle);
    }

    public boolean deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'article avec l'id " + id + " n'a pas été trouvé"));

        articleAuthorRepository.deleteAll(article.getArticleAuthors());
        articleRepository.delete(article);
        return true;
    }
}
