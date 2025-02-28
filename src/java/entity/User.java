/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class User {

    private int accountId;
    private String username;
    private String password;
    private String email;
    private boolean isUser; // 0 = user, 1 = admin
    private String fullName;
    private String phone;
    private String address;
    private String image;
    private boolean isMember; // 0: không phải thành viên, 1: thành viên VIP

    public User() {
    }

    public User(int accountId, String username, String password, String email, boolean isUser, String fullName, String phone, String address, String image, boolean isMember) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isUser = isUser;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.image = image;
        this.isMember = isMember;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIsUser() {
        return isUser;
    }

    public void setIsUser(boolean isUser) {
        this.isUser = isUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isIsMember() {
        return isMember;
    }

    public void setIsMember(boolean isMember) {
        this.isMember = isMember;
    }
    
    
    
}
