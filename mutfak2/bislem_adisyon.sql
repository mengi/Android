-- phpMyAdmin SQL Dump
-- version 2.10.3
-- http://www.phpmyadmin.net
-- 
-- Anamakine: localhost
-- Üretim Zamanı: 04 Ocak 2016 saat 11:59:19
-- Sunucu sürümü: 5.0.51
-- PHP Sürümü: 5.2.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- 
-- Veritabanı: `bislem_adisyon`
-- 

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `cari`
-- 

CREATE TABLE `cari` (
  `cariID` bigint(20) NOT NULL auto_increment,
  `carigrupID` int(11) NOT NULL,
  `acik` varchar(255) NOT NULL,
  `telefon` varchar(32) NOT NULL,
  `fax` varchar(32) NOT NULL,
  `adres` varchar(255) NOT NULL,
  `il` varchar(32) NOT NULL,
  `ilce` varchar(32) NOT NULL,
  `vdairesi` varchar(32) NOT NULL,
  `vno` varchar(32) NOT NULL,
  `web` varchar(32) NOT NULL,
  `eposta` varchar(32) NOT NULL,
  `yetkiliacik` varchar(64) NOT NULL,
  `yetkilitel` varchar(32) NOT NULL,
  `tarih` date NOT NULL,
  `durum` varchar(10) NOT NULL,
  `fiyatgrupID` int(11) NOT NULL,
  `bankaacik` varchar(255) NOT NULL,
  PRIMARY KEY  (`cariID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- 
-- Tablo döküm verisi `cari`
-- 

INSERT INTO `cari` VALUES (1, 0, 'Masa Z1', '', '', '', '', '', '', '', '', '', '', '', '0000-00-00', '', 0, '');
INSERT INTO `cari` VALUES (3, 0, 'Masa Z2', '', '', '', '', '', '', '', '', '', '', '', '0000-00-00', '', 0, 'Masa Z2');

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `garson`
-- 

CREATE TABLE `garson` (
  `ID` int(11) NOT NULL auto_increment,
  `KULLANICIADI` varchar(55) NOT NULL,
  `PAROLA` varchar(55) NOT NULL,
  `BULUNDUGUYER` varchar(55) NOT NULL,
  `GARSONKOD` varchar(11) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

-- 
-- Tablo döküm verisi `garson`
-- 

INSERT INTO `garson` VALUES (6, 'Adem', '1', '67', 'G-1');
INSERT INTO `garson` VALUES (7, 'TuÄŸba', '2', '66', 'G-7');

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `kat`
-- 

CREATE TABLE `kat` (
  `id` int(11) NOT NULL auto_increment,
  `acik` varchar(32) NOT NULL,
  `renk` varchar(8) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=68 ;

-- 
-- Tablo döküm verisi `kat`
-- 

INSERT INTO `kat` VALUES (66, 'SALON', '#694545');
INSERT INTO `kat` VALUES (67, 'BAHÃ‡E', '#ab4343');

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `masa`
-- 

CREATE TABLE `masa` (
  `id` int(11) NOT NULL auto_increment,
  `acik` varchar(32) NOT NULL,
  `kat` int(11) NOT NULL,
  `aktif` varchar(4) NOT NULL,
  `siparis` int(11) NOT NULL,
  `sira` smallint(6) NOT NULL,
  `acankim` varchar(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=84 ;

-- 
-- Tablo döküm verisi `masa`
-- 

INSERT INTO `masa` VALUES (-2, 'Sıcak Satış', 0, 'off', 2, 0, 'kasa');
INSERT INTO `masa` VALUES (-1, 'Paket Satış', 0, 'on', 2, 0, 'kasa');
INSERT INTO `masa` VALUES (48, 'B-A1', 67, 'off', 0, 1, 'G-1');
INSERT INTO `masa` VALUES (49, 'B-A2', 67, 'on', 0, 49, 'kasa');
INSERT INTO `masa` VALUES (50, 'B-A3', 67, 'off', 0, 50, 'kasa');
INSERT INTO `masa` VALUES (51, 'B-A4', 67, 'off', 0, 51, '');
INSERT INTO `masa` VALUES (52, 'B-A5', 67, 'off', 0, 52, 'G-1');
INSERT INTO `masa` VALUES (53, 'B-B1', 67, 'off', 0, 53, '');
INSERT INTO `masa` VALUES (54, 'B-B2', 67, 'off', 0, 54, '');
INSERT INTO `masa` VALUES (55, 'B-B3', 67, 'off', 0, 55, '');
INSERT INTO `masa` VALUES (56, 'B-B4', 67, 'off', 0, 56, '');
INSERT INTO `masa` VALUES (58, 'B-D1', 67, 'off', 0, 58, '');
INSERT INTO `masa` VALUES (59, 'B-D2', 67, 'off', 0, 59, '');
INSERT INTO `masa` VALUES (60, 'B-D3', 67, 'off', 0, 60, '');
INSERT INTO `masa` VALUES (61, 'B-D4', 67, 'off', 0, 61, '');
INSERT INTO `masa` VALUES (62, 'B-D5', 67, 'off', 0, 62, '');
INSERT INTO `masa` VALUES (63, 'B-E1', 67, 'off', 0, 63, '');
INSERT INTO `masa` VALUES (64, 'B-E2', 67, 'off', 0, 64, '');
INSERT INTO `masa` VALUES (69, 'S-A', 66, 'on', 0, 65, 'kasa');
INSERT INTO `masa` VALUES (70, 'S-A2', 66, 'off', 0, 70, 'kasa');
INSERT INTO `masa` VALUES (71, 'S-A3', 66, 'off', 0, 71, '');
INSERT INTO `masa` VALUES (72, 'S-B1', 66, 'off', 0, 72, '');
INSERT INTO `masa` VALUES (73, 'S-B2', 66, 'off', 0, 73, '');
INSERT INTO `masa` VALUES (75, 'S-B3', 66, 'off', 0, 74, '');
INSERT INTO `masa` VALUES (76, 'S-C1', 66, 'off', 0, 76, '');
INSERT INTO `masa` VALUES (77, 'S-C2', 66, 'off', 0, 77, '');
INSERT INTO `masa` VALUES (78, 'S-C3', 66, 'off', 0, 78, '');
INSERT INTO `masa` VALUES (79, 'S-C4', 66, 'off', 0, 79, '');
INSERT INTO `masa` VALUES (80, 'S-D1', 66, 'off', 0, 80, '');
INSERT INTO `masa` VALUES (81, 'S-D2', 66, 'off', 0, 81, '');
INSERT INTO `masa` VALUES (82, 'S-D3', 66, 'off', 0, 82, '');
INSERT INTO `masa` VALUES (83, 'S-D4', 66, 'off', 0, 83, '');

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `oturum`
-- 

CREATE TABLE `oturum` (
  `kullanici` varchar(16) character set latin1 NOT NULL default '',
  `sid` varchar(64) character set latin1 NOT NULL default '',
  `zaman` varchar(32) character set latin1 NOT NULL default ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Tablo döküm verisi `oturum`
-- 


-- --------------------------------------------------------

-- 
-- Tablo yapısı: `parcaliode`
-- 

CREATE TABLE `parcaliode` (
  `id` int(11) NOT NULL auto_increment,
  `siparisid` varchar(11) NOT NULL,
  `masaid` varchar(11) NOT NULL,
  `odemetur` varchar(25) NOT NULL,
  `tutar` float NOT NULL,
  `pikramtutar` float NOT NULL,
  `pindirim` float NOT NULL,
  `veresiyeid` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

-- 
-- Tablo döküm verisi `parcaliode`
-- 

INSERT INTO `parcaliode` VALUES (1, '154', '50', 'NAKIT', 2.5, 0, 0, 0);
INSERT INTO `parcaliode` VALUES (2, '154', '50', 'KREDI', 7.5, 0, 0, 0);
INSERT INTO `parcaliode` VALUES (3, '154', '50', 'KREDI', 2.5, 0, 0, 0);
INSERT INTO `parcaliode` VALUES (4, '154', '50', 'KREDI', 2.5, 0, 0, 0);
INSERT INTO `parcaliode` VALUES (5, '154', '50', 'VERESIYE', 2.5, 0, 0, 12);

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `siparis`
-- 

CREATE TABLE `siparis` (
  `id` int(11) NOT NULL auto_increment,
  `masa` int(11) NOT NULL,
  `aktif` int(11) NOT NULL,
  `bastrh` datetime NOT NULL,
  `bittrh` datetime default NULL,
  `odemetur` varchar(8) default NULL,
  `toplam` float(10,2) default NULL,
  `siparistip` varchar(11) NOT NULL,
  `indirim` float default NULL,
  `sacankim` varchar(55) default NULL,
  `oacankim` varchar(55) default NULL,
  `ikramtutar` float NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=156 ;

-- 
-- Tablo döküm verisi `siparis`
-- 

INSERT INTO `siparis` VALUES (138, 69, 0, '2015-10-23 16:53:10', '2015-10-23 17:14:21', 'NAKIT', 76.50, '0', 0, 'kasa', 'kasa', 0);
INSERT INTO `siparis` VALUES (139, 48, 0, '2015-10-23 17:03:44', '2015-10-23 17:15:02', 'NAKIT', 74.00, '0', 0, 'G-1', 'kasa', 0);
INSERT INTO `siparis` VALUES (140, 52, 0, '2015-10-23 17:11:46', '2015-10-23 17:14:38', 'NAKIT', 16.00, '0', 0, 'G-1', 'kasa', 0);
INSERT INTO `siparis` VALUES (141, 48, 0, '2015-10-30 00:36:17', '2015-11-10 13:18:52', 'NAKIT', 189.50, '0', 0, 'G-1', 'kasa', 0);
INSERT INTO `siparis` VALUES (142, -2, 0, '2015-11-10 12:59:02', '2015-11-10 13:01:08', 'NAKIT', 35.00, '2', 0, 'kasa', 'kasa', 0);
INSERT INTO `siparis` VALUES (143, -1, 0, '2015-11-10 12:59:39', '2015-11-10 13:01:11', 'NAKIT', 61.00, '2', 0, 'kasa', 'kasa', 0);
INSERT INTO `siparis` VALUES (144, -1, 0, '2015-11-10 13:01:54', '2015-11-10 13:02:52', 'KREDI', 21.00, '2', 0, 'kasa', 'kasa', 0);
INSERT INTO `siparis` VALUES (145, -2, 0, '2015-11-14 12:16:54', '2015-11-21 23:20:05', 'NAKIT', 5.00, '2', 0, 'kasa', 'kasa', 0);
INSERT INTO `siparis` VALUES (146, 69, 1, '2015-11-17 13:00:18', '0000-00-00 00:00:00', '', 0.00, '0', 0, 'kasa', 'kasa', 0);
INSERT INTO `siparis` VALUES (147, 70, 0, '2015-11-21 12:50:59', '2015-11-21 13:00:35', 'NAKIT', 2.50, '0', 0, 'kasa', 'kasa', 0);
INSERT INTO `siparis` VALUES (148, 49, 0, '2015-11-21 13:04:48', '2015-11-21 13:08:12', 'NAKIT', 2.50, '0', 0, 'kasa', 'kasa', 0);
INSERT INTO `siparis` VALUES (149, 49, 0, '2015-11-21 13:09:15', '2015-11-21 13:09:52', 'NAKIT', 2.50, '0', 0, 'kasa', 'kasa', 0);
INSERT INTO `siparis` VALUES (150, 49, 0, '2015-11-21 13:11:12', '2015-11-21 13:12:04', 'NAKIT', 2.50, '0', 0, 'kasa', 'kasa', 0);
INSERT INTO `siparis` VALUES (151, 49, 0, '2015-11-21 13:18:19', '2015-11-21 13:28:07', 'NAKIT', 2.50, '0', 0, 'kasa', 'kasa', 0);
INSERT INTO `siparis` VALUES (152, 49, 1, '2015-11-21 13:40:35', '0000-00-00 00:00:00', '', 0.00, '0', 0, 'kasa', 'kasa', 0);
INSERT INTO `siparis` VALUES (153, 50, 0, '2015-11-21 15:45:22', '2015-11-21 16:55:52', 'NAKIT', 5.00, '0', 0, 'kasa', 'kasa', 0);
INSERT INTO `siparis` VALUES (154, 50, 0, '2015-11-21 17:00:43', '2015-11-21 23:15:15', 'NAKIT', 7.50, '0', 0, 'kasa', 'kasa', 0);
INSERT INTO `siparis` VALUES (155, -1, 1, '2015-11-21 23:20:20', '0000-00-00 00:00:00', '', 0.00, '2', 0, '', '', 0);

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `siparishareket`
-- 

CREATE TABLE `siparishareket` (
  `id` int(11) NOT NULL auto_increment,
  `stokid` int(11) NOT NULL,
  `urunadi` varchar(64) NOT NULL,
  `adet` int(11) NOT NULL,
  `siparisid` int(11) NOT NULL,
  `masaid` int(11) NOT NULL,
  `eklemetrh` datetime default NULL,
  `tutar` float(10,2) NOT NULL,
  `durum` varchar(16) default NULL,
  `siparissekli` varchar(16) default NULL,
  `odemetur` varchar(16) default NULL,
  `ses` varchar(16) default NULL,
  `veresiyeid` varchar(16) default NULL,
  `siparisdurum` varchar(11) NOT NULL,
  `sanaladet` float NOT NULL,
  `ikramadet` float NOT NULL,
  `kesinadet` float NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=737 ;

-- 
-- Tablo döküm verisi `siparishareket`
-- 

INSERT INTO `siparishareket` VALUES (735, 63, 'asdadsa', 3, 154, 50, '2015-11-21 17:00:49', 2.50, '1', 'NORMAL', 'NAKIT', 'OKUNDU', '', '2', 0, 0, 10);
INSERT INTO `siparishareket` VALUES (736, 63, 'asdadsa', 2, 145, -2, '2015-11-21 23:16:11', 2.50, '1', 'NORMAL', 'NAKIT', 'OKUNDU', '', '2', 0, 0, 2);

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `siparishareket_yedek`
-- 

CREATE TABLE `siparishareket_yedek` (
  `id` int(11) NOT NULL auto_increment,
  `stokid` int(11) NOT NULL,
  `urunadi` varchar(64) NOT NULL,
  `adet` int(11) NOT NULL,
  `siparisid` int(11) NOT NULL,
  `masaid` int(11) NOT NULL,
  `eklemetrh` datetime NOT NULL,
  `tutar` float(10,2) NOT NULL,
  `durum` varchar(16) NOT NULL,
  `siparissekli` varchar(16) NOT NULL,
  `odemetur` varchar(16) NOT NULL,
  `ses` varchar(16) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Tablo döküm verisi `siparishareket_yedek`
-- 


-- --------------------------------------------------------

-- 
-- Tablo yapısı: `stok`
-- 

CREATE TABLE `stok` (
  `stokID` bigint(20) NOT NULL auto_increment,
  `acik` varchar(255) NOT NULL,
  `fiyat` float(10,2) NOT NULL,
  `aciklama` text NOT NULL,
  `tarih` date NOT NULL,
  `urungrupid` int(11) NOT NULL,
  `durumd` varchar(15) NOT NULL,
  PRIMARY KEY  (`stokID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=64 ;

-- 
-- Tablo döküm verisi `stok`
-- 

INSERT INTO `stok` VALUES (26, 'KÃ¼nefe', 7.00, '', '2015-10-23', 51, '0');
INSERT INTO `stok` VALUES (28, 'Kuzu Kaburga', 16.00, '', '2015-10-23', 50, '0');
INSERT INTO `stok` VALUES (29, 'FÄ±rÄ±n KebabÄ±', 15.00, '', '2015-10-23', 50, '0');
INSERT INTO `stok` VALUES (30, 'Adana', 14.00, '', '2015-10-23', 50, '0');
INSERT INTO `stok` VALUES (31, 'ÅžiÅŸ KÃ¶fte', 14.00, '', '2015-10-23', 50, '0');
INSERT INTO `stok` VALUES (32, 'Kuzu ÅžiÅŸ', 16.00, '', '2015-10-23', 50, '0');
INSERT INTO `stok` VALUES (33, 'Kanat', 13.00, '', '2015-10-23', 50, '0');
INSERT INTO `stok` VALUES (34, '1.5 Porsiyon KarÄ±ÅŸÄ±k Izgara', 23.00, '', '2015-10-23', 50, '0');
INSERT INTO `stok` VALUES (35, 'Tavuk ÅžiÅŸ', 13.00, '', '2015-10-23', 50, '0');
INSERT INTO `stok` VALUES (36, 'Kuzu Pirzola', 16.00, '', '2015-10-23', 50, '0');
INSERT INTO `stok` VALUES (37, 'SaÃ§ Kavurma', 15.00, '', '2015-10-23', 50, '0');
INSERT INTO `stok` VALUES (38, 'Kiremitte KaÅŸarlÄ± KÃ¶fte', 15.00, '', '2015-10-23', 50, '0');
INSERT INTO `stok` VALUES (39, 'Tirit', 16.00, '', '2015-10-23', 50, '0');
INSERT INTO `stok` VALUES (40, 'Beyti', 16.00, '', '2015-10-23', 50, '0');
INSERT INTO `stok` VALUES (41, 'Alinazik', 16.00, '', '2015-10-23', 50, '0');
INSERT INTO `stok` VALUES (42, 'Bamya', 7.00, '', '2015-10-23', 48, '0');
INSERT INTO `stok` VALUES (43, 'Mercimek', 5.00, '', '2015-10-23', 48, '0');
INSERT INTO `stok` VALUES (44, 'ArabaÅŸÄ±', 5.00, '', '2015-10-23', 48, '0');
INSERT INTO `stok` VALUES (45, 'Ä°ÅŸkembe', 7.00, '', '2015-10-23', 48, '0');
INSERT INTO `stok` VALUES (46, 'Etliekmek', 10.00, '', '2015-10-23', 49, '0');
INSERT INTO `stok` VALUES (47, 'BÄ±Ã§akarasÄ±', 12.00, '', '2015-10-23', 49, '0');
INSERT INTO `stok` VALUES (48, 'Mevlana', 10.00, '', '2015-10-23', 49, '0');
INSERT INTO `stok` VALUES (49, 'BÃ¶rek', 10.00, '', '2015-10-23', 49, '0');
INSERT INTO `stok` VALUES (50, 'BÄ±Ã§ak ÃœstÃ¼ KaÅŸar', 15.00, '', '2015-10-23', 49, '0');
INSERT INTO `stok` VALUES (51, 'Coca Cola', 2.50, '', '2015-10-23', 52, '0');
INSERT INTO `stok` VALUES (52, 'Coca Cola Zero', 2.50, '', '2015-10-23', 52, '0');
INSERT INTO `stok` VALUES (53, 'Fanta', 2.50, '', '2015-10-23', 52, '0');
INSERT INTO `stok` VALUES (54, 'Sprite', 2.50, '', '2015-10-23', 52, '0');
INSERT INTO `stok` VALUES (55, 'Åžalgam', 2.00, '', '2015-10-23', 52, '0');
INSERT INTO `stok` VALUES (56, 'Fuse Tea', 2.50, '', '2015-10-23', 52, '0');
INSERT INTO `stok` VALUES (57, 'Ayran', 1.50, '', '2015-10-23', 52, '0');
INSERT INTO `stok` VALUES (58, 'Soda', 2.00, '', '2015-10-23', 52, '0');
INSERT INTO `stok` VALUES (59, 'Meyveli Soda', 2.00, '', '2015-10-23', 52, '0');
INSERT INTO `stok` VALUES (60, 'Meyve Suyu', 2.50, '', '2015-10-23', 52, '0');
INSERT INTO `stok` VALUES (61, 'Su', 1.50, '', '2015-10-23', 52, '0');
INSERT INTO `stok` VALUES (62, 'asdadsa', 12.00, '', '2015-11-17', 52, '0');
INSERT INTO `stok` VALUES (63, 'asdadsa', 2.50, '', '2015-11-17', 52, '1');

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `stokbir`
-- 

CREATE TABLE `stokbir` (
  `id` int(11) NOT NULL auto_increment,
  `acik` varchar(150) NOT NULL,
  `tmiktar` int(11) NOT NULL,
  `fiyat` double NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

-- 
-- Tablo döküm verisi `stokbir`
-- 

INSERT INTO `stokbir` VALUES (2, 'deneme1', 21, 3.5);
INSERT INTO `stokbir` VALUES (3, 'deneme2', 45, 2.5);
INSERT INTO `stokbir` VALUES (4, 'asdadsa', 18, 2.5);
INSERT INTO `stokbir` VALUES (5, 'kÄ±yma', 5, 5);

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `stokbirhar`
-- 

CREATE TABLE `stokbirhar` (
  `id` int(11) NOT NULL auto_increment,
  `stokid` int(11) NOT NULL,
  `tarih` datetime NOT NULL,
  `miktar` int(11) NOT NULL,
  `durum` varchar(15) NOT NULL,
  `stoktip` varchar(12) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

-- 
-- Tablo döküm verisi `stokbirhar`
-- 

INSERT INTO `stokbirhar` VALUES (2, 2, '2015-11-16 12:46:22', 2, '', '');
INSERT INTO `stokbirhar` VALUES (3, 2, '2015-11-16 12:46:42', 4, '', '');
INSERT INTO `stokbirhar` VALUES (4, 2, '2015-11-16 12:46:45', 7, '', '');
INSERT INTO `stokbirhar` VALUES (5, 2, '2015-11-16 12:59:23', 8, '', '');
INSERT INTO `stokbirhar` VALUES (6, 2, '2015-11-16 12:59:59', 7, '', '');
INSERT INTO `stokbirhar` VALUES (7, 2, '2015-11-16 13:24:52', 7, '', '');
INSERT INTO `stokbirhar` VALUES (8, 56, '2015-11-17 11:37:13', 5, '1', '');
INSERT INTO `stokbirhar` VALUES (9, 51, '2015-11-17 11:37:24', 7, '0', '');
INSERT INTO `stokbirhar` VALUES (10, 53, '2015-11-17 11:38:12', 14, '0', '');
INSERT INTO `stokbirhar` VALUES (11, 54, '2015-11-17 11:39:07', 14, '0', '');
INSERT INTO `stokbirhar` VALUES (12, 2, '2015-11-17 11:39:37', 7, '0', '');
INSERT INTO `stokbirhar` VALUES (13, 3, '2015-11-17 11:39:54', 45, '0', '');
INSERT INTO `stokbirhar` VALUES (14, 4, '2015-11-17 12:35:38', 4, '0', '');
INSERT INTO `stokbirhar` VALUES (15, 5, '2015-11-21 11:04:34', 10, '0', '');
INSERT INTO `stokbirhar` VALUES (16, 5, '2015-11-21 11:04:54', 5, '1', '');

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `urungrup`
-- 

CREATE TABLE `urungrup` (
  `id` int(11) NOT NULL auto_increment,
  `acik` varchar(64) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=53 ;

-- 
-- Tablo döküm verisi `urungrup`
-- 

INSERT INTO `urungrup` VALUES (48, 'Ã‡ORBALAR');
INSERT INTO `urungrup` VALUES (49, 'PÄ°DELER');
INSERT INTO `urungrup` VALUES (50, 'KEBABLAR');
INSERT INTO `urungrup` VALUES (51, 'TATLILAR');
INSERT INTO `urungrup` VALUES (52, 'Ä°Ã‡ECEKLER');

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `veresiye`
-- 

CREATE TABLE `veresiye` (
  `id` int(11) NOT NULL auto_increment,
  `adsoyad` varchar(128) NOT NULL,
  `telefon` varchar(32) NOT NULL,
  `adres` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

-- 
-- Tablo döküm verisi `veresiye`
-- 

INSERT INTO `veresiye` VALUES (12, 'deneme', '(555) 555-5555', 'as');
INSERT INTO `veresiye` VALUES (13, 'deneme 2', '(555) 252-5552', 'adad');

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `veresiyesiparis`
-- 

CREATE TABLE `veresiyesiparis` (
  `id` int(11) NOT NULL auto_increment,
  `veresiyeid` varchar(11) NOT NULL,
  `siparisid` varchar(11) NOT NULL,
  `adsoyad` varchar(45) default NULL,
  `telefon` varchar(15) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

-- 
-- Tablo döküm verisi `veresiyesiparis`
-- 

INSERT INTO `veresiyesiparis` VALUES (6, '5', '93', '', '');
INSERT INTO `veresiyesiparis` VALUES (7, '5', '94', '', '');
INSERT INTO `veresiyesiparis` VALUES (8, '5', '106', 'gggggg', '1');
INSERT INTO `veresiyesiparis` VALUES (9, '5', '107', '', '1');
INSERT INTO `veresiyesiparis` VALUES (10, '5', '108', '', '1');
INSERT INTO `veresiyesiparis` VALUES (11, '6', '110', '1was', '');
INSERT INTO `veresiyesiparis` VALUES (12, '5', '111', '', '');
INSERT INTO `veresiyesiparis` VALUES (13, '5', '118', '', '');
INSERT INTO `veresiyesiparis` VALUES (14, '5', '131', 'asdadasd', '1');
INSERT INTO `veresiyesiparis` VALUES (15, '6', '132', 'asadasda', '');
INSERT INTO `veresiyesiparis` VALUES (16, '12', '154', 'sada', '');

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `wp_gcm_kullanicilar`
-- 

CREATE TABLE `wp_gcm_kullanicilar` (
  `id` int(10) NOT NULL auto_increment,
  `RegistrationID` text NOT NULL,
  `GarsonKod` varchar(15) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15 ;

-- 
-- Tablo döküm verisi `wp_gcm_kullanicilar`
-- 

INSERT INTO `wp_gcm_kullanicilar` VALUES (14, 'APA91bFvVbZPkg2XviZd3bdLsQ9YC95zEI5ofLKgG30FFkcpjs4ee7MLx3w5kFl7_q4oM3Io2eGZXgSMc9LtyX6fx65YIwZ8ONR3VXUdUgJpK0aHBWPL3aI', 'G-1');

-- --------------------------------------------------------

-- 
-- Tablo yapısı: `yonetici`
-- 

CREATE TABLE `yonetici` (
  `id` smallint(11) NOT NULL auto_increment,
  `kullanici` varchar(16) default NULL,
  `sifre` text,
  `isim` varchar(30) default NULL,
  `statu` varchar(3) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

-- 
-- Tablo döküm verisi `yonetici`
-- 

INSERT INTO `yonetici` VALUES (2, 'mutfak', '606717496665bcba', 'mutfak', '2');
INSERT INTO `yonetici` VALUES (4, 'kasa', '606717496665bcba', 'kasa', '1');
INSERT INTO `yonetici` VALUES (8, 'admin', '606717496665bcba', 'admin', '0');
