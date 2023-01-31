create table if not exists customer(
    id text not null primary key,
    name text not null,
    surname text not null,
    createdBy text not null,
    updatedBy text not null
);