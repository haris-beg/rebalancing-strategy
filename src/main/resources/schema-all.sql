DROP TABLE yahooHistory IF EXISTS;

CREATE TABLE yahooHistory  (
    history_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    ticker VARCHAR(10),
    priceDate DATE,
    openingPrice FLOAT,
    highPrice FLOAT,
    lowPrice FLOAT,
    closingPrice FLOAT,
    periodVolume BIGINT,
    adjClose FLOAT
);