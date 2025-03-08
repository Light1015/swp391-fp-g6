package entity;

/**
 * Lớp Book đại diện cho thực thể sách trong hệ thống.
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */


public class Book {

    private int bookId;
    private String title;
    private int authorId;
    private String description;
    private double price;
    private String coverImage;
    private String filePath;
    private String publisher;
    private int publicationYear;
    private int stockQuantity;
    private String language;
    private int seriesId;
    private int volumeNumber;
    private String bookType;
    private int createdBy;

    // Các thuộc tính mới cần thêm
    private String authorName;  // Tên tác giả
    private String seriesName;  // Tên bộ sách (nếu có)
    private String categories;  // Danh mục sách (dạng chuỗi cách nhau bằng dấu phẩy)

    // Constructor đầy đủ
    public Book(int bookId, String title, int authorId, String description, double price,
            String coverImage, String filePath, String publisher, int publicationYear,
            int stockQuantity, String language, int seriesId, int volumeNumber,
            String bookType, int createdBy) {
        this.bookId = bookId;
        this.title = title;
        this.authorId = authorId;
        this.description = description;
        this.price = price;
        this.coverImage = coverImage;
        this.filePath = filePath;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.stockQuantity = stockQuantity;
        this.language = language;
        this.seriesId = seriesId;
        this.volumeNumber = volumeNumber;
        this.bookType = bookType;
        this.createdBy = createdBy;
    }

    // Getter & Setter cho các thuộc tính mới
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    // Getter & Setter cho các thuộc tính cũ
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
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

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public int getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(int volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
