package read.pojos;

public class Items {

    private Integer id;
    private Integer quantity;
    private Double value;

    public Items(String data) {
        String[] values = data.split("-");

        this.id = Integer.parseInt(values[0]);
        this.quantity = Integer.parseInt(values[1]);
        this.value = Double.parseDouble(values[2]);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getValue() {
        return value;
    }
}
