create table numbers
(
    id     int primary key auto_increment,
    number double
);

insert into numbers(number) value (10);
insert into numbers(number) value (20);
insert into numbers(number) value (30);
insert into numbers(number) value (10);

create table result
(
    id     int primary key auto_increment,
    result double,
    date   datetime
);