package org.wildcodeschool.myBlog.service;

import org.springframework.stereotype.Service;
import org.wildcodeschool.myBlog.dto.AuthorDTO;
import org.wildcodeschool.myBlog.model.Author;
import org.wildcodeschool.myBlog.repository.AuthorRepository;
import org.wildcodeschool.myBlog.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(AuthorDTO::mapFromEntity).collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'auteur avec l'id " + id + " n'a pas été trouvé"));
        return AuthorDTO.mapFromEntity(author);
    }

    public AuthorDTO createAuthor(Author author) {
        Author savedAuthor = authorRepository.save(author);
        return AuthorDTO.mapFromEntity(savedAuthor);
    }

    public AuthorDTO updateAuthor(Long id, Author authorRequest) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'auteur avec l'id " + id + " n'a pas été trouvé"));

        author.setFirstname(authorRequest.getFirstname());
        author.setLastname(authorRequest.getLastname());

        Author updatedAuthor = authorRepository.save(author);
        return AuthorDTO.mapFromEntity(updatedAuthor);
    }

    public boolean deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'auteur avec l'id " + id + " n'a pas été trouvé"));

        authorRepository.delete(author);
        return true;
    }
}
