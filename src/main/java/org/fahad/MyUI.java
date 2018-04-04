package org.fahad;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;

@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final VerticalLayout layout = new VerticalLayout();

        SharePrice price = new SharePrice();
        Component priceChart = price.getChart();
        layout.addComponent(priceChart);

        CompareStock stock = new CompareStock();
        Component stockChart = stock.getChart();
        layout.addComponent(stockChart);

        DetailedChart_Nokia detailedChart_nokia = new DetailedChart_Nokia();
        Component nokiaChart = detailedChart_nokia.getChart();
        layout.addComponent(nokiaChart);

        DetailedChart_Nordea detailedChart_nordea = new DetailedChart_Nordea();
        Component nordeaChart = detailedChart_nordea.getChart();
        layout.addComponent(nordeaChart);

        DetailedChart_Microsoft detailedChart_microsoft = new DetailedChart_Microsoft();
        Component microsoftChart = detailedChart_microsoft.getChart();
        layout.addComponent(microsoftChart);

        DetailedChart_Telia detailedChart_telia = new DetailedChart_Telia();
        Component teliaChart = detailedChart_telia.getChart();
        layout.addComponent(teliaChart);

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
