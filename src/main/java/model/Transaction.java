package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Transaction {

    private int id;
    private String dpi;
    private String field;
    private String oldValue;
    private String newValue;
    private String operationType;
    private Date operationDate;

    public Transaction(int id, String dpi, String field, String oldValue, String newValue, String operationType, Date operationDate) {
        this.id = id;
        this.dpi = dpi;
        this.field = field;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.operationType = operationType;
        this.operationDate = operationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public static Transaction parseResultSetToTransaction(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String dpi = resultSet.getString("dpi");
        String field = resultSet.getString("field");
        String oldValue = resultSet.getString("old_value");
        String newValue = resultSet.getString("new_value");
        String operationType = resultSet.getString("operation_type");
        Date operationDate = resultSet.getTimestamp("operation_date");

        // Retornar una nueva instancia de Transaction
        return new Transaction(id, dpi, field, oldValue, newValue, operationType, operationDate);
    }

    // Método para convertir un ResultSet a una lista de objetos Transaction
    public static List<Transaction> parseResultSetToTransactionList(ResultSet resultSet) throws SQLException {
        List<Transaction> transactionList = new ArrayList<>();

        // Iterar sobre cada fila del ResultSet
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String dpi = resultSet.getString("dpi");
            String field = resultSet.getString("field");
            String oldValue = resultSet.getString("old_value");
            String newValue = resultSet.getString("new_value");
            String operationType = resultSet.getString("operation_type");
            Date operationDate = resultSet.getTimestamp("operation_date");

            // Crear un nuevo objeto Transaction y agregarlo a la lista
            Transaction transaction = new Transaction(id, dpi, field, oldValue, newValue, operationType, operationDate);
            transactionList.add(transaction);
        }

        // Retornar la lista de Transaction
        return transactionList;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return String.format("""
                             \tID de Transacci\u00f3n: %d
                             \tDPI: %s
                             \tCampo Modificado: %s
                             \tValor Anterior: %s
                             \tValor Nuevo: %s
                             \tTipo de Operacion: %s
                             \tFecha de Operacion: %s""",
                id, dpi, field,
                oldValue != null ? oldValue : "N/A",
                newValue != null ? newValue : "N/A",
                operationType,
                dateFormat.format(operationDate)
        );
    }

}
