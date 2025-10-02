package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // se crea la ventana principal del juego
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Juego de puzzles");

        // etiqueta para mostrar los mensajes de los objetos interactivos
        JLabel subtitle = new JLabel(" ", SwingConstants.CENTER);
        subtitle.setFont(new JLabel().getFont().deriveFont(16f));
        subtitle.setForeground(java.awt.Color.BLACK);

        // se crea el panel de juego y se le pasa la etiqueta del subtitulo
        GamePanel gamePanel = new GamePanel(subtitle);

        // se usa borderlayout para organizar la ventana como lo hizo santi
        window.setLayout(new java.awt.BorderLayout());
        window.add(gamePanel, java.awt.BorderLayout.CENTER);
        window.add(subtitle, java.awt.BorderLayout.SOUTH);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}