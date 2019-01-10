package write;

import read.pojos.FileObject;
import read.pojos.Items;
import read.pojos.Salesman;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.lineSeparator;

public class Writer {

    public void writeFile(FileObject file) {

        try {
            OutputStream out = new FileOutputStream(System
                    .getProperty("user.home") + "/data/out/" + file.getFileName() + ".done.dat");

            out.write(analyseData(file).getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String analyseData(FileObject file) {
        String analysedData = "";

        int amountCustomers = file.getCustomers().size();
        int amountSalesmans = file.getSalesmans().size();
        int saleId = findMostExpensiveSale(file);
        String worstSalesman = findWorseSalesman(file);

        analysedData = analysedData.concat("Amount of customers: [" + amountCustomers + "]." + lineSeparator());
        analysedData = analysedData.concat("Amount of salesmans: [" + amountSalesmans + "]." + lineSeparator());
        analysedData = analysedData.concat("ID of the most expensive sale: [" + saleId + "]." + lineSeparator());
        analysedData = analysedData.concat("Worst salesman ever: [" + worstSalesman + "].");
        return analysedData;
    }

    private String findWorseSalesman(FileObject file) {
        Map<String, Double> salesmans = new HashMap<>();

        for (Salesman salesman: file.getSalesmans()) {
            salesmans.put(salesman.getName(), 0.0);
        }

        for (int i = 0; i < file.getSales().size(); i++) {
            double total;
            for (int j = 0; j <file.getSales().get(i).getItems().size() ; j++) {
                Items item = file.getSales().get(i).getItems().get(j);
                total = item.getQuantity() * item.getValue();

                String name = file.getSales().get(i).getSalesmanName();
                if (salesmans.containsKey(name)) {
                    salesmans.put(file.getSales().get(i).getSalesmanName(), total + salesmans.get(name));

                } else {
                    salesmans.put(file.getSales().get(i).getSalesmanName(), total);
                }
            }
        }

        double min = Double.MAX_VALUE;
        List<String> minKeys = new ArrayList<>();
        for(Map.Entry<String, Double> entry : salesmans.entrySet()) {
            if(entry.getValue() < min) {
                min = entry.getValue();
                minKeys.clear();
            }
            if(entry.getValue() == min) {
                minKeys.add(entry.getKey());
            }
        }

        return minKeys.get(0);
    }

    private int findMostExpensiveSale(FileObject file) {
        int saleId = 0;
        double expensiveSale = 0;
        for (int i = 0; i < file.getSales().size(); i++) {
            for (int j = 0; j <file.getSales().get(i).getItems().size() ; j++) {
                Items item = file.getSales().get(i).getItems().get(j);
                double total = item.getQuantity() * item.getValue();
                if (total > expensiveSale) {
                    expensiveSale = total;
                    saleId = file.getSales().get(i).getSaleId();
                }
            }
        }
        return saleId;
    }
}
