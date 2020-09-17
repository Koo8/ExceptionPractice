import java.io.Serializable;
import java.math.BigDecimal;

public class Shopping implements Serializable {

    private BigDecimal price;
    private int unit;
    private String description;

    public Shopping(BigDecimal price, int unit, String description) {
        this.price = price;
        this.unit = unit;
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
