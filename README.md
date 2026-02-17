# Progetto Rubrica - Java Desktop Application

Applicazione desktop sviluppata in Java per la gestione di una rubrica contatti con interfaccia grafica Swing e persistenza su file locale.

---

## Obiettivo

Realizzare un'applicazione desktop che consenta di:

- Inserire un nuovo contatto
- Modificare un contatto esistente
- Eliminare un contatto
- Salvare automaticamente i dati su file
- Caricare automaticamente i dati all'avvio

---

## Architettura

Il progetto è strutturato secondo una separazione delle responsabilità:

- **Repository** → gestione dati e persistenza su file
- **TableModel** → adattatore tra modello dati e JTable
- **GUI (Swing)** → gestione interfaccia utente



---

## Persistenza

I dati vengono salvati in un file locale `informazioni.txt`  
Formato riga:

Nome;Cognome;Indirizzo;Telefono;Eta

Il file viene creato automaticamente se non presente.

---

## Tecnologie Utilizzate

- Java 11
- Java Swing
- JTable
- File I/O

---


