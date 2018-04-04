package org.fahad;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.ChartSelectionEvent;
import com.vaadin.addon.charts.ChartSelectionListener;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.GradientColor;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.util.Util;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class DetailedChart_Telia {
    CSVReader data = new CSVReader();
    @SuppressWarnings("deprecation")
    private static final LocalDate DEMO_DATASET_END = LocalDate.of(2017, 12, 31);
    private static final LocalDate DEMO_DATASET_START = LocalDate.of(2017, 1, 1);
    private final Number[] FULL_DEMO_DATA_SET = data.getTelia().getData();

    private static final long DAY_IN_MILLIS = 24 * 60 * 60 * 1000;

    protected Component getChart() {
        VerticalLayout lo = new VerticalLayout();
        lo.setSpacing(false);
        lo.setMargin(false);
        lo.setWidth("100%");
        lo.setHeight("600px");

        final Chart masterChart = getMasterChart();
        final Chart detailChart = getDetailChart();

        masterChart.addChartSelectionListener(new ChartSelectionListener() {
            @Override
            public void onSelection(ChartSelectionEvent event) {
                long start = event.getSelectionStart().longValue();
                long end = event.getSelectionEnd().longValue();

                // set plot band to highlight the selection on the master chart
                PlotBand plotBand1 = new PlotBand();
                PlotBand plotBand2 = new PlotBand();
                plotBand1.setColor(new SolidColor(0, 0, 0, 0.2));
                plotBand2.setColor(new SolidColor(0, 0, 0, 0.2));
                plotBand1.setFrom(Util.toHighchartsTS(DEMO_DATASET_START.atStartOfDay().toInstant(ZoneOffset.UTC)));
                plotBand1.setTo(start);
                plotBand2.setFrom(end);
                plotBand2.setTo(Util.toHighchartsTS(DEMO_DATASET_END.atStartOfDay().toInstant(ZoneOffset.UTC)));

                masterChart.getConfiguration().getxAxis()
                        .setPlotBands(plotBand1, plotBand2);
                masterChart.drawChart();

                List<Number> list = DetailedChart_Telia.this.getPartialList(
                        start, end);

                Configuration configuration = detailChart.getConfiguration();
                configuration.getChart().setAnimation(false);
                ListSeries detailData = (ListSeries) configuration.getSeries()
                        .get(0);
                PlotOptionsLine plotOptionsLine = (PlotOptionsLine) detailData
                        .getPlotOptions();
                plotOptionsLine.setPointStart(start);
                detailData.setData(list);
                detailChart.drawChart(configuration);
            }

        });

        lo.addComponent(detailChart);
        lo.addComponent(masterChart);

        return lo;
    }

    private List<Number> getPartialList(long start, long end) {
        List<Number> list = new ArrayList<>();
        for (int i = 0; i < FULL_DEMO_DATA_SET.length; i++) {
            long dataTimeStamp = Util.toHighchartsTS(DEMO_DATASET_START.atStartOfDay().toInstant(ZoneOffset.UTC)) + i
                    * DAY_IN_MILLIS;
            if (dataTimeStamp > start && dataTimeStamp < end) {
                list.add(FULL_DEMO_DATA_SET[i]);
            }
        }
        return list;
    }

    private Chart getDetailChart() {
        Chart detailChart = new Chart();
        detailChart.setHeight("100%");
        detailChart.setWidth("100%");

        Configuration configuration = detailChart.getConfiguration();

        configuration.getCredits().setEnabled(false);
        configuration.setTitle("Telia Stock");
        configuration
                .setSubTitle("Select an area by dragging across the lower chart");
        configuration.getxAxis().setType(AxisType.DATETIME);
        configuration.getyAxis().setTitle(new AxisTitle((String) "Price (EUR)"));

        configuration.getyAxis().setMinRange(0.1);

        configuration
                .getTooltip()
                .setFormatter(
                        "function() {var point = this.points[0];return '<b>'+ point.series.name +'</b><br/>'+ Highcharts.dateFormat('%A %B %e %Y', this.x) + ':<br/>'+ Highcharts.numberFormat(point.y, 2) +' EUR';}");
        configuration.getTooltip().setShared(true);

        PlotOptionsLine series = new PlotOptionsLine();
        series.setPointInterval(DAY_IN_MILLIS);
        configuration.setPlotOptions(series);

        States states = new States();
        Hover hover = new Hover();
        hover.setRadius(3);
        states.setHover(hover);

        Marker marker = series.getMarker();
        marker.setEnabled(false);
        marker.setStates(states);
        marker.setRadius(2);
        series.setAnimation(false);

        ListSeries seriesList = new ListSeries();
        PlotOptionsLine plotOptionsLine = new PlotOptionsLine();
        plotOptionsLine.setPointInterval(DAY_IN_MILLIS);
        plotOptionsLine.setPointStart(Util.toHighchartsTS(DEMO_DATASET_START.atStartOfDay().toInstant(ZoneOffset.UTC)));
        seriesList.setPlotOptions(plotOptionsLine);
        seriesList.setName("Telia");
        seriesList.setData(FULL_DEMO_DATA_SET);

        configuration.getLegend().setEnabled(false);
        configuration.setExporting(false);
        configuration.addSeries(seriesList);

        detailChart.drawChart(configuration);

        return detailChart;
    }

    private Chart getMasterChart() {
        Chart masterChart = new Chart(ChartType.AREA);
        masterChart.setHeight("80px");
        masterChart.setWidth("100%");
        masterChart.setId("master-chart");

        Configuration configuration = masterChart.getConfiguration();
        configuration.getChart().setZoomType(ZoomType.X);

        configuration.getChart().setReflow(false);
        configuration.getChart().setBorderWidth(0);
        configuration.getChart().setBackgroundColor(null);
        configuration.getChart().setMarginLeft(50);
        configuration.getChart().setMarginRight(20);

        configuration.getTitle().setText("");

        configuration.getxAxis().setType(AxisType.DATETIME);
        configuration.getxAxis().setShowLastLabel(true);
        configuration.getxAxis().setMinRange(14 * DAY_IN_MILLIS);
        configuration.getxAxis().setTitle(new AxisTitle(""));

        PlotBand mask = new PlotBand();
        mask.setColor(new SolidColor(0, 0, 0, 0.2));
        mask.setFrom(Util.toHighchartsTS(DEMO_DATASET_START.atStartOfDay().toInstant(ZoneOffset.UTC)));
        mask.setTo(Util.toHighchartsTS(DEMO_DATASET_END.atStartOfDay().toInstant(ZoneOffset.UTC)));
        configuration.getxAxis().setPlotBands(mask);

        YAxis yAxis = configuration.getyAxis();
        yAxis.setGridLineWidth(0);
        yAxis.setLabels(new Labels(false));
        yAxis.setTitle(new AxisTitle(""));
        yAxis.setMin(0.6);
        yAxis.setShowFirstLabel(false);

        configuration.getTooltip().setEnabled(false);

        configuration.getLegend().setEnabled(false);
        configuration.getCredits().setEnabled(false);

        PlotOptionsArea plotOptions = new PlotOptionsArea();
        plotOptions.setLineWidth(1);
        plotOptions.setShadow(false);
        Hover hover = new Hover();
        hover.setLineWidth(1);
        States states = new States();
        states.setHover(hover);
        plotOptions.setStates(states);
        plotOptions.setEnableMouseTracking(false);
        plotOptions.setAnimation(false);
        configuration.setPlotOptions(plotOptions);

        ListSeries ls = new ListSeries();
        PlotOptionsArea masterPlotOptions = new PlotOptionsArea();
        GradientColor fillColor = GradientColor.createLinear(0, 0, 0, 1);
        fillColor.addColorStop(0, new SolidColor(69, 114, 167, 1));
        fillColor.addColorStop(1, new SolidColor(69, 114, 167, 0.5));
        masterPlotOptions.setFillColor(fillColor);
        masterPlotOptions.setPointInterval(24 * 3600 * 1000);
        masterPlotOptions.setMarker(new Marker(false));
        masterPlotOptions
                .setPointStart(Util.toHighchartsTS(DEMO_DATASET_START.atStartOfDay().toInstant(ZoneOffset.UTC)));
        ls.setPlotOptions(masterPlotOptions);
        ls.setName("Telia");
        ls.setData(FULL_DEMO_DATA_SET);
        configuration.addSeries(ls);

        masterChart.drawChart(configuration);

        return masterChart;
    }
}
