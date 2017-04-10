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
    adjClose FLOAT,
    tradingDayNum INT
);

DROP TABLE p123History IF EXISTS;
CREATE TABLE p123History  (
    history_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    periodStartDate DATE,
    periodReturn DOUBLE
);

DROP TABLE p123RealizedTxns IF EXISTS;
CREATE TABLE p123RealizedTxns  (
    txn_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    ticker VARCHAR(10),
    positionOpenDate DATE,
    positionCloseDate DATE,
    daysHeld INT
);

DROP TABLE portfolioSnapshots IF EXISTS;
CREATE TABLE portfolioSnapshots  (
    snapshot_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    snapshotDate DATE,
    asset1StartValue DOUBLE,
    asset2StartValue DOUBLE,
    asset1EndValue DOUBLE,
    asset2EndValue DOUBLE
);