package com.fateczl.aumatch.repository;

import com.fateczl.aumatch.model.Animal;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AnimalRepositoryImpl implements AnimalRepository{

    String db = "jdbc:mysql://localhost:3306/AUMATCH?useSSL=false";
    String user = "root";
    String password = "maijer1245";

    @Override
    public Animal save(Animal animal) {
        String sql = "INSERT INTO ANIMAL" +
                "  (porte, idade, sexo, tipo, status, nome) VALUES " +
                " (?, ?, ?, ?, ?, ?);";
        try (Connection connection = DriverManager
                .getConnection(db, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, animal.getPorte());
            preparedStatement.setInt(2, animal.getIdade());
            preparedStatement.setString(3, animal.getSexo());
            preparedStatement.setString(4, animal.getTipo());
            preparedStatement.setString(5, animal.getStatus());
            preparedStatement.setString(6, animal.getNome());

            preparedStatement.executeUpdate();
            System.out.println(preparedStatement);

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    animal.setAnimalId(generatedKeys.getLong(1));
                }
                else {
                    throw new RuntimeException("Creating animal failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return animal;
    }

    @Override
    public Animal update(Animal animal){
        String sql = "update ANIMAL set porte = ? , idade = ? , sexo= ? , tipo =? , status = ? , nome = ? where id = ?;";
        try (Connection connection = DriverManager
                .getConnection(db, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            System.out.println(preparedStatement);

            preparedStatement.setString(1, animal.getPorte());
            preparedStatement.setInt(2, animal.getIdade());
            preparedStatement.setString(3, animal.getSexo());
            preparedStatement.setString(4, animal.getTipo());
            preparedStatement.setString(5, animal.getStatus());
            preparedStatement.setString(6, animal.getNome());   
            preparedStatement.setLong(7, animal.getAnimalId());


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return animal;
    }

    @Override
    public Optional<Animal> findById(Long animalId) {
        String sql = "select id,porte,idade,sexo,tipo,status,nome from ANIMAL where id = ?";
        try (Connection connection = DriverManager
                .getConnection(db, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setLong(1, animalId);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Animal animal = new Animal();
                animal.setAnimalId(rs.getInt("id"));
                animal.setPorte(rs.getString("porte"));
                animal.setIdade(rs.getInt("idade"));
                animal.setSexo(rs.getString("sexo"));
                animal.setTipo(rs.getString("tipo"));
                animal.setStatus(rs.getString("status"));
                animal.setNome(rs.getString("nome"));

                return Optional.of(animal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }



    @Override
    public void delete(Long animalId) {
        String sql = "delete from ANIMAL where id = ?";
        try (Connection connection = DriverManager
                .getConnection(db, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, animalId);
            System.out.println(preparedStatement);
            int result = preparedStatement.executeUpdate();

            System.out.println("Number of records affected :: " + result);
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Animal> findAll() {
        String sql = "select id,porte,idade,sexo,tipo,status, nome from ANIMAL";
        List<Animal> animais = new ArrayList<>();
        try (Connection connection = DriverManager
                .getConnection(db, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Animal animal = new Animal();
                animal.setAnimalId(rs.getInt("id"));
                animal.setPorte(rs.getString("porte"));
                animal.setIdade(rs.getInt("idade"));
                animal.setSexo(rs.getString("sexo"));
                animal.setTipo(rs.getString("tipo"));
                animal.setStatus(rs.getString("status"));
                animal.setNome(rs.getString("nome"));

                animais.add(animal);
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return animais;
    }

    @Override
    public Integer getQuantidadeAnimaisProcedure() {
        String sql = "{ call Verificar_Quantidade_ANIMAIS(?) }";
        List<Animal> animais = new ArrayList<>();
        try (Connection connection = DriverManager
                .getConnection(db, user, password);
             CallableStatement callableStatement = connection.prepareCall(sql);) {
            callableStatement.registerOutParameter(1, Types.INTEGER);
            System.out.println(callableStatement);
            callableStatement.executeUpdate();
            Integer result = callableStatement.getInt(1);
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

