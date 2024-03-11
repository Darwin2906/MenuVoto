/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menuvoto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CandidatoDAO {
    private Connection connection;

    public CandidatoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Candidato> obtenerCandidatosPorEleccion(int idEleccion) {
        List<Candidato> candidatos = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM candidatos WHERE idElección = ?")) {
            statement.setInt(1, idEleccion);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Candidato candidato = new Candidato();
                candidato.setId(resultSet.getInt("id"));
                candidato.setNombre(resultSet.getString("nombre"));
                candidato.setPropuesta(resultSet.getString("propuesta"));
                candidato.setFoto(resultSet.getString("foto"));
                candidatos.add(candidato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return candidatos;
    }

    // Puedes implementar otros métodos CRUD según tus necesidades
}
