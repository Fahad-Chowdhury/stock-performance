package org.fahad;

import com.vaadin.addon.charts.model.ListSeries;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVReader {
    private List<Date> date = new ArrayList<>();
    private List<String> dateString = new ArrayList<>();
    private ListSeries nokia = new ListSeries();
    private ListSeries nordea = new ListSeries();
    private ListSeries microsoft = new ListSeries();
    private ListSeries telia = new ListSeries();

    public CSVReader() {
        setUp();
    }

    public List<Date> getDate() {
        return date;
    }

    public ListSeries getNokia() {
        return nokia;
    }

    public ListSeries getNordea() {
        return nordea;
    }

    public ListSeries getMicrosoft() {
        return microsoft;
    }

    public ListSeries getTelia() {
        return telia;
    }

    void setUp(){
        String csvFile = "DATA.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] price = line.split(cvsSplitBy);
                dateString.add(price[0]);
                nokia.addData(Float.valueOf(price[1]));
                nordea.addData(Float.valueOf(price[2]));
                microsoft.addData(Float.valueOf(price[3]));
                telia.addData(Float.valueOf(price[4]));
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            dateString.forEach(dateString -> {
                try {
                    Date parsedDate = formatter.parse(dateString);
                    date.add(parsedDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
