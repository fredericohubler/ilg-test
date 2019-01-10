package read;

import read.pojos.Customer;
import read.pojos.FileObject;
import read.pojos.Items;
import read.pojos.Sale;
import read.pojos.Salesman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Reader {

    private final File folder = new File(System.getProperty("user.home") + "/data/in");

    public List<FileObject> readFiles() {

        List<FileObject> fileObjects = new ArrayList<>();

        if (folder.exists()) {
            for (int i = 0; i < folder.listFiles().length; i++) {
                String path = folder.listFiles()[i].getAbsolutePath();
                if (!path.substring(path.length() -4).equals(".dat")){
                    continue;
                }

                FileObject currentFile = new FileObject();

                currentFile.setFileName(folder.listFiles()[i].getName().replace(".dat", ""));

                String data = extractString(folder.listFiles()[i]);
                int index = findThirdIndex(data);

                String[] dataSplit = data.split("ç");
                while (dataSplit.length > 2) {
                    readFileObject(data, currentFile);

                    data = data.substring(index + dataSplit[3].length() - 3);
                    index = findThirdIndex(data);
                    dataSplit = data.split("ç");
                }
                fileObjects.add(currentFile);
            }
        }
        return fileObjects;
    }

    private void readFileObject(String currentLine, FileObject fileObject) {

        String[] data = currentLine.split("ç");
        String id = data[0];

        switch (parseInt(id.substring(2))) {

            case 1:
                Salesman salesman = new Salesman();
                salesman.setCPF(data[1]);
                salesman.setName(data[2]);

                salesman.setSalary(parseDouble(data[3].split(" ")[0]));
                fileObject.getSalesmans().add(salesman);

                break;

            case 2:
                Customer customer = new Customer();
                customer.setCNPJ(data[1]);
                customer.setName(data[2]);
                customer.setBusinessArea(data[3].split(" ")[0]);
                fileObject.getCustomers().add(customer);

                break;

            case 3:
                Sale sale = new Sale();
                sale.setSaleId(parseInt(data[1]));
                sale.setItems(populateItems(data[2]));
                sale.setSalesmanName(data[3].split(" ")[0]);
                fileObject.getSales().add(sale);

                break;
        }
    }

    private int findThirdIndex(String data) {
        return data.indexOf("ç", data.indexOf("ç", data.indexOf("ç") + 1) + 1) + 1;
    }

    private String extractString(File file) {

        String extracted = "";
        try {
            Scanner sc = new Scanner(file);
            if (sc.hasNextLine()) extracted = sc.nextLine();
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return extracted;
    }

    private List<Items> populateItems(String data) {
        List<Items> items = new ArrayList<>();
        data = data.replace("[", "");
        data = data.replace("]", "");

        String[] split = data.split(",");

        int i = 0;
        while (i < split.length) {
            items.add(new Items(split[i]));
            i++;
        }

        return items;
    }
}