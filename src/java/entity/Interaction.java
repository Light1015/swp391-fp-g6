package entity;

import java.util.Date;

/**
 *
 * @author SE18-CE181003-Lam Huy Hoang
 */
public class Interaction {

    private int interaction_id;
    private int account_id;
    private int book_id;
    private String action;
    private double rating;
    private String comment;
    private String username;
    private Date created_at;
    private int status;

    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_REPORTED = 1;
    public static final int STATUS_DISABLED = 2;

    public Interaction() {
    }

    public Interaction(int interaction_id, int account_id, int book_id, double rating, String comment) {
        this.interaction_id = interaction_id;
        this.account_id = account_id;
        this.book_id = book_id;
        this.rating = rating;
        this.comment = comment;
    }

    public Interaction(int interaction_id, int account_id, int book_id, String action, double rating, String comment) {
        this.interaction_id = interaction_id;
        this.account_id = account_id;
        this.book_id = book_id;
        this.action = action;
        this.rating = rating;
        this.comment = comment;
    }

    public Interaction(int interaction_id, int account_id, int book_id, String action, double rating, String comment,
            String username, Date created_at) {
        this.interaction_id = interaction_id;
        this.account_id = account_id;
        this.book_id = book_id;
        this.action = action;
        this.rating = rating;
        this.comment = comment;
        this.username = username;
        this.created_at = created_at;
    }

    public Interaction(int interaction_id, int account_id, int book_id, String action,
            double rating, String comment, String username, Date created_at, int status) {
        this.interaction_id = interaction_id;
        this.account_id = account_id;
        this.book_id = book_id;
        this.action = action;
        this.rating = rating;
        this.comment = comment;
        this.username = username;
        this.created_at = created_at;
        this.status = status;
    }

    public int getInteraction_id() {
        return interaction_id;
    }

    public void setInteraction_id(int interaction_id) {
        this.interaction_id = interaction_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusName() {
        switch (status) {
            case STATUS_NORMAL:
                return "normal";
            case STATUS_REPORTED:
                return "reported";
            case STATUS_DISABLED:
                return "disabled";
            default:
                return "unknown";
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
