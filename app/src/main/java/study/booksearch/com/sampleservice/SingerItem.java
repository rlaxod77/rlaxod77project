package study.booksearch.com.sampleservice;

public class SingerItem{

    String title;
    String authors;
    String ImageUrl;

    public SingerItem() {
    }


    public SingerItem(String title, String authors, String imageUrl) {
        this.title = title;
        this.authors = authors;
        this.ImageUrl = imageUrl;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
}