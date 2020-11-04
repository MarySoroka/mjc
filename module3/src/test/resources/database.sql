CREATE SCHEMA gift_certificates AUTHORIZATION sa;
-- -----------------------------------------------------
-- Table `gift_certificates`.`gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates`.`gift_certificate` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) UNIQUE NOT NULL,
  `description` VARCHAR(1500) NOT NULL,
  `price` DECIMAL(10,0) NOT NULL,
  `create_date` DATETIME NOT NULL,
  `last_update_date` DATETIME NOT NULL,
  `duration` INT NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `gift_certificates`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates`.`tag` (
  `id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) UNIQUE NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `gift_certificates`.`certificate_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates`.`certificate_tag` (
  `tag_id` BIGINT NOT NULL,
  `certificate_id` BIGINT NOT NULL,
  PRIMARY KEY (`tag_id`, `certificate_id`),
  foreign key (`tag_id`) references `gift_certificates`.`tag`(`id`) on delete cascade,
  foreign key (`certificate_id`) references `gift_certificates`.`gift_certificate`(`id`)  on delete cascade)