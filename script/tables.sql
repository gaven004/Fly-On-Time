create table airlines
(
    iata_code String,
    name      String,
    location  String
)
    engine = MergeTree PRIMARY KEY iata_code
        ORDER BY iata_code
        SETTINGS index_granularity = 8192;

create table airport
(
    iata_code String,
    name      String,
    city      String
)
    engine = MergeTree PRIMARY KEY iata_code
        ORDER BY iata_code
        SETTINGS index_granularity = 8192;

create table flight
(
    flight_schedules  String,
    airlines          String,
    departure_city    String,
    departure_airport String,
    landing_city      String,
    landing_airport   String,
    schedule_departure_time Nullable(DateTime('Asia/Shanghai')),
    schedule_landing_time Nullable(DateTime('Asia/Shanghai')),
    actual_departure_time Nullable(DateTime('Asia/Shanghai')),
    actual_landing_time Nullable(DateTime('Asia/Shanghai')),
    flight_no Nullable(String),
    status Nullable(String)
)
    engine = MergeTree PRIMARY KEY (departure_airport, landing_airport)
        ORDER BY (departure_airport, landing_airport)
        SETTINGS index_granularity = 8192;

