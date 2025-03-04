package entity;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class BookCategory {

    private int book_id;
    private int category_id;

    public BookCategory() {
    }

    public BookCategory(int book_id, int category_id) {
        this.book_id = book_id;
        this.category_id = category_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

}
