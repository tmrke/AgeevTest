# create table numbers
# (
#     id     int primary key auto_increment,
#     number double
# );
#
# insert into numbers(number) value (10);
# insert into numbers(number) value (20);
# insert into numbers(number) value (30);
# insert into numbers(number) value (10);

drop table result;

create table result
(
    id        int primary key auto_increment,
    result    double,
    operation varchar(100),
    date      datetime,
    message   varchar(100)
);