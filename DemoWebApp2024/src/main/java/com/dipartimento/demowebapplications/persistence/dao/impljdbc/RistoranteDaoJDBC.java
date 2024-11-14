package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.persistence.DBManager;
import com.dipartimento.demowebapplications.persistence.dao.PiattoDao;
import com.dipartimento.demowebapplications.persistence.dao.RistoranteDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.List;

public class RistoranteDaoJDBC implements RistoranteDao {

    private Connection connection;

    public RistoranteDaoJDBC(Connection connection) { this.connection = connection; }

    private void restRelationsPResentInTheJoinTable(Connection connection, String nomeRistorante) throws Exception {

        String query="Delete FROM ristorante_piatto WHERE ristorante_nome= ? ";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nomeRistorante);

        preparedStatement.execute();

    }

    private void insertJoinRistorantePiatto(Connection connection, String nomeRistorante, String nomePiatto) throws SQLException {

        String query="INSERT INTO ristorante_piatto (ristorante_nome,piatto_nome) VALUES (? , ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nomeRistorante);
        preparedStatement.setString(2, nomePiatto);

        preparedStatement.execute();

    }


    @Override
    public List<Ristorante> findAll() {
        List<Ristorante> ristoranti = new ArrayList<Ristorante>();

        Statement st = null;
        String query = "select * from ristorante";
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Ristorante rist = new RistoranteProxy();
                rist.setNome(rs.getString("nome"));
                rist.setDescrizione(rs.getString("descrizione"));
                rist.setUbicazione(rs.getString("ubicazione"));
                ristoranti.add(rist);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ristoranti;
    }

    @Override
    public Ristorante findByPrimaryKey(String nome) {

        String query = "SELECT nome, descrizione, ubicazione FROM ristorante WHERE nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                RistoranteProxy rist = new RistoranteProxy();
                rist.setNome(nome);
                rist.setDescrizione(rs.getString("descrizione"));
                rist.setUbicazione(rs.getString("ubicazione"));
                return  rist;
            }

        } catch (Exception e) { e.printStackTrace();}

        return null;
    }

    @Override
    public void save(Ristorante ristorante) {
        String query = "INSERT INTO ristorante (nome, descrizione, ubicazione) VALUES (?, ?, ?) " +
                "ON CONFLICT (nome) DO UPDATE SET " +
                "   descrizione = EXCLUDED.descrizione , "+
                "   ubicazione = EXCLUDED.ubicazione ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ristorante.getNome());
            statement.setString(2, ristorante.getDescrizione());
            statement.setString(3, ristorante.getUbicazione());
            statement.executeUpdate();

            List<Piatto> piatti = ristorante.getPiatti();
            if(piatti == null || piatti.isEmpty()) { return; }

            // reset all relation present in the join table
            restRelationsPResentInTheJoinTable(connection , ristorante.getNome());

            PiattoDao pd = DBManager.getInstance().getPiattoDao();

            for (Piatto tempP : piatti) {
                pd.save(tempP);
                insertJoinRistorantePiatto(connection , ristorante.getNome() , tempP.getNome());
            }

        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void delete(Ristorante ristorante) {

        String query = "DELETE FROM ristorante WHERE nome = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ristorante.getNome());
            statement.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public List<Ristorante> findAllByPiattoName(String piattoName) {

        String query = "SELECT nome, descrizione, ubicazione FROM ristorante, ristorante_piatto " +
                "WHERE ristorante_piatto.piatto_nome = ? " +
                "and ristorante.nome = ristorante_piatto.ristorante_nome";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, piattoName);
            ResultSet rs = statement.executeQuery(query);

            List<Ristorante> ristoranti = new ArrayList<>();
            while (rs.next()){
                Ristorante rist = new RistoranteProxy();
                rist.setNome(rs.getString("nome"));
                rist.setDescrizione(rs.getString("descrizione"));
                rist.setUbicazione(rs.getString("ubicazione"));
                ristoranti.add(rist);
            }

            return ristoranti;

        } catch (Exception e) { e.printStackTrace(); }

        return List.of();
    }
}
