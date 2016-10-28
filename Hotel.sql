---------- SQL DDL for creating the Hotel schema and its relations ----------
----- Make the Hotel schema -----
DROP SCHEMA IF EXISTS Hotel;
CREATE SCHEMA Hotel;
USE Hotel;

----- Create relations for the Hotel schema -----
-- Guest relation --
DROP TABLE IF EXISTS Guest;
CREATE TABLE Guest
(
    gID INT AUTO_INCREMENT,  -- ID for guests
    gName VARCHAR(30),  -- Name for guests
    age INT CHECK (age >= 18),  -- Age of guests
    PRIMARY KEY (gID)
);

-- RoomDetails relation --
DROP TABLE IF EXISTS RoomDetails;
CREATE TABLE RoomDetails
(
    roomType VARCHAR(20),  -- Type of the room (single, double, suite, etc.)
    price DECIMAL(5, 2),  -- Decimal price which has 5 total digits, 2 of which are for the decimal
    capacity INT DEFAULT 1,  -- Maximum number of people that is suggested for the room
    beds INT DEFAULT 1,  -- Number of beds in the room
    bathrooms INT DEFAULT 1,  -- Number of bathrooms in the room
    hasWindows BOOLEAN DEFAULT FALSE,  -- TRUE if room has windows; FALSE otherwise
    PRIMARY KEY (roomType)
);

-- Room relation --
DROP TABLE IF EXISTS Room;
CREATE TABLE Room
(
    rID INT NOT NULL,  -- ID for rooms
    roomType VARCHAR(20),  -- The particular type of the room
    floor INT DEFAULT 2, -- Floor number of the room
    smokingAllowed BOOLEAN DEFAULT FALSE,  -- Whether or not smoking is allowed in the room
    PRIMARY KEY (rID),
    FOREIGN KEY (roomType) REFERENCES RoomDetails (roomType)
);
ALTER TABLE Room AUTO_INCREMENT = 101;

-- PaymentRecord relation --
DROP TABLE IF EXISTS PaymentRecord;
CREATE TABLE PaymentRecord
(
    pID INT AUTO_INCREMENT,  -- ID for payment
    pName VARCHAR(20),  -- Type of payment used (cash, credit card, etc.)
    cost DECIMAL(7, 2),  -- How much the payment was (7 total digits, 2 of which are for right of decimal point)
    updatedAt TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,  -- last time this payment record was updated (for archive purposes)
    PRIMARY KEY (pID)
);
ALTER TABLE PaymentRecord AUTO_INCREMENT = 1001;

-- Booking relation --
DROP TABLE IF EXISTS Booking;
CREATE TABLE Booking
(
    rID INT,  -- Room ID
    gID INT,  -- Guest ID for guest who has booked the matching room ID
    pID INT,  -- Payment ID for how the booking was paid and how much the booking cost
    startDate DATE DEFAULT '0000-00-00',  -- Start date for the booking
    endDate DATE DEFAULT '0000-00-00',  -- End date for the booking
    updatedAt TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,  -- Last time this booking was updated (for archive purposes)
    PRIMARY KEY (rID,  gID, pID),
    FOREIGN KEY (rID) REFERENCES Room (rID) ON UPDATE CASCADE ON DELETE CASCADE,  -- Cascade rID changes
    FOREIGN KEY (gID) REFERENCES Guest (gID) ON UPDATE CASCADE ON DELETE CASCADE,  -- Cascade gID changes
    FOREIGN KEY (pID) REFERENCES PaymentRecord (pID) ON UPDATE CASCADE ON DELETE CASCADE  -- Cascade pID changes
);

-- BookingArchive relation (for archived Bookings) --
DROP TABLE IF EXISTS BookingArchive;
CREATE TABLE BookingArchive
(
    rID INT,  -- Room ID
    gID INT,  -- Guest ID for guest who has booked the matching room ID
    pID INT,  -- Payment ID for how the booking was paid and how much the booking cost
    startDate DATE DEFAULT '0000-00-00',  -- Start date for the booking
    endDate DATE DEFAULT '0000-00-00',  -- End date for the booking
    updatedAt TIMESTAMP NOT NULL,  -- Last time this booking was updated (for archive purposes)
    PRIMARY KEY (rID,  gID, pID)
);

