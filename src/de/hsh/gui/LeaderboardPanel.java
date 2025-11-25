package de.hsh.gui;

import de.hsh.persistence.LeaderboardEntry;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LeaderboardPanel extends JPanel {
    private GUIController guiController;
    private JTable leaderboardTable;
    private DefaultTableModel tableModel;

    public LeaderboardPanel(GUIController guiController) {
        this.guiController = guiController;
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("🏆 LEADERBOARD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 215, 0));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columnNames = { "Rank", "Player", "Score", "Level", "Date" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        leaderboardTable = new JTable(tableModel);
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 14));
        leaderboardTable.setRowHeight(30);
        leaderboardTable.setShowGrid(true);
        leaderboardTable.setGridColor(new Color(100, 100, 150));
        leaderboardTable.setBackground(new Color(30, 30, 60, 200));
        leaderboardTable.setForeground(Color.WHITE);
        leaderboardTable.setSelectionBackground(new Color(100, 100, 200));
        leaderboardTable.setSelectionForeground(Color.WHITE);

        // Header styling
        leaderboardTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        leaderboardTable.getTableHeader().setBackground(new Color(50, 50, 100));
        leaderboardTable.getTableHeader().setForeground(new Color(255, 215, 0));
        leaderboardTable.getTableHeader().setReorderingAllowed(false);

        // Center align all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(new Color(30, 30, 60, 200));
        centerRenderer.setForeground(Color.WHITE);
        for (int i = 0; i < leaderboardTable.getColumnCount(); i++) {
            leaderboardTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Column widths
        leaderboardTable.getColumnModel().getColumn(0).setPreferredWidth(60); // Rank
        leaderboardTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Player
        leaderboardTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Score
        leaderboardTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Level
        leaderboardTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Date

        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = createStyledButton("← BACK TO MENU", new Color(100, 100, 100));
        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) getParent().getLayout();
            cl.show(getParent(), "Hauptmenü");
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load leaderboard data
        refreshLeaderboard();
    }

    public void refreshLeaderboard() {
        tableModel.setRowCount(0);
        List<LeaderboardEntry> entries = guiController.getAppFassade().getPersistence().getTop10();

        int rank = 1;
        for (LeaderboardEntry entry : entries) {
            String rankStr = rank <= 3 ? getRankEmoji(rank) + " " + rank : String.valueOf(rank);
            tableModel.addRow(new Object[] {
                    rankStr,
                    entry.getPlayerName(),
                    entry.getScore(),
                    entry.getLevel(),
                    entry.getFormattedDate()
            });
            rank++;
        }

        // Add empty rows if less than 10
        while (rank <= 10) {
            tableModel.addRow(new Object[] { rank, "-", "-", "-", "-" });
            rank++;
        }
    }

    private String getRankEmoji(int rank) {
        switch (rank) {
            case 1:
                return "🥇";
            case 2:
                return "🥈";
            case 3:
                return "🥉";
            default:
                return "";
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Gradient background
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(20, 20, 50),
                0, getHeight(), new Color(50, 20, 80));
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}
