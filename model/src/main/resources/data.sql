CREATE TABLE coordinate (
                            coordinates_id SERIAL PRIMARY KEY,
                            x_coordinate INTEGER NOT NULL,
                            y_coordinate INTEGER NOT NULL,
                            UNIQUE (x_coordinate, y_coordinate)
);

CREATE TABLE location (
                          location_id SERIAL PRIMARY KEY,
                          x_coordinate FLOAT NOT NULL,
                          y_coordinate INTEGER NOT NULL,
                          z_coordinate INTEGER NOT NULL,
                          UNIQUE (x_coordinate, y_coordinate, z_coordinate)
);

CREATE TABLE named_location (
                                location_id SERIAL PRIMARY KEY,
                                x_coordinate INTEGER NOT NULL,
                                y_coordinate INTEGER NOT NULL,
                                z_coordinate INTEGER NOT NULL,
                                name VARCHAR(160) NOT NULL,
                                UNIQUE (x_coordinate, y_coordinate, z_coordinate)
);

CREATE TABLE route (
                       route_id SERIAL PRIMARY KEY,
                       name VARCHAR(160) NOT NULL,
                       coordinates_id INTEGER REFERENCES coordinate(coordinates_id) NOT NULL,
                       creation_date TIMESTAMP NOT NULL,
                       from_location INTEGER REFERENCES location(location_id) NOT NULL,
                       to_location INTEGER REFERENCES named_location(location_id),
                       distance INTEGER,
                       CHECK (distance > 1)
);

CREATE TABLE ticket (
                        ticket_id SERIAL PRIMARY KEY,
                        direction INTEGER REFERENCES route(route_id) NOT NULL,
                        buy_date TIMESTAMP NOT NULL,
                        price REAL,
                        CHECK (price > 0)
);

CREATE TABLE passenger (
                           passenger_id VARCHAR(8) PRIMARY KEY,
                           name VARCHAR(64) NOT NULL,
                           surname VARCHAR(64) NOT NULL,
                           birth_date TIMESTAMP
);

INSERT INTO coordinate (x_coordinate, y_coordinate) VALUES (1, 1),
                                                                           (1, 2),
                                                                           (1, 3),
                                                                           (1, 4),
                                                                           (2, 1),
                                                                           (2, 2),
                                                                           (2, 3),
                                                                           (2, 4),
                                                                           (3, 1),
                                                                           (3, 2),
                                                                           (3, 3),
                                                                           (3, 4),
                                                                           (4, 1),
                                                                           (4, 2),
                                                                           (4, 3),
                                                                           (4, 4);

INSERT INTO location (x_coordinate, y_coordinate, z_coordinate) VALUES (1, 1, 1), (1, 2, 1);

INSERT INTO named_location (x_coordinate, y_coordinate, z_coordinate, name) VALUES (2, 1, 1, 'Moscow'), (2, 2, 1, 'Spb');

INSERT INTO route (name, coordinates_id, creation_date, from_location, to_location, distance) VALUES ('first', 1, '28-10-2022', 1, 1, 40), ('second', 2, '30-10-2022', 2, 2, 30);
