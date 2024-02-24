-- Criação do ENUM para o tipo de cozinha
CREATE TYPE kitchen_type AS ENUM ('JAPONESA', 'PASTA', 'REGIONAL'); -- Substitua 'TIPO1', 'TIPO2', 'TIPO3' pelos tipos de cozinha reais.

-- Criação da tabela Customer
CREATE TABLE Customer (
    id UUID PRIMARY KEY,
    login VARCHAR(124) NOT NULL,
    password VARCHAR(60) NOT NULL,
    lastname VARCHAR(60),
    firstname VARCHAR(60),
    email VARCHAR(124) NOT NULL,
    phone VARCHAR(24)
);

-- Criação da tabela Restaurant
CREATE TABLE Restaurant (
    id UUID PRIMARY KEY,
    name VARCHAR(60) NOT NULL UNIQUE,
    address VARCHAR(120) NOT NULL,
    kitchenType kitchen_type NOT NULL,
    openTime TIME NOT NULL,
    closeTime TIME NOT NULL,
    capacity INTEGER NOT NULL,
    stars INTEGER DEFAULT 0
);

-- Criação da tabela Reservation
CREATE TABLE Reservation (
    id UUID PRIMARY KEY,
    reservationDate TIMESTAMP NOT NULL,
    quantity INTEGER,
    customerID UUID REFERENCES Customer(id),
    restaurantID UUID REFERENCES Restaurant(id)
);

-- Criação da tabela Avaliation
CREATE TABLE Avaliation (
    id UUID PRIMARY KEY,
    customerID UUID REFERENCES Customer(id),
    restaurantID UUID REFERENCES Restaurant(id),
    reservationID UUID REFERENCES Reservation(id),
    stars INTEGER
);

