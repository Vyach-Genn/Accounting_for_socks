package pro_sky.accounting_for_socks.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "socks")
public class Sock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String color;

    private Integer cottonPart;

    private Integer quantity;

    public Sock() {
    }

    public Sock(long id, String color, Integer cottonPart, Integer quantity) {
        this.id = id;
        this.color = color;
        this.cottonPart = cottonPart;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCottonPart() {
        return cottonPart;
    }

    public void setCottonPart(Integer cottonPart) {
        this.cottonPart = cottonPart;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sock sock = (Sock) o;
        return id == sock.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
