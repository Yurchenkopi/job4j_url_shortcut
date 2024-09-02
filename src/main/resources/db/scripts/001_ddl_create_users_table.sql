create table users (
                        id serial primary key not null,
                        site varchar,
                        login varchar(2000) unique,
                        password varchar(2000)
);