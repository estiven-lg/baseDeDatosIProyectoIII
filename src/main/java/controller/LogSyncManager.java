package controller;

import model.Transaction;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogSyncManager {



    // Constructor que valida si el archivo existe y si no, lo crea
    public LogSyncManager() throws IOException {
        String fileName = "./logSync/bitacora.txt";
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

//    // Método para consultar todas las entradas del archivo
//    public List<Transaction> getEntries() throws IOException, ParseException {
//        List<Transaction> entries = new ArrayList<>();
//        BufferedReader br = new BufferedReader(new FileReader(fileName));
//        String line;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        while ((line = br.readLine()) != null) {
//            String[] properties = line.split(",");
//
//            Transaction transaction = new Transaction(
//                    properties[0],
//                    properties[1],
//                    sdf.parse(properties[2])
//            );
//
//            entries.add(transaction);
//        }
//        br.close();
//        return entries;
//    }
//
//    public String getEntry(String id) throws IOException {
//        BufferedReader br = new BufferedReader(new FileReader(fileName));
//        String line;
//
//        while ((line = br.readLine()) != null) {
//            String[] parts = line.split(",");
//            String currentId = parts[0];
//
//            if (currentId == id) {
//                br.close();
//                return line;  // Retornamos la línea si el ID coincide
//            }
//        }
//
//        br.close();
//        return null;  // Retorna null si no encuentra la entrada con el ID dado
//    }
//
//    // Método para agregar una nueva transaccion al archivo
//    public void addEntry(Transaction entry) throws IOException {
//        FileWriter fw = new FileWriter(fileName, true); // 'true' para agregar al final del archivo
//        BufferedWriter bw = new BufferedWriter(fw);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String line = entry.getId() + "," + entry.getStatus() + "," + sdf.format(entry.getDate());
//        bw.write(line);
//        bw.newLine();
//        bw.close();
//    }
//
//    // Método para actualizar una entrada en el archivo basado en el ID
//    public boolean updateEntry(Transaction entry) throws IOException {
//        File inputFile = new File(fileName);
//        File tempFile = new File("temp.txt");
//
//        BufferedReader br = new BufferedReader(new FileReader(inputFile));
//        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String line;
//        boolean updated = false;
//
//        while ((line = br.readLine()) != null) {
//            String[] parts = line.split(",");
//            String currentId = parts[0];
//
//            if (currentId == entry.getId()) {
//                // Actualizamos la línea con el nuevo status y date
//                String newLine = entry.getId() + "," + entry.getStatus() + "," + sdf.format(entry.getDate());
//                bw.write(newLine);
//                updated = true;
//            } else {
//                // Mantenemos la línea tal como está
//                bw.write(line);
//            }
//            bw.newLine();
//        }
//
//        br.close();
//        bw.close();
//
//        // Reemplazamos el archivo original con el archivo temporal actualizado
//        if (!inputFile.delete()) {
//            System.out.println("No se pudo eliminar el archivo original.");
//            return false;
//        }
//
//        if (!tempFile.renameTo(inputFile)) {
//            System.out.println("No se pudo renombrar el archivo temporal.");
//            return false;
//        }
//
//        return updated;
//    }
//
//    public void upsertEntry(Transaction entry) throws IOException {
//        if (this.entryExists(entry.getId())) {
//            this.updateEntry(entry);
//        } else {
//            this.addEntry(entry);
//        }
//    }
//
//    private boolean entryExists(String id) throws IOException {
//        BufferedReader br = new BufferedReader(new FileReader(fileName));
//        String line;
//
//        while ((line = br.readLine()) != null) {
//            String[] parts = line.split(",");
//            String currentId = parts[0];
//
//            if (currentId.equals(id)) {
//                br.close();
//                return true;  // Retorna true si el ID existe
//            }
//        }
//
//        br.close();
//        return false;  // Retorna false si no encuentra el ID
//    }
}
