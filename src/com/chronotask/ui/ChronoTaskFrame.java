package com.chronotask.ui;

import com.chronotask.exception.TaskException;
import com.chronotask.model.Task;
import com.chronotask.model.Priority;
import com.chronotask.service.TaskService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChronoTaskFrame extends JFrame {

    private final TaskService taskService;

    private final DefaultListModel<Task> listModel =
            new DefaultListModel<>();

    private final JList<Task> taskList =
            new JList<>(listModel);

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    private final JLabel statusLabel = new JLabel("Ready");

    private final Color background = new Color(15, 18, 35);
    private final Color panelColor = new Color(25, 30, 52);
    private final Color accent = new Color(130, 105, 255);
    private final Color textColor = new Color(240, 242, 255);
    private final Color muted = new Color(155, 164, 190);

    public ChronoTaskFrame(TaskService taskService) {

        this.taskService = taskService;

        setTitle("ChronoTask");
        setSize(950, 650);
        setMinimumSize(new Dimension(750, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(background);

        createInterface();
        refreshTasks();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                executor.shutdown();
            }
        });
    }

    // Main interface
    private void createInterface() {

        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);
        add(createTaskArea(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    // Header
    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());

        header.setBackground(panelColor);
        header.setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("ChronoTask");

        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(textColor);

        JLabel subtitle = new JLabel(
                "Organise your tasks. Manage your time."
        );

        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(muted);

        JPanel heading = new JPanel(new GridLayout(2, 1, 0, 5));

        heading.setOpaque(false);
        heading.add(title);
        heading.add(subtitle);

        JLabel date = new JLabel(LocalDate.now().toString());

        date.setForeground(accent);
        date.setFont(new Font("Segoe UI", Font.BOLD, 14));

        header.add(heading, BorderLayout.WEST);
        header.add(date, BorderLayout.EAST);

        return header;
    }

    // Task display area
    private JPanel createTaskArea() {

        JPanel main = new JPanel(new BorderLayout(0, 15));

        main.setBackground(background);
        main.setBorder(new EmptyBorder(25, 30, 20, 30));

        JLabel sectionTitle = new JLabel("My Tasks");

        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 23));
        sectionTitle.setForeground(textColor);

        JLabel description = new JLabel(
                "Your tasks are displayed below."
        );

        description.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        description.setForeground(muted);

        JPanel heading = new JPanel(new GridLayout(2, 1, 0, 6));

        heading.setOpaque(false);
        heading.add(sectionTitle);
        heading.add(description);

        main.add(heading, BorderLayout.NORTH);

        taskList.setBackground(background);
        taskList.setForeground(textColor);
        taskList.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        taskList.setFixedCellHeight(85);
        taskList.setCellRenderer(new TaskRenderer());

        taskList.setSelectionBackground(
                new Color(49, 48, 85)
        );

        JScrollPane scrollPane = new JScrollPane(taskList);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(48, 54, 80)
                )
        );

        scrollPane.getViewport().setBackground(background);

        main.add(scrollPane, BorderLayout.CENTER);

        return main;
    }

    // Bottom buttons
    private JPanel createBottomPanel() {

        JPanel bottom = new JPanel(new BorderLayout());

        bottom.setBackground(panelColor);
        bottom.setBorder(new EmptyBorder(18, 25, 18, 25));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));

        buttons.setOpaque(false);

        JButton addButton = createButton("+ Add Task", accent);
        JButton completeButton = createButton("✓ Complete", new Color(35, 145, 110));
        JButton deleteButton = createButton("Delete", new Color(190, 65, 80));
        JButton refreshButton = createButton("Refresh", new Color(65, 95, 165));
        JButton activityButton = createButton("Activity Log", new Color(90, 75, 145));

        buttons.add(addButton);
        buttons.add(completeButton);
        buttons.add(deleteButton);
        buttons.add(refreshButton);
        buttons.add(activityButton);

        addButton.addActionListener(e -> addTask());

        completeButton.addActionListener(e -> completeTask());

        deleteButton.addActionListener(e -> deleteTask());

        refreshButton.addActionListener(e -> refreshTasks());

        activityButton.addActionListener(e -> showActivities());

        statusLabel.setForeground(muted);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        bottom.add(buttons, BorderLayout.CENTER);
        bottom.add(statusLabel, BorderLayout.SOUTH);

        return bottom;
    }

    // Styled button
    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        button.setBorder(new EmptyBorder(12, 18, 12, 18));

        return button;
    }

    // Custom task appearance
    private class TaskRenderer extends JLabel
            implements ListCellRenderer<Task> {

        @Override
        public Component getListCellRendererComponent(
                JList<? extends Task> list,
                Task task,
                int index,
                boolean selected,
                boolean focused) {

            String title = escape(task.getTitle());

            String priority = task.getPriority().name();

            String description = task.getDescription() == null
                    ? ""
                    : escape(task.getDescription());

            String dueDate = task.getDueDate() == null
                    ? "No deadline"
                    : task.getDueDate().toString();

            String status = task.isCompleted()
                    ? "COMPLETED"
                    : "PENDING";

            String html = "<html><div style='padding:8px;'>"
                    + "<b style='font-size:15px;'>"
                    + title
                    + "</b>"
                    + "<br><span style='color:#A0A8C5;'>"
                    + description
                    + "</span>"
                    + "<br><br>"
                    + "<span style='color:#A995FF;'>"
                    + priority
                    + "</span>"
                    + " &nbsp;&nbsp; "
                    + "<span style='color:#A0A8C5;'>"
                    + dueDate
                    + "</span>"
                    + " &nbsp;&nbsp; "
                    + "<span style='color:"
                    + (task.isCompleted() ? "#55D6A0" : "#F0B85A")
                    + ";'>"
                    + status
                    + "</span>"
                    + "</div></html>";

            setText(html);

            setOpaque(true);

            setBackground(selected
                    ? new Color(49, 48, 85)
                    : background);

            setForeground(textColor);

            setBorder(new EmptyBorder(5, 12, 5, 12));

            return this;
        }
    }

    private String escape(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    // Add task
    private void addTask() {

        JTextField titleField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField dateField = new JTextField();

        JComboBox<Priority> priorityBox =
                new JComboBox<>(Priority.values());

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));

        panel.add(new JLabel("Task Title:"));
        panel.add(titleField);

        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        panel.add(new JLabel("Due Date (YYYY-MM-DD):"));
        panel.add(dateField);

        panel.add(new JLabel("Priority:"));
        panel.add(priorityBox);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Create New Task",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {

            String title = titleField.getText().trim();

            String description = descriptionField.getText().trim();

            LocalDate dueDate =
                    LocalDate.parse(dateField.getText().trim());

            Priority priority =
                    (Priority) priorityBox.getSelectedItem();

            Task task = new Task(
                    0,
                    title,
                    description,
                    dueDate,
                    priority,
                    false
            );

            performAndRefresh(() -> taskService.addTask(task));

        } catch (Exception e) {

            showError("Invalid task details. Check the date format.");
        }
    }

    // Complete task
    private void completeTask() {

        Task selectedTask = taskList.getSelectedValue();

        if (selectedTask == null) {
            showError("Please select a task first.");
            return;
        }

        if (selectedTask.isCompleted()) {
            showError("This task is already completed.");
            return;
        }

        selectedTask.setCompleted(true);

        performAndRefresh(() ->
                taskService.updateTask(selectedTask)
        );
    }

    // Delete task
    private void deleteTask() {

        Task selectedTask = taskList.getSelectedValue();

        if (selectedTask == null) {
            showError("Please select a task first.");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this task?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        performAndRefresh(() ->
                taskService.deleteTask(selectedTask.getId())
        );
    }

    // Refresh tasks
    private void refreshTasks() {

        executor.submit(() -> {

            try {

                List<Task> tasks = taskService.getAllTasks();

                SwingUtilities.invokeLater(() -> {

                    listModel.clear();

                    for (Task task : tasks) {
                        listModel.addElement(task);
                    }

                    statusLabel.setText(
                            "Total tasks: " + tasks.size()
                    );
                });

            } catch (TaskException e) {

                SwingUtilities.invokeLater(() ->
                        showError(e.getMessage())
                );
            }
        });
    }

    // Background database operation
    private void performAndRefresh(BackgroundAction action) {

        executor.submit(() -> {

            try {

                action.run();

                List<Task> tasks = taskService.getAllTasks();

                SwingUtilities.invokeLater(() -> {

                    listModel.clear();

                    for (Task task : tasks) {
                        listModel.addElement(task);
                    }

                    statusLabel.setText("Operation completed successfully!");
                });

            } catch (TaskException e) {

                SwingUtilities.invokeLater(() ->
                        showError(e.getMessage())
                );
            }
        });
    }

    // Activity history
    private void showActivities() {

        executor.submit(() -> {

            List<String> activities = taskService.getActivities();

            SwingUtilities.invokeLater(() -> {

                JTextArea textArea = new JTextArea(15, 40);

                textArea.setEditable(false);
                textArea.setBackground(panelColor);
                textArea.setForeground(textColor);
                textArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));

                for (String activity : activities) {
                    textArea.append(activity + "\n");
                }

                JOptionPane.showMessageDialog(
                        this,
                        new JScrollPane(textArea),
                        "Activity History",
                        JOptionPane.INFORMATION_MESSAGE
                );
            });
        });
    }

    // Error messages
    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "ChronoTask",
                JOptionPane.ERROR_MESSAGE
        );
    }

    @FunctionalInterface
    private interface BackgroundAction {
        void run() throws TaskException;
    }
}