/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menuvoto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VotoDAO {
    private Connection connection;

    public VotoDAO(Connection connection) {
        this.connection = connection;
    }

    public void registrarVoto(int idAdministrador, int idCandidato, int idEleccion, java.sql.Timestamp fechaHoraVoto) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO votos (idAdministrador, idCandidato, idElección, fechaHoraVoto) VALUES (?, ?, ?, ?)")) {
            statement.setInt(1, idAdministrador);
            statement.setInt(2, idCandidato);
            statement.setInt(3, idEleccion);
            statement.setTimestamp(4, fechaHoraVoto);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
