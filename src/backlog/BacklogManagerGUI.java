package backlog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


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
        frame.setSize(600, 400);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(taskList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        taskList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewTaskDetails();
                }
            }
        });

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
        JTextField deadlineField = new JTextField(10);
        JTextField assigneeField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Status:"));
        panel.add(statusBox);
        panel.add(new JLabel("Deadline:"));
        panel.add(deadlineField);
        panel.add(new JLabel("Assignee:"));
        panel.add(assigneeField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Add Task", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String description = descriptionField.getText();
            String status = (String) statusBox.getSelectedItem();
            String deadline = deadlineField.getText();
            String assignee = assigneeField.getText();

            Task newTask = new Task(name, description, status, deadline, assignee);
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
        JTextField deadlineField = new JTextField(selectedTask.getDeadline(), 10);
        JTextField assigneeField = new JTextField(selectedTask.getAssignee(), 10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Status:"));
        panel.add(statusBox);
        panel.add(new JLabel("Deadline:"));
        panel.add(deadlineField);
        panel.add(new JLabel("Assignee:"));
        panel.add(assigneeField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Update Task", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            selectedTask.setName(nameField.getText());
            selectedTask.setDescription(descriptionField.getText());
            selectedTask.setStatus((String) statusBox.getSelectedItem());
            selectedTask.setDeadline(deadlineField.getText());
            selectedTask.setAssignee(assigneeField.getText());

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

    private void viewTaskDetails() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(frame, "Please select a task to view details.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String details = "Name: " + selectedTask.getName() + "\n"
                + "Description: " + selectedTask.getDescription() + "\n"
                + "Status: " + selectedTask.getStatus() + "\n"
                + "Deadline: " + selectedTask.getDeadline() + "\n"
                + "Assignee: " + selectedTask.getAssignee();

        JOptionPane.showMessageDialog(frame, details, "Task Details", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BacklogManagerGUI::new);
    }
}
