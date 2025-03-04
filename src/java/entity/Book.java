/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class Book {

    private int book_id;
    private String title;
    private String author;
    private String description;
    private double price;
    private String cover_image;
    private String file_path;

    public Book() {
    }

    public Book(int book_id, String title, String author, String description, double price, String cover_image, String file_path) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.cover_image = cover_image;
        this.file_path = file_path;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    // Phương thức toString để in thông tin sách
    @Override
    public String toString() {
        return "Book{"
                + "book_id=" + book_id
                + ", title='" + title + '\''
                + ", author='" + author + '\''
                + ", description='" + description + '\''
                + ", price=" + price
                + ", cover_image='" + cover_image + '\''
                + ", file_path='" + file_path + '\''
                + '}';
    }
    
}
