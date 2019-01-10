package read.pojos;

import java.util.ArrayList;
import java.util.List;

public class FileObject {

    List<Salesman> salesmans;
    List<Customer> customers;
    List<Sale> sales;
    String fileName;


    public FileObject() {
        this.salesmans = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.sales = new ArrayList<>();
        this.fileName ="";
    }

    public List<Salesman> getSalesmans() {
        return salesmans;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FileObject{" +
                "salesmans=" + salesmans +
                ", customers=" + customers +
                ", sales=" + sales +
                '}';
    }
}
