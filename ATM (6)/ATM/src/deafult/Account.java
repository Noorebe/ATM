package deafult;

import java.util.ArrayList;
import java.util.List;

//Przykład klasy reprezentującej konto użytkownika
public class Account {
 private String nrKarty;
 private String pin;
 private String stanKonta;
 private List<String> historiaTransakcji;

 public Account(String nrKarty, String pin, String stanKonta) {
     this.nrKarty = nrKarty;
     this.pin = pin;
     this.stanKonta = stanKonta;
     this.historiaTransakcji = new ArrayList<>();
 }

 // Getter i setter
 public String getNrKarty() {
     return nrKarty;
 }

 public void setNrKarty(String nrKarty) {
     this.nrKarty = nrKarty;
 }

 public String getPin() {
     return pin;
 }

 public void setPin(String pin) {
     this.pin = pin;
 }

 public String getStanKonta() {
     return stanKonta;
 }

 public void setStanKonta(String stanKonta) {
     this.stanKonta = stanKonta;
 }

 public List<String> getHistoriaTransakcji() {
     return historiaTransakcji;
 }

 public void addHistoriaTransakcji(String transakcja) {
     historiaTransakcji.add(transakcja);
 }

 @Override
 public String toString() {
     return "Account{" +
             "nrKarty='" + nrKarty + '\'' +
             ", pin='" + pin + '\'' +
             ", stanKonta='" + stanKonta + '\'' +
             ", historiaTransakcji=" + historiaTransakcji +
             '}';
 }
}

