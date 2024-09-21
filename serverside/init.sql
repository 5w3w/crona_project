CREATE DATABASE IF NOT EXISTS crona_adm 
    WITH
    OWNER = crona_adm
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

-- Таблица пользователей (Users)
CREATE TABLE Users (
    ID SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL
);

-- Таблица событий (Events)
CREATE TABLE Events (
    ID SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Organizator INT REFERENCES Users(ID) ON DELETE CASCADE,
    Location VARCHAR(255) NOT NULL,
    MaxCapacity INT,
    CurrCap INT,
    TimeFrom TIMESTAMP,
    TimeTo TIMESTAMP
);

-- Таблица посещений (Visits)
CREATE TABLE Visits (
    UserID INT REFERENCES Users(ID) ON DELETE CASCADE,
    EventID INT REFERENCES Events(ID) ON DELETE CASCADE,
    TimeForm TIMESTAMP,
    PRIMARY KEY (UserID, EventID)
);
