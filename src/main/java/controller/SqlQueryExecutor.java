package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Person;
import model.Transaction;

public class SqlQueryExecutor {

    Statement statement;

    public SqlQueryExecutor(Statement statement) {
        this.statement = statement;
    }

    // Método para generar consulta SQL para insertar una nueva persona
    public Person getPerson(String dpi) throws SQLException {
        String query = "SELECT * FROM person" + " WHERE dpi = '" + dpi + "'";
        return Person.parseResultSetToPerson(statement.executeQuery(query));
    }

    // Método para generar consulta SQL para insertar una nueva persona
    public void createPerson(Person person) throws SQLException {
        String query = "INSERT INTO person (dpi, first_name, second_name, first_surname, second_surname, "
                + "home_address, home_phone, mobile_phone, base_salary, bonus) VALUES ('"
                + person.getDpi() + "', '" + person.getFirstName() + "', '" + person.getSecondName() + "', '"
                + person.getFirstSurname() + "', '" + person.getSecondSurname() + "', '"
                + person.getHomeAddress() + "', '" + person.getHomePhone() + "', '"
                + person.getMobilePhone() + "', " + person.getBaseSalary() + ", " + person.getBonus() + ")";
        statement.executeUpdate(query);
    }

    // Método para generar consulta SQL para actualizar los datos de una persona
    public void updatePerson(Person person) throws SQLException {
        String query = "UPDATE person SET first_name = '" + person.getFirstName() + "', second_name = '"
                + person.getSecondName() + "', first_surname = '" + person.getFirstSurname()
                + "', second_surname = '" + person.getSecondSurname() + "', home_address = '"
                + person.getHomeAddress() + "', home_phone = '" + person.getHomePhone()
                + "', mobile_phone = '" + person.getMobilePhone() + "', base_salary = "
                + person.getBaseSalary() + ", bonus = " + person.getBonus()
                + " WHERE dpi = '" + person.getDpi() + "'";
        statement.executeUpdate(query);
    }

    // Método para generar consulta SQL para actualizar los datos de una persona
    public void upsertPerson(Person person) throws SQLException {
        try {
            this.createPerson(person);
        } catch (SQLException ex) {

            // Si el INSERT falla, probablemente ya existe el registro, entonces hacer el UPDATE
            System.out.println(ex.getSQLState());
            if (ex.getSQLState().equals("23000") || ex.getSQLState().equals("23505")) {
                System.out.println(ex.getSQLState() + "esta persona ya existe pero la vamos a actualizar");
                this.updatePerson(person);
            } else {
                Logger.getLogger(SqlQueryExecutor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    // Método para generar consulta SQL para eliminar una persona
    public void deletePerson(String dpi) throws SQLException {
        String query = "DELETE FROM person WHERE dpi = '" + dpi + "'";
        statement.execute(query);
    }

    // Método para obtener todas las personas de la tabla
    public List<Person> getAllPersons() throws SQLException {
        String query = "SELECT * FROM person ORDER BY first_name,second_name, first_surname, second_surname";
        ResultSet resultSet = statement.executeQuery(query);

        // Devolver la lista de personas
        return Person.parseResultSetToPersonList(resultSet);
    }

    // Método para obtener todas las personas de la tabla
    public List<Transaction> getAllTransaction() throws SQLException {
        String query = "SELECT * FROM transaction";
        ResultSet resultSet = statement.executeQuery(query);

        // Devolver la lista de tracciones
        return Transaction.parseResultSetToTransactionList(resultSet);
    }

    // Método para REMOVER todas las personas de la tabla
    public void removeAllTransaction() throws SQLException {
        String query = "DELETE FROM transaction";
        statement.execute(query);
    }

}
