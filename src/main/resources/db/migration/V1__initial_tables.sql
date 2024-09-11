CREATE TYPE RoomType AS ENUM ('SINGLE', 'DOUBLE', 'SUITE');
CREATE TYPE ReservationStatus AS ENUM ('CONFIRMED', 'CANCELED');


CREATE TABLE clients (
client_id BIGSERIAL PRIMARY KEY,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
phone VARCHAR(20) NOT NULL
);

CREATE TABLE rooms (
room_id BIGSERIAL PRIMARY KEY,
room_name VARCHAR(50) NOT NULL,
room_type RoomType NOT NULL,
price DECIMAL(10,2) NOT NULL
);

CREATE TABLE reservations (
reservation_id BIGSERIAL PRIMARY KEY,
start_date DATE NOT NULL,
end_date DATE NOT NULL,
room_id BIGINT NOT NULL,
client_id BIGINT NOT NULL,
status ReservationStatus NOT NULL,
CONSTRAINT fk_room
FOREIGN KEY (room_id)
REFERENCES rooms(room_id)
ON DELETE CASCADE,
CONSTRAINT fk_client
FOREIGN KEY (client_id)
REFERENCES clients(client_id)
ON DELETE CASCADE
);