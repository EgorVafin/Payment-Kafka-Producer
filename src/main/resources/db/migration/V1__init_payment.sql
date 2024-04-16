create table payment (amount numeric(5,2) not null, currency varchar(5) not null,
                      created_at timestamp(6) not null, id bigserial not null,
                      inquiry_ref_id bigint not null, transaction_ref_id bigint not null unique,
                      updated_at timestamp(6) not null,
                      status varchar(128) check (status in ('BOOKED','PENDING')), primary key (id));
