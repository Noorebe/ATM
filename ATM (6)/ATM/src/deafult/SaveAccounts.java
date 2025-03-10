package deafult;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveAccounts {

    public static void main(String[] args) {
        // Przykładowe konta do zapisania
        String[][] konta = {
            {"11", "1", "1000"},
            {"2345678901234567", "4321", "250"},
            {"3456789012345678", "5678", "950"}
        };

        saveAccountsToFile(konta, "Konta.dat");
    }

    // Metoda do zapisu kont do pliku binarnego
    public static void saveAccountsToFile(String[][] konta, String filename) {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(filename))) {
            // Iteracja po każdym koncie
            for (String[] konto : konta) {
                String nrKarty = konto[0];
                String pin = konto[1];
                String stanKonta = konto[2];

                // Zapisz numer karty
                byte[] nrKartyBytes = nrKarty.getBytes("UTF-8");
                out.writeInt(nrKartyBytes.length); // Zapisz długość numeru karty
                out.write(nrKartyBytes); // Zapisz numer karty

                // Zapisz PIN
                byte[] pinBytes = pin.getBytes("UTF-8");
                out.writeInt(pinBytes.length); // Zapisz długość PIN-u
                out.write(pinBytes); // Zapisz PIN

                // Zapisz stan konta
                byte[] stanKontaBytes = stanKonta.getBytes("UTF-8");
                out.writeInt(stanKontaBytes.length); // Zapisz długość stanu konta
                out.write(stanKontaBytes); // Zapisz stan konta
            }

            System.out.println("Kontap została zapisana do pliku: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

