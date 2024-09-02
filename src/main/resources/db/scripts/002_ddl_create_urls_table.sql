CREATE TABLE urls (
                        id serial primary key not null,
                        url varchar unique ,
                        code varchar(12),
                        total int default 0 not null,
                        user_id int references users(id)
);