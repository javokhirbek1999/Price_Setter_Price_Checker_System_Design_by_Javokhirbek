package com.javokhirbek.developer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class PriceChecker {

    private JTextArea display;
    private ArrayList<Item> itemList;
    private Iterator iterator;
    private Item currentItem;
    private JFrame frame;
    private boolean isShowingItem;
    private JButton showPrice;
    private JPanel panel;
    private Font font;

    public PriceChecker() {
        //UI
        frame = new JFrame("Price Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        font = new Font("Halvetica Neue", Font.ITALIC, 22);

        display = new JTextArea(10, 20);
        display.setFont(font);

        JScrollPane itemPanel = new JScrollPane(display);
        itemPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        itemPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Select file");
        open.addActionListener(new openFile());

        file.add(open);
        menuBar.add(file);
        frame.setJMenuBar(menuBar);

        showPrice = new JButton("Show Price");

        panel.add(display);
        panel.add(showPrice);
        showPrice.addActionListener(new showPriceForSelectedItem());


        // Add to the frame
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(650, 500);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PriceChecker();
            }
        });
    }

    private class showPriceForSelectedItem implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isShowingItem) {
                display.setText(currentItem.getPrice());
                showPrice.setText("Next Item");
                isShowingItem = false;
            } else {
                //show the next item
                if (iterator.hasNext()) {
                    showNextItem();
                } else {
                    // handle if no more items left
                    display.setText("That was the last item");
                    showPrice.setEnabled(false);
                }
            }
        }
    }

    private class openFile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(frame);
            showSelectedItem(fileChooser.getSelectedFile());
        }
    }
    private void showSelectedItem(File selectedFile) {

        itemList = new ArrayList<Item>();

        try {

            BufferedReader br = new BufferedReader(new FileReader(selectedFile));

            String line = null;

            while ((line = br.readLine()) != null) {
                getItem(line);
            }

        } catch (Exception e) {
            System.out.println("Couldn't load the file");
            e.printStackTrace();
        }

        // show item and price

        iterator = itemList.iterator();
        showNextItem();

    }

    private void getItem(String line) {

        StringTokenizer result = new StringTokenizer(line, "/");
        if (result.hasMoreTokens()) {
            Item item = new Item(result.nextToken(), result.nextToken());
            itemList.add(item);
            System.out.println("Item selected");
        }

    }

    private void showNextItem() {
        currentItem = (Item) iterator.next();

        display.setText(currentItem.getItem());
        showPrice.setText("Show Price");
        isShowingItem = true;
    }
}

