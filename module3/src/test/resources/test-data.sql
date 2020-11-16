INSERT INTO gift_certificates.tag (name) values ('name');
INSERT INTO gift_certificates.tag (name) values ('la2');
INSERT INTO gift_certificates.tag (name) values ('la3');
INSERT INTO gift_certificates.tag (name) values ('la4');
INSERT INTO gift_certificates.tag (name) values ('la5');
INSERT INTO gift_certificates.gift_certificate (name,description, price, create_date, last_update_date, duration ) values ('lala','desc',12,'2016-06-06 23:59:59','2016-06-06 23:59:59',13);
INSERT INTO gift_certificates.gift_certificate (name,description, price, create_date, last_update_date, duration ) values ('l2','desc',12,'2016-06-06 23:59:59','2016-06-06 23:59:59',13);
INSERT INTO gift_certificates.gift_certificate (name,description, price, create_date, last_update_date, duration ) values ('l3','desc',12,'2016-06-06 23:59:59','2016-06-06 23:59:59',13);
INSERT INTO gift_certificates.gift_certificate (name,description, price, create_date, last_update_date, duration ) values ('l4','desc',12,'2016-06-06 23:59:59','2016-06-06 23:59:59',13);
INSERT INTO gift_certificates.certificate_tag (tag_id, certificate_id) values (1,1);
INSERT INTO gift_certificates.certificate_tag (tag_id, certificate_id) values (1,2);
INSERT INTO gift_certificates.certificate_tag (tag_id, certificate_id) values (2,1);
INSERT INTO gift_certificates.user (name, surname) values ('mary','flash');
INSERT INTO gift_certificates.user (name, surname) values ('mry','color');
INSERT INTO gift_certificates."ORDER" (user_id, timestamp, cost, order_certificate_id) values (1,'2016-06-06 23:59:59',13,1);
INSERT INTO gift_certificates."ORDER" (user_id, timestamp, cost, order_certificate_id) values (2,'2016-06-06 23:59:59',130,2);
INSERT INTO gift_certificates."ORDER" (user_id, timestamp, cost, order_certificate_id) values (2,'2016-06-06 23:59:59',131,3);
INSERT INTO gift_certificates."ORDER" (user_id, timestamp, cost, order_certificate_id) values (1,'2016-06-06 23:59:59',132,4);
INSERT INTO gift_certificates."ORDER" (user_id, timestamp, cost, order_certificate_id) values (2,'2016-06-06 23:59:59',133,3);




