package View;

import Control.ErrorControl;
import Control.Session;
import Model.Error;
import Model.Product;
import Toolkit.Graph;
import Toolkit.SearchComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.UserSessionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductView extends View {

    private final Product product;

    public ProductView (Product product) {

        removeMenus(true);
        setResizable(false);
        setSize(new Dimension(500,465));

        this.product = product;
        setTitle(product.getArtName());
        this.setLayout(null);

        Graph graph = new Graph(this, this.product.getxPoints(), this.product.getyPoints(), this.product);
        this.addComponent("Graph", graph);

        JLabel ca = new JLabel("Auf Lager:");
        this.addComponent("CurrentAmount", ca);
        JTextField tfCA = new JTextField();
        tfCA.setText(product.getAmount()+"");
        tfCA.setEditable(false);
        this.addComponent("tfCA", tfCA);

        JLabel min = new JLabel("Mindestbestand:");
        this.addComponent("Min", min);
        JTextField tfMin = new JTextField();
        tfMin.setText(product.getMinBestand()+"");
        tfMin.setEditable(false);
        this.addComponent("tfMin", tfMin);

        JLabel max = new JLabel("Maximaler Bestand:");
        this.addComponent("Max", max);
        JTextField tfMax = new JTextField();
        tfMax.setText(product.getMaxBestand()+"");
        tfMax.setEditable(false);
        this.addComponent("tfMax", tfMax);

        JLabel mel = new JLabel("Meldebestand:");
        this.addComponent("Mel", mel);
        JTextField tfMel = new JTextField();
        tfMel.setText(product.getMelBestand()+"");
        tfMel.setEditable(false);
        this.addComponent("tfMel", tfMel);

        JLabel steuer = new JLabel("Steuersatz:");
        this.addComponent("Steuer", steuer);
        JTextField tfS = new JTextField();
        tfS.setText(NumberFormat.getPercentInstance().format(product.getSteuersatz()/100f));
        tfS.setEditable(false);
        this.addComponent("tfS", tfS);


        JLabel artNum = new JLabel("Artikelnummer:");
        this.addComponent("number", artNum);
        JTextField tfArtNum = new JTextField();
        tfArtNum.setText(product.getArtNumber());
        tfArtNum.setEditable(false);
        this.addComponent("tfArtNum", tfArtNum);

        JLabel artName = new JLabel("Artikelname:");
        this.addComponent("name", artName);
        JTextField tfArtName = new JTextField();
        tfArtName.setText(product.getArtName());
        tfArtName.setEditable(false);
        this.addComponent("tfArtName", tfArtName);

        JLabel einheit = new JLabel("Einheit:");
        this.addComponent("einheit", einheit);
        JTextField tfenheit = new JTextField();
        tfenheit.setText(product.getEinheit());
        tfenheit.setEditable(false);
        this.addComponent("tfenheit", tfenheit);


        JLabel vk = new JLabel("Verkaufspreis:");
        this.addComponent("vk", vk);
        JTextField tfvk = new JTextField();
        tfvk.setText(NumberFormat.getCurrencyInstance(new Locale("de", "DE")).format(product.getVkPreis()));
        tfvk.setEditable(false);
        this.addComponent("tfvk", tfvk);

        JLabel ek = new JLabel("Einkaufspreis:");
        this.addComponent("ek", ek);
        JTextField tfek = new JTextField();
        tfek.setText(NumberFormat.getCurrencyInstance(new Locale("de", "DE")).format(product.getEkPreis()));
        tfek.setEditable(false);
        this.addComponent("tfek", tfek);


        JLabel da = new JLabel("Nächste Lieferung:");
        this.addComponent("DeliveryDate", da);
        JTextField tfda = new JTextField();
        tfda.setText(product.isOrdered() ? String.valueOf(product.getLieferdatum()) : "Kein bevor stehender Wareneingang!");
        tfda.setEditable(false);
        this.addComponent("tfda", tfda);

        JLabel lo = new JLabel("Lagerort:");
        this.addComponent("Lagerort", lo);
        JTextField tflo = new JTextField();
        tflo.setText(product.getLagerort().toString());
        tflo.setEditable(false);
        this.addComponent("tflo", tflo);

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                int y = 150;
                graph.setBounds(0,0,getWidth(), 150);
                ca.setBounds(0, y, getWidth()/2, 25);
                tfCA.setBounds(getWidth()/2, y, getWidth()/2, 25);
                y += 25;
                min.setBounds(0, y, getWidth()/2, 25);
                tfMin.setBounds(getWidth()/2, y, getWidth()/2, 25);
                y += 25;
                max.setBounds(0, y, getWidth()/2, 25);
                tfMax.setBounds(getWidth()/2, y, getWidth()/2, 25);
                y += 25;
                mel.setBounds(0, y, getWidth()/2, 25);
                tfMel.setBounds(getWidth()/2, y, getWidth()/2, 25);
                y += 25;
                steuer.setBounds(0, y, getWidth()/2, 25);
                tfS.setBounds(getWidth()/2, y, getWidth()/2, 25);
                y += 25;

                artNum.setBounds(0, y, getWidth()/2, 25);
                tfArtNum.setBounds(getWidth()/2, y, getWidth()/2, 25);
                y += 25;
                artName.setBounds(0, y, getWidth()/2, 25);
                tfArtName.setBounds(getWidth()/2, y, getWidth()/2, 25);
                y += 25;
                einheit.setBounds(0, y, getWidth()/2, 25);
                tfenheit.setBounds(getWidth()/2, y, getWidth()/2, 25);
                y += 25;

                vk.setBounds(0, y, getWidth()/2, 25);
                tfvk.setBounds(getWidth()/2, y, getWidth()/2, 25);
                y += 25;
                ek.setBounds(0, y, getWidth()/2, 25);
                tfek.setBounds(getWidth()/2, y, getWidth()/2, 25);
                y += 25;

                da.setBounds(0, y, getWidth()/2, 25);
                tfda.setBounds(getWidth()/2, y, getWidth()/2, 25);
                y += 25;
                lo.setBounds(0, y, getWidth()/2, 25);
                tflo.setBounds(getWidth()/2, y, getWidth()/2, 25);
                y += 25;
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        this.setVisible(true);
    }


    /**
     * Update the View
     *
     * @param args objects to tell explicit to update
     */
    @Override
    public void update(Object... args) {

    }

    /**
     * NECESSARY FOR THE SEARCH
     *
     * @param list
     */
    @Override
    public void get_searchKeysAndActions(ArrayList<SearchComponent> list) {

    }
}
