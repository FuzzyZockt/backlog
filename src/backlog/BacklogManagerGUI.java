package backlog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Task {
    private String name;
    private String description;
    private String status;

    public Task(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name + " (" + status + ")";
    }
}

public class BacklogManagerGUI {
    private ArrayList<Task> backlog;
    private JFrame frame;
    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;

    public BacklogManagerGUI() {
        backlog = new ArrayList<>();
        frame = new JFrame("Backlog Manager");
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);

        initializeGUI();
    }

    private void initializeGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(taskList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Task");
        JButton updateButton = new JButton("Update Task");
        JButton deleteButton = new JButton("Delete Task");

        addButton.addActionListener(e -> addTask());
        updateButton.addActionListener(e -> updateTask());
        deleteButton.addActionListener(e -> deleteTask());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void addTask() {
        JTextField nameField = new JTextField(10);
        JTextField descriptionField = new JTextField(10);
        String[] statuses = {"To Do", "In Progress", "Done"};
        JComboBox<String> statusBox = new JComboBox<>(statuses);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Status:"));
        panel.add(statusBox);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Add Task", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String description = descriptionField.getText();
            String status = (String) statusBox.getSelectedItem();

            Task newTask = new Task(name, description, status);
            backlog.add(newTask);
            taskListModel.addElement(newTask);
        }
    }

    private void updateTask() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(frame, "Please select a task to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nameField = new JTextField(selectedTask.getName(), 10);
        JTextField descriptionField = new JTextField(selectedTask.getDescription(), 10);
        String[] statuses = {"To Do", "In Progress", "Done"};
        JComboBox<String> statusBox = new JComboBox<>(statuses);
        statusBox.setSelectedItem(selectedTask.getStatus());

        JPanel panel = new JPanel();
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Status:"));
        panel.add(statusBox);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Update Task", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            selectedTask.setName(nameField.getText());
            selectedTask.setDescription(descriptionField.getText());
            selectedTask.setStatus((String) statusBox.getSelectedItem());

            taskList.repaint();
        }
    }

    private void deleteTask() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(frame, "Please select a task to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this task?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            backlog.remove(selectedTask);
            taskListModel.removeElement(selectedTask);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BacklogManagerGUI::new);
    }
}

