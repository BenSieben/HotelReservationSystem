-- SQL DDL for creating the Hotel schema and its relations --
-- Make the Hotel schema --
DROP SCHEMA IF EXISTS Hotel;
CREATE SCHEMA Hotel;
USE Hotel;

-- Create relations for the Hotel schema --

-- Customer relation --
DROP TABLE IF EXISTS Customer;
CREATE TABLE Customer
(
    customer_id BIGINT AUTO_INCREMENT,  -- ID for customer
    first_name VARCHAR(35) NOT NULL, -- Customer's first name
    last_name VARCHAR(35), -- Customer's last name
    username VARCHAR(20) NOT NULL, -- Customer's username
    password VARCHAR(20) NOT NULL, -- Customer's last name
    age INT NOT NULL CHECK (age >= 18),  -- Customer's age
    PRIMARY KEY (customer_id),
    CONSTRAINT username_constraint UNIQUE (username)
);
ALTER TABLE Customer AUTO_INCREMENT = 1;

-- Guest relation --
DROP TABLE IF EXISTS Guest;
CREATE TABLE Guest
(
    guest_id BIGINT AUTO_INCREMENT,  -- ID for guest
    age INT NOT NULL,  -- Age of guest
    PRIMARY KEY (guest_id)
);
ALTER TABLE Guest AUTO_INCREMENT = 11;

-- Customer_Guest relation --
DROP TABLE IF EXISTS Customer_Guest;
CREATE TABLE Customer_Guest
(
    customer_id BIGINT,  -- Primary Key from Customer
    guest_id BIGINT,  -- Primary Key from Guest
    FOREIGN KEY (customer_id) REFERENCES Customer (customer_id) ON UPDATE CASCADE ON DELETE CASCADE,  -- Cascade customer_id changes
    FOREIGN KEY (guest_id) REFERENCES Guest (guest_id) ON UPDATE CASCADE ON DELETE CASCADE  -- Cascade guest_id changes
);

-- Room relation --
DROP TABLE IF EXISTS Room;
CREATE TABLE Room
(
    room_id BIGINT AUTO_INCREMENT,  -- ID for rooms
    room_number INT NOT NULL,
    CONSTRAINT room_number_constraint UNIQUE (room_number),
    PRIMARY KEY (room_id)
);
ALTER TABLE Room AUTO_INCREMENT = 101;

-- Details relation --
DROP TABLE IF EXISTS Details;
CREATE TABLE Details
(
    details_id BIGINT AUTO_INCREMENT,
    price DECIMAL(5, 2),  -- Decimal price which has 5 total digits, 2 of which are for the decimal
    room_type VARCHAR(20),  -- The particular type of the room
    floor INT DEFAULT 2, -- Floor number of the room
    capacity INT DEFAULT 1,  -- Maximum number of people that is suggested for the room
    beds INT DEFAULT 1,  -- Number of beds in the room
    bathrooms INT DEFAULT 1,  -- Number of bathrooms in the room
    has_windows BOOLEAN DEFAULT FALSE,  -- TRUE if room has windows; FALSE otherwise
    smoking_allowed BOOLEAN DEFAULT FALSE,  -- Whether or not smoking is allowed in the room
    PRIMARY KEY (details_id)
);
ALTER TABLE Details AUTO_INCREMENT = 1001;

-- Room_Details relation --
DROP TABLE IF EXISTS Room_Details;
CREATE TABLE Room_Details
(
  room_id BIGINT,  -- Primary Key from Room
  details_id BIGINT,  -- Primary Key from Details
  FOREIGN KEY (room_id) REFERENCES Room (room_id) ON UPDATE CASCADE ON DELETE CASCADE,  -- Cascade room_id changes
  FOREIGN KEY (details_id) REFERENCES Details (details_id) ON UPDATE CASCADE ON DELETE CASCADE  -- Cascade details_id changes
);

-- Payment relation --
DROP TABLE IF EXISTS Payment;
CREATE TABLE Payment
(
    payment_id BIGINT AUTO_INCREMENT,  -- ID for payment
    payment_type ENUM('cash', 'visa', 'mastercard', 'paypal', 'prepaid', 'check') NOT NULL,  -- Type of payment used
    amount DECIMAL(7, 2) NOT NULL,  -- How much the payment was (7 total digits, 2 of which are for right of decimal point)
    updated_at TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,  -- last time this payment record was updated (for archive purposes)
    PRIMARY KEY (payment_id)
);
ALTER TABLE Payment AUTO_INCREMENT = 10001;

-- Period relation --
DROP TABLE IF EXISTS Period;
CREATE TABLE Period
(
    period_id BIGINT AUTO_INCREMENT,  -- Room ID
    start_date DATETIME NOT NULL,  -- Start date for the booking
    end_date DATETIME NOT NULL,  -- End date for the booking
    PRIMARY KEY (period_id)
);
ALTER TABLE Period AUTO_INCREMENT = 100001;

-- Booking relation --
DROP TABLE IF EXISTS Booking;
CREATE TABLE Booking
(
    booking_id BIGINT AUTO_INCREMENT,
    updated_at TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,  -- Last time this booking was updated (for archive purposes)
    PRIMARY KEY(booking_id)
);
ALTER TABLE Booking AUTO_INCREMENT = 1000001;

-- Booking_Period relation --
DROP TABLE IF EXISTS Booking_Period;
CREATE TABLE Booking_Period
(
  booking_id BIGINT, -- Primary Key from Booking
  period_id BIGINT, -- Primary Key from Date
  FOREIGN KEY (booking_id) REFERENCES Booking (booking_id) ON UPDATE CASCADE ON DELETE CASCADE,  -- Cascade booking_id changes
  FOREIGN KEY (period_id) REFERENCES Period (period_id) ON UPDATE CASCADE ON DELETE CASCADE  -- Cascade period_id changes
);

-- Booking_Payment relation --
DROP TABLE IF EXISTS Booking_Payment;
CREATE TABLE Booking_Payment
(
  booking_id BIGINT, -- Primary Key from Booking
  payment_id BIGINT, -- Primary Key from Payment
  FOREIGN KEY (booking_id) REFERENCES Booking (booking_id) ON UPDATE CASCADE ON DELETE CASCADE,  -- Cascade booking_id changes
  FOREIGN KEY (payment_id) REFERENCES Payment (payment_id) ON UPDATE CASCADE ON DELETE CASCADE  -- Cascade payment_id changes
);

-- Booking_Room relation --
DROP TABLE IF EXISTS Booking_Room;
CREATE TABLE Booking_Room
(
  booking_id BIGINT, -- Primary Key from Booking
  room_id BIGINT, -- Primary Key from Room
  FOREIGN KEY (booking_id) REFERENCES Booking (booking_id) ON UPDATE CASCADE ON DELETE CASCADE,  -- Cascade booking_id changes
  FOREIGN KEY (room_id) REFERENCES Room (room_id) ON UPDATE CASCADE ON DELETE CASCADE  -- Cascade room_id changes
);

-- Booking_Customer relation --
DROP TABLE IF EXISTS Booking_Customer;
CREATE TABLE Booking_Customer
(
  booking_id BIGINT, -- Primary Key from Booking
  customer_id BIGINT, -- Primary Key from Customer
  FOREIGN KEY (booking_id) REFERENCES Booking (booking_id) ON UPDATE CASCADE ON DELETE CASCADE,  -- Cascade booking_id changes
  FOREIGN KEY (customer_id) REFERENCES Customer (customer_id) ON UPDATE CASCADE ON DELETE CASCADE  -- Cascade customer_id changes
);