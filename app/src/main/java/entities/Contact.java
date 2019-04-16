package entities;

import java.io.Serializable;

public class Contact implements Serializable {

    private int id;
    private String name;
    private String phone;
    private String description;
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Contact(int id, String name, String phone, String description, String category) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.category = category;
    }

    public Contact() {
    }
}
