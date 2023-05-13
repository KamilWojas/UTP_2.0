package zad2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FutureTaskList extends JFrame implements ActionListener {

    private JButton startButton, cancelButton, getResultButton;
    private JList<Future<String>> taskList;
    private DefaultListModel<Future<String>> listModel;
    private ExecutorService executorService;
    private List<Future<String>> taskFutures;

    public FutureTaskList() {
        super("Future Task List");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);

        // Initialize components
        startButton = new JButton("Start Task");
        startButton.addActionListener(this);

        cancelButton = new JButton("Cancel Task");
        cancelButton.addActionListener(this);

        getResultButton = new JButton("Get Result");
        getResultButton.addActionListener(this);

        listModel = new DefaultListModel<Future<String>>();
        taskList = new JList<Future<String>>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(getResultButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        executorService = Executors.newFixedThreadPool(5);
        taskFutures = new ArrayList<Future<String>>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            // Submit task to executor service
            Future<String> future = executorService.submit(new ExampleTask());
            taskFutures.add(future);
            listModel.addElement(future);
        } else if (e.getSource() == cancelButton) {
            // Cancel selected task
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex >= 0) {
                Future<String> future = listModel.getElementAt(selectedIndex);
                if (!future.isDone()) {
                    future.cancel(true);
                    listModel.removeElement(future);
                    taskFutures.remove(future);
                }
            }
        } else if (e.getSource() == getResultButton) {
            // Show result of selected task
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex >= 0) {
                Future<String> future = listModel.getElementAt(selectedIndex);
                if (future.isDone()) {
                    try {
                        String result = future.get();
                        JOptionPane.showMessageDialog(this, "Result: " + result, "Task Result", JOptionPane.INFORMATION_MESSAGE);
                    } catch (InterruptedException | ExecutionException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private static class ExampleTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            // Perform long-running task here
            Thread.sleep(5000);
            return "Task completed!";
        }
    }

    public static void main(String[] args) {
        FutureTaskList app = new FutureTaskList();
        app.setVisible(true);
    }
}