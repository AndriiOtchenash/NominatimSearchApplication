DROP TABLE IF EXISTS search_entity;

CREATE TABLE search_entity(
    id INT AUTO_INCREMENT PRIMARY KEY,
    query VARCHAR(250) NOT NULL,
    latitude VARCHAR(250) NOT NULL,
    longitude VARCHAR(250) NOT NULL
)
