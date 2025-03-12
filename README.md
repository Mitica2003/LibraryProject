# Digital Library

Acest proiect este o aplicație software pentru gestionarea unei librării digitale. Aplicația permite adăugarea, ștergerea, căutarea și gestionarea cărților într-o bază de date, oferind o interfață prietenoasă pentru utilizatori.

## Caracteristici
- Adăugarea și ștergerea cărților din librărie
- Căutare avansată după titlu, autor sau gen
- Gestionarea utilizatorilor și a împrumuturilor
- Interfață intuitivă

## Tehnologii utilizate
- Java
- Gradle
- SQLite/MySQL (pentru stocarea datelor)
- Hibernate (pentru gestionarea bazei de date)
- JavaFX/Swing (pentru interfața grafică)

## Structura proiectului
Proiectul este organizat pe pachete, fiecare având un rol specific:

- **controller** – gestionează logica aplicației și interacțiunea cu utilizatorul
- **database** – inițializează conexiunea cu baza de date
- **launcher** – clasa principală care pornește aplicația
- **mapper** – transformă obiectele din baza de date în modele utilizabile
- **model** – definește entitățile utilizate în aplicație
  - **builder** – construiește obiecte model
  - **validator** – validează datele introduse
- **repository** – gestionează operațiunile CRUD pentru diverse entități
  - **book** – operațiuni legate de cărți
  - **security** – gestionarea securității utilizatorilor
  - **user** – manipularea datelor utilizatorilor
- **service** – implementează logica de business
  - **book** – servicii pentru manipularea cărților
  - **pdf** – generarea de rapoarte PDF
  - **user** – servicii pentru utilizatori
- **view** – gestionează interfața grafică
  - **model** – modele pentru interfața grafică
  - **builder** – construirea componentelor UI

## Contribuții
Orice contribuție este binevenită! Pentru a contribui, vă rugăm să anunțați creatorul proiectului.
