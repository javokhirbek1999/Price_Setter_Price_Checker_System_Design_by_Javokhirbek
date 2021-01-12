package com.javokhirbek.developer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class PriceCheckerBuilder {

    private JFrame frame;
    private JTextArea item;
    private JTextArea price;
    private ArrayList<Item> itemList;


    public PriceCheckerBuilder() {
        //UI
        frame = new JFrame("Price Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create Panel
        JPanel jPanel = new JPanel();


        //Create Font
        Font font = new Font("Helvetica Neue", Font.BOLD, 21);

        // Item Text Area Font
        item = new JTextArea(5, 15);
        item.setLineWrap(true);
        item.setWrapStyleWord(true);
        item.setFont(font);

        // Price Text Area Font
        price = new JTextArea(5, 15);
        price.setLineWrap(true);
        price.setWrapStyleWord(true);
        price.setFont(font);

        // Item Text Area Pane
        JScrollPane itemScrollPanel = new JScrollPane(item);
        itemScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        itemScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Price Text Area Panel
        JScrollPane priceScrollPanel = new JScrollPane(price);
        priceScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        priceScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Buttons
        JButton nextButton = new JButton("Next");

        itemList = new ArrayList<>();
        System.out.println("Size: " + itemList.size());

        // Labels
        JLabel itemLabel = new JLabel("Item");
        JLabel priceLabel = new JLabel("Price");

        // Add components to the Main Panel
        jPanel.add(itemLabel);
        jPanel.add(itemScrollPanel);
        jPanel.add(priceLabel);
        jPanel.add(priceScrollPanel);
        jPanel.add(nextButton);
        nextButton.addActionListener(new nextButtonListener());

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem newFile = new JMenuItem("New");
        JMenuItem saveFile = new JMenuItem("Save");

        newFile.addActionListener(new newFileListener());
        saveFile.addActionListener(new saveFileListener());

        file.add(newFile);
        file.add(saveFile);

        menuBar.add(file);


        //Add components to the frame
        frame.getContentPane().add(BorderLayout.CENTER, jPanel);
        frame.setJMenuBar(menuBar);
        frame.setSize(500, 600);
        frame.setVisible(true);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PriceCheckerBuilder();
            }
        });
    }

    class newFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("New File Menu Selected");
        }
    }

    class saveFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Item item1 = new Item(item.getText(), price.getText());
            itemList.add(item1);

            JFileChooser file = new JFileChooser();
            file.showSaveDialog(frame);
            saveFile(file.getSelectedFile());
        }

    }

    class nextButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Item items = new Item(item.getText(), price.getText());
            itemList.add(items);
            clearText();
            System.out.println("Size: " + itemList.size());
        }
    }

    private void clearText() {
        item.setText("");
        price.setText("");
        item.requestFocus();
    }
    private void saveFile(File selectedFile) {

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));

            Iterator<Item> items = itemList.iterator();

            while (items.hasNext()) {
                Item item1 = items.next();
                writer.write(item1.getItem() + "/");
                writer.write("$"+item1.getPrice() + "\n");
            }
            writer.close();

        } catch (Exception e) {
            System.out.println("Couldn't write to file");
            e.printStackTrace();
        }
    }
}
