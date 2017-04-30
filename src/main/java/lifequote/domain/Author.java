package lifequote.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by milanashara on 12/24/16.
 */
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author", targetEntity = Quote.class)
    private List<Quote> quotes;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }
}
