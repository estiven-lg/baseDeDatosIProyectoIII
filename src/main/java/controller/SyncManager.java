package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Person;
import model.Transaction;

public class SyncManager {

    public static class SyncItem {

        private Map<String, Transaction> item;
        private Boolean OriginDB1 = false;
        private Boolean OriginDB2 = false;

        public SyncItem(Map<String, Transaction> Item) {
            this.item = Item;

        }

        public Map<String, Transaction> getItem() {
            return item;
        }

        public void setItem(Map<String, Transaction> Item) {
            this.item = Item;
        }

        public Boolean getDB1() {
            return OriginDB1;
        }

        public void setDB1(Boolean DB1) {
            this.OriginDB1 = DB1;
        }

        public Boolean getDB2() {
            return OriginDB2;
        }

        public void setDB2(Boolean DB2) {
            this.OriginDB2 = DB2;
        }

        public String toString() {
            return "Origen1:" + this.OriginDB1 + " , origen2:" + this.OriginDB2 + "|" + this.item.toString();
        }

    }

    public static Map<String, SyncItem> getSyncMap(SqlQueryExecutor DB1, SqlQueryExecutor DB2) throws SQLException {
        List<Transaction> transactionsDB1 = DB1.getAllTransaction();
        List<Transaction> transactionsDB2 = DB2.getAllTransaction();

        Map<String, SyncItem> transactionsMap = new HashMap<>();

        for (Transaction transaction : transactionsDB1) {
            String dpi = transaction.getDpi();
            String field = transaction.getField();

            transactionsMap.putIfAbsent(dpi, new SyncItem(new HashMap<>()));
            SyncItem fieldMap = transactionsMap.get(dpi);

            fieldMap.setDB1(true);
            if (!fieldMap.item.containsKey(field) || fieldMap.item.get(field).getOperationDate().before(transaction.getOperationDate())) {
                fieldMap.item.put(field, transaction);
            }
        }

        for (Transaction transaction : transactionsDB2) {
            String dpi = transaction.getDpi();
            String field = transaction.getField();

            transactionsMap.putIfAbsent(dpi, new SyncItem(new HashMap<>()));

            SyncItem fieldMap = transactionsMap.get(dpi);

            fieldMap.setDB2(true);
            if (!fieldMap.item.containsKey(field) || fieldMap.item.get(field).getOperationDate().before(transaction.getOperationDate())) {
                fieldMap.item.put(field, transaction);
            }
        }

        return transactionsMap;
    }

    public static void sync(SqlQueryExecutor DB1, SqlQueryExecutor DB2) throws SQLException {
        Map<String, SyncItem> items = getSyncMap(DB1, DB2);

        List<Transaction> transactionsDB1 = DB1.getAllTransaction();
        List<Transaction> transactionsDB2 = DB2.getAllTransaction();

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        writeInLog("DB1 sincronizacion -" + timeStamp, transactionsDB1);
        writeInLog("DB2 sincronizacion -" + timeStamp, transactionsDB2);

        for (Map.Entry<String, SyncItem> outerEntry : items.entrySet()) {
            Person person = null;
            String dpi = outerEntry.getKey();
            SyncItem itemSync = outerEntry.getValue();
            System.out.println(dpi);

            Transaction transactionDelete = outerEntry.getValue().item.get("*DELETE*");
            Transaction transactionInsert = outerEntry.getValue().item.get("*INSERT*");

            if (transactionDelete != null
                    && transactionInsert != null
                    && transactionInsert.getOperationDate().before(transactionDelete.getOperationDate())
                    || transactionInsert == null) {

                if (itemSync.OriginDB1) {
                    DB2.deletePerson(dpi);
                }
                if (itemSync.OriginDB2) {
                    DB1.deletePerson(dpi);
                }

                continue;
            }

            if (itemSync.OriginDB1) {
                person = DB1.getPerson(dpi);
            } else if (itemSync.OriginDB2) {
                person = DB2.getPerson(dpi);
            }

            if (person == null) {
                continue;
            }

            if (itemSync.item.containsKey("dpi")) {
                person.setDpi((String) itemSync.item.get("dpi").getNewValue());
            }
            if (itemSync.item.containsKey("first_name")) {
                person.setFirstName((String) itemSync.item.get("first_name").getNewValue());
            }
            if (itemSync.item.containsKey("second_name")) {
                person.setSecondName((String) itemSync.item.get("second_name").getNewValue());
            }
            if (itemSync.item.containsKey("first_surname")) {
                person.setFirstSurname((String) itemSync.item.get("first_surname").getNewValue());
            }
            if (itemSync.item.containsKey("second_surname")) {
                person.setSecondSurname((String) itemSync.item.get("second_surname").getNewValue());
            }
            if (itemSync.item.containsKey("home_address")) {
                person.setHomeAddress((String) itemSync.item.get("home_address").getNewValue());
            }
            if (itemSync.item.containsKey("home_phone")) {
                person.setHomePhone((String) itemSync.item.get("home_phone").getNewValue());
            }
            if (itemSync.item.containsKey("mobile_phone")) {
                person.setMobilePhone((String) itemSync.item.get("mobile_phone").getNewValue());
            }
            if (itemSync.item.containsKey("base_salary")) {
                person.setBaseSalary(Float.parseFloat(itemSync.item.get("base_salary").getNewValue()));
            }
            if (itemSync.item.containsKey("bonus")) {
                person.setBonus(Float.parseFloat(itemSync.item.get("bonus").getNewValue()));
            }

            System.out.println(person);

            if (itemSync.OriginDB1) {
                DB2.upsertPerson(person);
            }
            if (itemSync.OriginDB2) {
                DB1.upsertPerson(person);
            }

        }

        DB1.removeAllTransaction();
        DB2.removeAllTransaction();
    }

    public static void writeInLog(String title, List<Transaction> transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true))) {

            writer.write("******************" + title + "******************\n");

            for (Transaction transaccion : transaction) {
                writer.write(transaccion.toString());
                writer.newLine();
                writer.write("-----------------------------------------------------------------");
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

}
