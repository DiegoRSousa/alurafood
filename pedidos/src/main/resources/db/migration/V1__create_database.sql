CREATE TABLE pedidos (
    id bigint not null auto_increment,
    instante timestamp not null,
    status varchar(15) not null,
    primary key (id)
);

CREATE TABLE itens_do_pedido (
    id bigint not null auto_increment,
    descricao varchar(255) not null,
    preco decimal(19,2) not null ,
    quantidade decimal(11, 2) not null,
    pedido_id bigint,
    primary key(id),
    foreign key(pedido_id) references pedidos(id)
);