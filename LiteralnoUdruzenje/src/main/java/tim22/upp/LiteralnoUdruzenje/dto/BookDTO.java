package tim22.upp.LiteralnoUdruzenje.dto;

import tim22.upp.LiteralnoUdruzenje.model.Writer;

import java.util.HashSet;
import java.util.Set;

public class BookDTO {

    private String name;
    private Set<String> authorNames = new HashSet<String>();
    private String genre;

    public BookDTO(){

    }

    public BookDTO(String name, Set<Writer> writers, String genre){
        this.name = name;
        Set<String> authorNames = new HashSet<>();
        for (Writer writer: writers) {
            authorNames.add(writer.getUsername());
        }
        this.authorNames = authorNames;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(Set<String> authorNames) {
        this.authorNames = authorNames;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}