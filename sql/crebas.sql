use amsaf;

CREAte table dbversion
(
    dbversion_id    int not null auto_increment,
    version_id      int not null,
    version         varchar(12),
    constraint dbversion_pk primary key (dbversion_id)
);

insert into dbversion values(1,1,'0.1');
update dbversion set version_id=1,version='0.1';


create table user
(
	user_id int not null auto_increment,
	first_name varchar(64) not null,
	last_name varchar(64) not null,
	email varchar(128) not null,
	login varchar(32) not null,
	password varchar(32), 
	updated_at timestamp,
	created_at timestamp,
	primary key user_pk (user_id)
);

create or replace view login as select user_id, email, login as username, password from user;


create table stamps #--- ?
(
	stamps_id int not null auto_increment,
	stamps varchar(32) not null,
	updated_at timestamp,
	created_at timestamp,
	primary key stamps_pk (stamps_id)
);

create table customer
(
	customer_id int not null auto_increment,
	customer_name varchar(255) not null,
	customer_address varchar(512),
	updated_at timestamp,
	created_at timestamp,
	primary key customer_pk (customer_id)
);

insert into customer (customer_id,customer_name,customer_address)
values(1,'Roga & Copyta','Sovetskaya st.1, Chernomorsk, USSR');

insert into customer (customer_id,customer_name,customer_address)
values(2,'Bezenchuk & Nimfy','Plehanova st.12, Stargorod, USSR');

insert into contact(title,first_name,last_name,email,phone,customer_id)
values('Zic-predsedatel','A.A.','Funt','nm60@gmail.com','099-555-4433',1);

insert into contact(title,first_name,last_name,email,phone,customer_id)
values('Curier','M.S.','Panicovsky','nm60@gmail.com','088-666-2211',1);

insert into contact(title,first_name,last_name,email,phone,customer_id)
values('BigBoss','V.A.','Bezenchuck','nm60@gmail.com','077-888-0044',2);

insert into contact(title,first_name,last_name,email,phone,customer_id)
values('Master','I.E.','Bravo-Zhivotovsky','nm60@gmail.com','123-456-7890',2);

create table contact
(
	contact_id int not null auto_increment,
	title varchar(32),
	first_name varchar(64),
	last_name varchar(64) not null,
	email varchar(128),
	phone varchar(32),
	updated_at timestamp,
	created_at timestamp,
	customer_id int not null,
	primary key contact_pk (contact_id),
	constraint contact_customer_fk foreign key (customer_id) references customer (customer_id) on delete cascade	
);

create table item 
(
	item_id int not null auto_increment,
	item_number varchar(64) not null,
	item_name varchar(64) not null,
	item_description varchar(512),
	last_price decimal(8,2),
	updated_at timestamp,
	created_at timestamp,
	primary key item_pk (item_id)
);

create table quote
(
	quote_id int not null auto_increment,
	location varchar(255),
	contactor varchar(255),
	rig_tank_eq varchar(255),
	discount decimal(8,2),
	tax_proc decimal(4,2) default 7.5,
	signature mediumblob,
	updated_at timestamp,
	created_at timestamp,
	primary key quote_pk (quote_id)
);

create table quoteitem
(
	quoteitem_id int not null auto_increment,
	quote_id int not null,
	item_id int not null,
	qty int not null default 1,
	price decimal(8,2),
	updated_at timestamp,
	created_at timestamp,
	primary key quoteitem_pk (quoteitem_id),
	constraint quoteitem_item_fk foreign key (item_id) references item (item_id),
	constraint quoteitem_quote_fk foreign key (quote_id) references quote (quote_id)
);

create table `order`
(
	order_id int not null auto_increment,
	location varchar(255),
	contactor varchar(255),
	rig_tank_eq varchar(255),
	discount decimal(8,2),
	tax_proc decimal(4,2) default 7.5,
	signature mediumblob,
	updated_at timestamp,
	created_at timestamp,
	primary key order_pk (order_id)
);

create table orderitem
(
	orderitem_id int not null auto_increment,
	order_id int not null,
	item_id int not null,
	qty int not null default 1,
	price decimal(8,2),
	updated_at timestamp,
	created_at timestamp,
	primary key orderitem_pk (orderitem_id),
	constraint orderitem_item_fk foreign key (item_id) references item (item_id),
	constraint orderitem_order_fk foreign key (order_id) references `order` (order_id)
);

create table invoice
(
	invoice_id int not null auto_increment,
	location varchar(255),
	contactor varchar(255),
	rig_tank_eq varchar(255),
	discount decimal(8,2),
	tax_proc decimal(4,2) default 7.5,
	signature mediumblob,
	updated_at timestamp,
	created_at timestamp,
	primary key invoice_pk (invoice_id)
);

create table invoiceitem
(
	invoiceitem_id int not null auto_increment,
	invoice_id int not null,
	item_id int not null,
	qty int not null default 1,
	price decimal(8,2),
	updated_at timestamp,
	created_at timestamp,
	primary key invoiceitem_pk (invoiceitem_id),
	constraint invoiceitem_item_fk foreign key (item_id) references item (item_id),
	constraint invoiceitem_invoice_fk foreign key (invoice_id) references invoice (invoice_id)
);

delimiter |

create trigger user_insert before insert on user
for each row
begin
if ifnull(new.created_at,0) = 0 then
   set new.created_at = now();
end if;
end;
|

create trigger customer_insert before insert on customer
for each row
begin
if ifnull(new.created_at,0) = 0 then
   set new.created_at = now();
end if;
end;
|
create trigger contact_insert before insert on contact
for each row
begin
if ifnull(new.created_at,0) = 0 then
   set new.created_at = now();
end if;
end;
|


create trigger stamps_insert before insert on stamps
for each row
begin
if ifnull(new.created_at,0) = 0 then
   set new.created_at = now();
end if;
end;
|

create trigger invoice_insert before insert on invoice
for each row
begin
if ifnull(new.created_at,0) = 0 then
   set new.created_at = now();
end if;
end;
|

create trigger invoiceitem_insert before insert on invoiceitem
for each row
begin
if ifnull(new.created_at,0) = 0 then
   set new.created_at = now();
end if;
end;
|

create trigger quote_insert before insert on quote
for each row
begin
if ifnull(new.created_at,0) = 0 then
   set new.created_at = now();
end if;
end;
|

create trigger quoteitem_insert before insert on quoteitem
for each row
begin
if ifnull(new.created_at,0) = 0 then
   set new.created_at = now();
end if;
end;
|
delimiter ;
