package lifequote.domain;

import javax.persistence.*;

/**
 * Created by milanashara on 12/24/16.
 */
@Entity
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

//    private byte[] image;

    private String imageRelativeUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    private Virtue virtue = new Virtue();

    @ManyToOne(fetch = FetchType.EAGER)
    private Author author = new Author();

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

//    public byte[] getImage() {
//        return image;
//    }
//
//    public void setImage(byte[] image) {
//        this.image = image;
//    }

    public String getImageRelativeUrl() {
        return imageRelativeUrl;
    }

    public void setImageRelativeUrl(String imageRelativeUrl) {
        this.imageRelativeUrl = imageRelativeUrl;
    }
}
