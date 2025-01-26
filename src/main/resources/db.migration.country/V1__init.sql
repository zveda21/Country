create extension if not exists "uuid-ossp";

-- Create table country if it doesn't exist
create table if not exists "country"
(
    id       UUID unique  not null default uuid_generate_v1() primary key,
    country_name varchar(255) not null,
    country_code varchar(50)  not null
    );
