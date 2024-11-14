package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.persistence.DBManager;
import com.dipartimento.demowebapplications.persistence.dao.PiattoDao;
import com.dipartimento.demowebapplications.persistence.dao.RistoranteDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PiattoDaoJDBC implements PiattoDao {

    private Connection connection;

    public PiattoDaoJDBC(Connection connection) { this.connection = connection; }

    private void restRelationsPResentInTheJoinTable(Connection connection, String nomePiatto) throws Exception {

        String query="Delete FROM ristorante_piatto WHERE piatto_nome= ? ";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nomePiatto);

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
    public List<Piatto> findAll() {
        List<Piatto> piatti = new ArrayList<Piatto>();

        Statement st = null;
        String query = "select * from piatto";
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Piatto p = new PiattoProxy();
                p.setNome(rs.getString("nome"));
                p.setIngredienti(rs.getString("ingredienti"));
                piatti.add(p);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }

        return piatti;
    }

    @Override
    public Piatto findByPrimaryKey(String nome) {

        String query = "SELECT nome, ingredienti FROM piatto WHERE nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                PiattoProxy p = new PiattoProxy();
                p.setNome(nome);
                p.setIngredienti(rs.getString("ingredienti"));
                return p;
            }

        } catch (Exception e) { e.printStackTrace();}

        return null;
    }

    @Override
    public void save(Piatto piatto) {

        String query = "INSERT INTO piatto (nome, ingredienti) VALUES (?, ?) " +
            "ON CONFLICT (nome) DO UPDATE SET " +
            "   ingredienti = EXCLUDED.ingredienti";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, piatto.getNome());
            statement.setString(2, piatto.getIngredienti());
            statement.executeUpdate();

            List<Ristorante> ristoranti = piatto.getRistoranti();
            if(ristoranti == null || ristoranti.isEmpty()) { return; }

            // reset all relation present in the join table
            restRelationsPResentInTheJoinTable(connection, piatto.getNome());

            RistoranteDao rd = DBManager.getInstance().getRistoranteDao();

            for (Ristorante tempR : ristoranti) {
                rd.save(tempR);
                insertJoinRistorantePiatto(connection , tempR.getNome(), piatto.getNome());
            }

        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void delete(Piatto piatto) {

        String query = "DELETE FROM piatto WHERE nome = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, piatto.getNome());
            statement.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public List<Piatto> findAllByRistoranteName(String ristoranteName) {

        String query = "SELECT nome, ingredienti FROM piatto, ristorante_piatto " +
                "WHERE ristorante_piatto.ristorante_nome = ? " +
                    "and piatto.nome = ristorante_piatto.piatto_nome";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ristoranteName);
            ResultSet rs = statement.executeQuery(query);

            List<Piatto> piatti = new ArrayList<>();
            while (rs.next()){
                Piatto p = new PiattoProxy();
                p.setNome(rs.getString("nome"));
                p.setIngredienti(rs.getString("ingredienti"));
                piatti.add(p);
            }

            return piatti;

        } catch (Exception e) { e.printStackTrace(); }

        return List.of();
    }
}
