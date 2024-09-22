-- Таблица пользователей (Users)
CREATE TABLE IF NOT EXISTS Users (
    ID SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255),
    Password VARCHAR(255) NOT NULL
);

-- Таблица событий (Events)
CREATE TABLE IF NOT EXISTS Events (
    ID SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Organizator INT REFERENCES Users(ID) ON DELETE CASCADE,
    Location VARCHAR(255) NOT NULL,
    max_capacity INT,
    curr_cap INT,
    time_from TIMESTAMP,
    time_to TIMESTAMP
);

-- Таблица посещений (Visits)
CREATE TABLE IF NOT EXISTS Visits (
    user_id INT REFERENCES Users(ID) ON DELETE CASCADE,
    event_id INT REFERENCES Events(ID) ON DELETE CASCADE,
    time_from TIMESTAMP,
    PRIMARY KEY (user_id, event_id)
);