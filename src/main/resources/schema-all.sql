DROP TABLE yahooHistory IF EXISTS;

CREATE TABLE yahooHistory  (
    history_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    priceDate DATE,
    openingPrice FLOAT,
    highPrice FLOAT,
    lowPrice FLOAT,
    closingPrice FLOAT,
    daysVolume BIGINT,
    adjClose FLOAT
);