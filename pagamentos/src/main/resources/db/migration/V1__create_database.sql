CREATE TABLE pagamentos (
    id bigint not null auto_increment ,
    valor decimal(19,2) not null ,
    nome varchar(100) not null,
    numero varchar(19) not null,
    expiracao varchar(7) not null,
    codigo varchar(3) not null,
    status varchar(255) not null,
    forma_de_pagamento_id bigint not null,
    pedido_id bigint not null,
    primary key(id)
);