# Registro-Comuni-ISTAT
This project is about handling the list of all cities with every metadata associated

ITA  

Questo progetto mostra come gestire il registro con tutte le informazioni dei vari comuni di Italia.  
Questo registro è rappresentato da un file csv gestito direttamente dall'ISTAT, l'ente ufficiale adibito a gestire questo tipo di informazioni da parte della pubblica amministrazione italiana.  
Questo progetto è pensato per consigliare una soluzione che permetta di automatizzare il download del csv, la verifica che il csv di cui si è appena fatto il download abbia o meno informazioni contenute diverse (lettura del MD5) dall'ultimo csv processato e, in caso positivo, il successivo inserimento in database o aggiornamento del record già presente. Al fine di mantenere uno storico delle informazioni presenti nel database, così come di quelle oramai passate (vuoi perchè per quel comune i metadati sono cambiati o perchè è stato cancellato), alla fine del processo viene fatto un update massivo sia per indicare i comuni ancora validi sia per dare un'indicazione di validità temporale.  
Tutto questo processo viene fatto all'avvio e riproposto ogni mezzanotte.  

La chiave primaria è composta da quelli che reputo essere gli attributi più importanti e necessari per dare l'univocità al comune: il codice catastale, il nome del comune, il nome della provincia, il nome della regione.  
(A volte capita che un comune cambi nome o cambi la provincia di riferimento. L'Italia è un paese speciale, forse anche troppo....)  

Per gestire una chiave primaria composita ho usato il paradigma basato sulla creazione di una IdClass così da poter poi fare la query classica findById passando direttamente l'oggetto piuttosto che dover scrivere una query troppo lunga riportando tutti gli attributi.  
Inoltre, pensando alla possibilità che questo codice possa essere eseguito in un ambiente ridondato, al fine di evitare problemi di concorrenza sulla stessa entità, ho preferito esplicitare l'uso combinato del pessimistic e optimistic locking.  

Le API sono state sviluppate in modo da essere usufruibili secondo il paradigma sia REST sia GraphQL.

ENG  

This project demonstrates how to manage a registry containing information about various municipalities in Italy.  
This registry is represented by a CSV file managed directly by ISTAT, the official agency responsible for handling this type of information on behalf of the Italian public administration.  
The project aims to suggest a solution that automates the downloading of the CSV file, verifying whether the newly downloaded CSV contains different information (by reading the MD5) from the last processed CSV, and, if so, subsequently inserting it into the database or updating the existing record. 
In order to maintain a history of the information in the database, as well as information that is now outdated (either because the metadata for that municipality has changed or because it has been deleted), a massive update is performed at the end of the process to indicate both the still-valid municipalities and provide a temporal validity indication.  
This entire process is carried out at startup and repeated every midnight.  

The primary key is composed of what I consider to be the most important and necessary attributes to uniquely identify a municipality: the cadastral code, the name of the municipality, the name of the province, and the name of the region.  
(Sometimes a municipality changes its name or the reference province. Italy is a special country, perhaps even too much...)  

To handle a composite primary key, I decided to create an IdClass, so that I can then perform the classic findById query by passing the object directly rather than having to write a lengthy query listing all the attributes.  
Furthermore, considering the possibility that this code may be executed in a redundant environment, in order to avoid concurrency issues on the same entity, I chose to explicitly combine pessimistic and optimistic locking.

The API have been developed in order to be used both via REST and GraphQL paradigm



*It is suggested to run this project as container within a docker-compose within a docker network where a container with postgresql is present.*
*Example:*

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:latest
    container_name: postgres-container
    environment:
      POSTGRES_DB: postgres
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: mysecretpassword
    ports:
      - "5432:5432"
    networks:
      - my-network
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 10s
      timeout: 5s
      retries: 5

  app:
    image: registro-comuni-istat:latest
    container_name: spring-app
    environment:
      DB_HOST: postgres
      DB_PORT: 5432
      CRON: "0 19 * * * ?"
    depends_on:
      postgres:
      condition: service_healthy
    ports:
      - "8080:8080"
    networks:
      - net-gio

networks:
  my-network:
    driver: bridge
