package com.company;
import java.awt.*;
import javax.swing.*;

public class displayArray extends JPanel {

    //region Variable Declaration

    private static final int SCREEN_WIDTH = 1200;
    private static final int SCREEN_HEIGHT = 600;
    private static final int BAR_WIDTH = 8;
    private static final int BAR_COUNT = SCREEN_WIDTH/BAR_WIDTH;
    private final int[] array = new int[BAR_COUNT];
    private SwingWorker<Void, Void> bubbleSorter, insertionSorter, selectionSorter, finalPainter;
    JMenuItem bubble, insertion, selection, quarter, half, full;
    private static int current_index = Integer.MAX_VALUE;
    private static int checking_index;
    private static boolean sorted;
    private static int algorithmChoice = 0;
    private static int time_delay = 1;

    //endregion

    public displayArray(){

        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
        addElements();

        JMenuBar menuBar = new JMenuBar();

        JMenu algorithms = new JMenu("Algorithms");
        JMenu speed = new JMenu("Speed");

        bubble = new JMenuItem("Bubble Sort");
        algorithms.add(bubble);
        insertion = new JMenuItem("Insertion Sort");
        algorithms.add(insertion);
        selection = new JMenuItem("Selection Sort");
        algorithms.add(selection);
        bubble.addActionListener(e -> algorithmChoice = 0);
        insertion.addActionListener(e -> algorithmChoice = 1);
        selection.addActionListener(e -> algorithmChoice = 2);

        quarter = new JMenuItem("0.25x");
        speed.add(quarter);
        half = new JMenuItem("0.50x");
        speed.add(half);
        full = new JMenuItem("1.00x");
        speed.add(full);
        quarter.addActionListener(e -> time_delay = 8);
        half.addActionListener(e -> time_delay = 4);
        full.addActionListener(e -> time_delay = 1);

        JMenuItem shuffle = new JMenuItem("Shuffle");
        shuffle.addActionListener(e -> randomizeElements());

        JMenuItem sort = new JMenuItem("Sort");
        sort.addActionListener(e -> runAlgorithm());

        menuBar.add(algorithms);
        menuBar.add(speed);
        menuBar.add(shuffle);
        menuBar.add(sort);
        this.add(menuBar);

    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D graphics = (Graphics2D)g;
        super.paintComponent(graphics);
        for (int i = 0; i < array.length; i++){
            if (current_index == i){
                graphics.setColor(Color.green);
            } else {
                graphics.setColor(Color.black);
            }
            graphics.fillRect(i*BAR_WIDTH, SCREEN_HEIGHT-array[i]*4, BAR_WIDTH, array[i]*4);
        }
        if (sorted){
            if (checking_index == 0){
                graphics.setColor(Color.green);
                graphics.fillRect(0, SCREEN_HEIGHT-array[0]*4, BAR_WIDTH, array[0]*4);
            } else if (checking_index == BAR_COUNT-1){
                sorted = false;
                repaint();
            }
            else {
                for (int i = 1; i <= checking_index; i++) {
                    graphics.setColor(Color.green);
                    graphics.fillRect(i * BAR_WIDTH, SCREEN_HEIGHT - array[i] * 4, BAR_WIDTH, array[i] * 4);
                    graphics.setColor(Color.black);
                    graphics.fillRect((i-1) * BAR_WIDTH, SCREEN_HEIGHT - array[i-1] * 4, BAR_WIDTH, array[i-1] * 4);
                }
            }
        }
    }

    //region Initialize Array

    public void addElements(){
        for (int i = 0; i < array.length; i++){
            array[i] = i;
        }
        randomizeElements();
    }

    public void randomizeElements(){
        for (int i = 0; i < array.length; i++){
            int temp = array[i];
            int pos = (int) (Math.random() * array.length);
            array[i] = array[pos];
            array[pos] = temp;
        }
        repaint();
    }

    //endregion

    //region Sorting Algorithms

    public void bubbleSort() {
        bubbleSorter = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws Exception {
                int n = array.length;
                for (int i = 0; i < n - 1; i++)
                    for (int j = 0; j < n - i - 1; j++)
                        if (array[j] > array[j + 1]) {
                            int temp = array[j];
                            array[j] = array[j + 1];
                            array[j + 1] = temp;
                            current_index = j;
                            repaint();
                            Thread.sleep(time_delay);
                        }

                return null;
            }

            @Override
            public void done(){
                super.done();
                current_index = Integer.MAX_VALUE;
                repaint();
                sorted = true;
                finalCheck();
            }
        };
        bubbleSorter.execute();
    }

    public void insertionSort() {
        insertionSorter = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws Exception {
                int n = array.length;
                for (int i = 1; i < n; ++i) {
                    int temp = array[i];
                    int j = i - 1;
                    while (j >= 0 && array[j] > temp) {
                        array[j + 1] = array[j];
                        j--;
                        current_index = j+1;
                        repaint();
                        Thread.sleep(time_delay);
                    }
                    array[j + 1] = temp;
                    repaint();
                    Thread.sleep(time_delay);
                }
                return null;
            }

            @Override
            public void done(){
                super.done();
                current_index = Integer.MAX_VALUE;
                repaint();
                sorted = true;
                finalCheck();
            }
        };
        insertionSorter.execute();
    }

    public void selectionSort() {
        selectionSorter = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws Exception {
                int n = array.length;
                for (int i = 0; i < n - 1; i++) {
                    int min_index = i;
                    for (int j = i + 1; j < n; j++)
                        if (array[j] < array[min_index])
                            min_index = j;
                    int temp = array[min_index];
                    array[min_index] = array[i];
                    current_index = min_index;
                    repaint();
                    Thread.sleep(time_delay);
                    array[i] = temp;
                    current_index = i;
                    repaint();
                    Thread.sleep(time_delay);
                }
                return null;
            }

            @Override
            public void done(){
                super.done();
                current_index = Integer.MAX_VALUE;
                repaint();
                sorted = true;
                finalCheck();
            }
        };
        selectionSorter.execute();
    }

    //endregion

    //region Helper Methods

    public void runAlgorithm(){
        if (algorithmChoice == 0)
            bubbleSort();
        else if (algorithmChoice == 1)
            insertionSort();
        else if (algorithmChoice == 2)
            selectionSort();
    }

    public void finalCheck(){
        finalPainter = new SwingWorker<>(){
            @Override
            public Void doInBackground() throws Exception {
                for (int i = 0; i < BAR_COUNT; i++){
                    checking_index = i;
                    repaint();
                    Thread.sleep(time_delay);
                }
                return null;
            }
        };
        finalPainter.execute();
    }

    //endregion

}

