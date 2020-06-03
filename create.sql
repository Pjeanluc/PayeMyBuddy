create table account (id bigint not null auto_increment, balance Decimal(8,2) not null, currency integer not null, user_id bigint, primary key (id)) engine=InnoDB;
create table bank (id bigint not null, bic_code varchar(100) not null, name_bank varchar(100), user_id bigint, primary key (id)) engine=InnoDB;
create table banktransfer (id bigint not null, bank_id bigint, primary key (id)) engine=InnoDB;
create table credit (transactionid varchar(100) not null, type_credit varchar(30) not null, id bigint not null, primary key (id)) engine=InnoDB;
create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
create table movement (id bigint not null, comment varchar(100) not null, amount Decimal(8,2) not null, date_movement datetime not null, sens varchar(1) not null, account_id bigint, primary key (id)) engine=InnoDB;
create table transfer (fees Decimal(8,2) not null, id bigint not null, buddy_id bigint, primary key (id)) engine=InnoDB;
create table user (id bigint not null, email varchar(255) not null, firstname varchar(100) not null, lastname varchar(100) not null, password varchar(100) not null, pseudo varchar(100) not null, primary key (id)) engine=InnoDB;
create table user_buddies (user_id bigint not null, buddies_id bigint not null) engine=InnoDB;
alter table user add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);
alter table account add constraint fk_account_user foreign key (user_id) references user (id);
alter table bank add constraint fk_bank_user foreign key (user_id) references user (id);
alter table banktransfer add constraint fk_banktransfert_bank foreign key (bank_id) references bank (id);
alter table banktransfer add constraint fk_banktransfert_movement foreign key (id) references movement (id);
alter table credit add constraint fk_credit_movement foreign key (id) references movement (id);
alter table movement add constraint fk_movement_account foreign key (account_id) references account (id);
alter table transfer add constraint fk_transfer_user foreign key (buddy_id) references user (id);
alter table transfer add constraint fk_transfer_movement foreign key (id) references movement (id);
alter table user_buddies add constraint fk_buddybuddyid_userid foreign key (buddies_id) references user (id);
alter table user_buddies add constraint fk_buddyuserid_userid foreign key (user_id) references user (id);