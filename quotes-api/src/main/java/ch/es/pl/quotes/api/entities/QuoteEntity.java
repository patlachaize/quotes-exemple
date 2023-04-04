package ch.es.pl.quotes.api.entities;


import jakarta.persistence.*;

@Entity(name = "Quote")
@Table(name = "quotes")
public class QuoteEntity {
    @TableGenerator(name = "genQuotes",
            table = "idQuotes",
            pkColumnName = "name",
            valueColumnName = "val",
            initialValue = 3,
            allocationSize = 100)
    @Id // @GeneratedValue
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genQuotes")
    private int id;
    private String author;
    private String citation;

    public QuoteEntity() {}

    public QuoteEntity(int id, String author, String citation) {
        this.id = id;
        this.author = author;
        this.citation = citation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }
}
