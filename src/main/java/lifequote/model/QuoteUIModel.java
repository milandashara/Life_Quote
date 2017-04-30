package lifequote.model;

import lifequote.domain.Author;
import lifequote.domain.Virtue;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by milanashara on 12/24/16.
 */

public class QuoteUIModel {


    private Long id;

    private String name;

    private String description;

    private String imageRelativeUrl;

    private Long virtueId;

    private Long authorId;

    private Virtue virtue;

    private Author author;

    private List<Author> authors;

    private List<Virtue> virtues;

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Virtue> getVirtues() {
        return virtues;
    }

    public void setVirtues(List<Virtue> virtues) {
        this.virtues = virtues;
    }

    //    private MultipartFile file;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageRelativeUrl() {
        return imageRelativeUrl;
    }

    public void setImageRelativeUrl(String imageRelativeUrl) {
        this.imageRelativeUrl = imageRelativeUrl;
    }

    public Long getVirtueId() {
        return virtueId;
    }

    public void setVirtueId(Long virtueId) {
        this.virtueId = virtueId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "QuoteUIModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageRelativeUrl=" + imageRelativeUrl +
                ", virtueId=" + virtueId +
                ", authorId=" + authorId +
                '}';
    }

    public Virtue getVirtue() {
        return virtue;
    }

    public void setVirtue(Virtue virtue) {
        this.virtue = virtue;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
