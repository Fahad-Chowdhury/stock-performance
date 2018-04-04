package org.fahad;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.Component;

import static com.vaadin.addon.charts.model.Compare.PERCENT;

public class CompareStock {

    protected Component getChart() {
        CSVReader data = new CSVReader();

        final Chart chart = new Chart();
        chart.setHeight("450px");
        chart.setWidth("100%");
        chart.setTimeline(true);

        Configuration configuration = chart.getConfiguration();
        configuration.getTitle().setText("Stock Price Comparison");

        YAxis yAxis = new YAxis();
        Labels label = new Labels();
        label.setFormatter("(this.value > 0 ? ' + ' : '') + this.value + '%'");
        yAxis.setLabels(label);

        PlotLine plotLine = new PlotLine();
        plotLine.setValue(2);
        plotLine.setWidth(2);
        plotLine.setColor(SolidColor.SILVER);
        yAxis.setPlotLines(plotLine);
        configuration.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setPointFormat("<span style=\"color:{series.color}\">{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>");
        tooltip.setValueDecimals(2);
        configuration.setTooltip(tooltip);

        DataSeries nokiaSeries = new DataSeries();
        nokiaSeries.setName("Nokia");
        int index = 0;
        for (Number number : data.getNokia().getData() ) {
            DataSeriesItem item = new DataSeriesItem();
            item.setX(data.getDate().get(index));
            item.setY(number);
            index++;
            nokiaSeries.add(item);
        }

        DataSeries nordeaSeries = new DataSeries();
        nordeaSeries.setName("Nordea");
        index = 0;
        for (Number number : data.getNordea().getData()) {
            DataSeriesItem item = new DataSeriesItem();
            item.setX(data.getDate().get(index));
            item.setY(number);
            index++;
            nordeaSeries.add(item);
        }

        DataSeries microsoftSeries = new DataSeries();
        microsoftSeries.setName("Microsoft");
        index = 0;
        for (Number number : data.getMicrosoft().getData()) {
            DataSeriesItem item = new DataSeriesItem();
            item.setX(data.getDate().get(index));
            item.setY(number);
            index++;
            microsoftSeries.add(item);
        }

        DataSeries teliaSeries = new DataSeries();
        teliaSeries.setName("Telia");
        index = 0;
        for (Number number : data.getTelia().getData()) {
            DataSeriesItem item = new DataSeriesItem();
            item.setX(data.getDate().get(index));
            item.setY(number);
            index++;
            teliaSeries.add(item);
        }

        configuration.setSeries(nokiaSeries, nordeaSeries, microsoftSeries, teliaSeries);

        PlotOptionsSeries plotOptionsSeries = new PlotOptionsSeries();
        plotOptionsSeries.setCompare(PERCENT);
        configuration.setPlotOptions(plotOptionsSeries);

        RangeSelector rangeSelector = new RangeSelector();
        rangeSelector.setSelected(4);
        configuration.setRangeSelector(rangeSelector);

        chart.drawChart(configuration);
        return chart;
    }
}
