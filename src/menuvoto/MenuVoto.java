/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package menuvoto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MenuVoto {
    private static final String URL = "jdbc:mysql://localhost:3306/votasoft";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Establecer la conexión a la base de datos (asegúrate de tener los detalles correctos)
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

                CandidatoDAO candidatoDAO = new CandidatoDAO(connection);

                // Crear la interfaz gráfica
                JFrame frame = new JFrame("Menú de Voto");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);

                JPanel panel = new JPanel();
                frame.getContentPane().add(panel);
                placeComponents(panel, candidatoDAO, connection);

                frame.setVisible(true);

                // Cerrar la conexión a la base de datos al cerrar la aplicación
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private static void placeComponents(JPanel panel, CandidatoDAO candidatoDAO, Connection connection) {
        panel.setLayout(null);

        JButton mostrarCandidatosButton = new JButton("Mostrar Candidatos");
        mostrarCandidatosButton.setBounds(10, 20, 150, 25);
        panel.add(mostrarCandidatosButton);

        mostrarCandidatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idEleccion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la elección:"));
                List<Candidato> candidatos = candidatoDAO.obtenerCandidatosPorEleccion(idEleccion);

                StringBuilder candidatosInfo = new StringBuilder();
                for (Candidato candidato : candidatos) {
                    candidatosInfo.append("ID: ").append(candidato.getId()).append("\n");
                    candidatosInfo.append("Nombre: ").append(candidato.getNombre()).append("\n");
                    candidatosInfo.append("Propuesta: ").append(candidato.getPropuesta()).append("\n");
                    candidatosInfo.append("Foto: ").append(candidato.getFoto()).append("\n");
                    candidatosInfo.append("-----------------------------\n");
                }

                JOptionPane.showMessageDialog(null, candidatosInfo.toString(), "Candidatos", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton votarButton = new JButton("Votar");
        votarButton.setBounds(10, 60, 150, 25);
        panel.add(votarButton);

        votarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idAdministrador = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su ID de administrador:"));
                    int idCandidato = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del candidato para votar:"));
                    int idEleccion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la elección:"));

                    // Obtén la fecha y hora actual
                    java.util.Date utilDate = new java.util.Date();
                    java.sql.Timestamp fechaHoraVoto = new java.sql.Timestamp(utilDate.getTime());

                    // Registra el voto utilizando el VotoDAO
                    VotoDAO votoDAO = new VotoDAO(connection);
                    votoDAO.registrarVoto(idAdministrador, idCandidato, idEleccion, fechaHoraVoto);

                    // Muestra un mensaje de confirmación
                    JOptionPane.showMessageDialog(null, "Voto registrado correctamente", "Votar", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    // Captura la excepción si el usuario ingresa un valor no válido
                    JOptionPane.showMessageDialog(null, "Ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton salirButton = new JButton("Salir");
        salirButton.setBounds(10, 100, 150, 25);
        panel.add(salirButton);

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
