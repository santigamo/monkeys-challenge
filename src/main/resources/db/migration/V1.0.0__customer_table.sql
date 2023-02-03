create table if not exists customer(
    id text not null primary key,
    name text not null unique,
    surname text not null,
    avatar text not null,
    createdBy text not null,
    updatedBy text not null
);