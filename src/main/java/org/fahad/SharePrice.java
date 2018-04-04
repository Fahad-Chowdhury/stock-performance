package org.fahad;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.util.Util;
import com.vaadin.ui.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;

public class SharePrice {


    protected Component getChart() {
        CSVReader data = new CSVReader();
        Chart chart = new Chart();
        chart.setHeight("450px");
        chart.setWidth("100%");
        final long DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
        final LocalDate DEMO_DATASET_END = LocalDate.of(2017, 12, 31);
        final LocalDate DEMO_DATASET_START = LocalDate.of(2017, 1, 1);

        Configuration configuration = chart.getConfiguration();
        configuration.getChart().setType(ChartType.LINE);
        configuration.getChart().setMarginRight(130);
        configuration.getChart().setMarginBottom(25);

        configuration.getTitle().setText("Stock performance in 2017");

        YAxis yAxis = configuration.getyAxis();
        yAxis.setMin(0d);
        yAxis.setTitle(new AxisTitle("Price (euro)"));
        yAxis.getTitle().setAlign(VerticalAlign.MIDDLE);

        configuration.getxAxis().setType(AxisType.DATETIME);

        configuration
                .getTooltip()
                .setFormatter(
                        "'<b>'+ this.series.name +'</b> <br/>' + Highcharts.dateFormat('%A %B %e %Y', this.x) + '<br/>price = '+ this.y +' euro'");

        PlotOptionsLine plotOptions = new PlotOptionsLine();
        plotOptions.setPointInterval(DAY_IN_MILLIS);
        plotOptions.setPointStart(Util.toHighchartsTS(DEMO_DATASET_START.atStartOfDay().toInstant(ZoneOffset.UTC)));
        plotOptions.getDataLabels().setEnabled(false);
        configuration.setPlotOptions(plotOptions);


        Legend legend = configuration.getLegend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setAlign(HorizontalAlign.RIGHT);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setX(-10d);
        legend.setY(100d);
        legend.setBorderWidth(0);

        ListSeries ls = data.getNokia();
        ls.setName("Nokia");
        configuration.addSeries(ls);
        ls = data.getNordea();
        ls.setName("Nordea");
        configuration.addSeries(ls);
        ls = data.getMicrosoft();
        ls.setName("Microsoft");
        configuration.addSeries(ls);
        ls = data.getTelia();
        ls.setName("Telia");
        configuration.addSeries(ls);

        chart.drawChart(configuration);
        return chart;
    }

}