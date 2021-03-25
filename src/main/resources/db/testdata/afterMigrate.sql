set foreign_key_checks = 0;

delete from products;

set foreign_key_checks = 1;

alter table products auto_increment = 1;


insert into products values (null, "Monitor LG Ultrawide 25p", "Full HD, Resolução 2560 x 1080, 2 saídas HDMI", 999.90);
insert into products values (null, "Monitor Philips 18p", "HD, Resolução 1366 x 768, 1 saída HDMI, 1 saída VGA", 453.90);
insert into products values (null, "Notebook Samsung", "Core i5, 8Gb, SSD 256Gb, Tela de 15p", 3999.90);
insert into products values (null, "Notebook Acer", "Core i7, 16Gb, SSD 256Gb, Tela de 13p", 5979.90);