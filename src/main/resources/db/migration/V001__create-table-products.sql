create table products (
	id bigint not null auto_increment,
	name varchar(255) unique not null,
    description varchar(255) not null,
	price decimal(10,2) unsigned not null,
	
	primary key (id)
) engine=InnoDB default charset=utf8;
