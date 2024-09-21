
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
