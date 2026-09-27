import java.io.Serializable;

public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String category;

    public Skill() {
    }

    public Skill(int id, String name, String category) {

        this.id = id;
        this.name = name;
        this.category = category;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean hasSameName(Skill other) {

        return this.name.equalsIgnoreCase(other.name);
    }

    @Override
    public String toString() {

        return name + " (" + category + ")";
    }
}