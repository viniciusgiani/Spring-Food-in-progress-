alter table response add code varchar(36) not null after id;
update response set code = uuid();
alter table response add constraint uk_response_code unique (code);