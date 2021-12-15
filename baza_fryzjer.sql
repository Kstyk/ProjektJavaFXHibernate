-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 03 Gru 2021, 18:07
-- Wersja serwera: 10.1.25-MariaDB
-- Wersja PHP: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `fryzjer`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `adres_oddzialu`
--

CREATE TABLE `adres_oddzialu` (
  `id_adresu` int(11) NOT NULL,
  `kod_pocztowy` varchar(6) COLLATE utf8_polish_ci NOT NULL,
  `miasto` varchar(64) COLLATE utf8_polish_ci NOT NULL,
  `ulica` varchar(64) COLLATE utf8_polish_ci DEFAULT NULL,
  `numer_domu` varchar(16) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `adres_oddzialu`
--

INSERT INTO `adres_oddzialu` (`id_adresu`, `kod_pocztowy`, `miasto`, `ulica`, `numer_domu`) VALUES
(1, '14-021', 'Warszawa', 'Konwaliowa', '55'),
(2, '38-021', 'Ma?ókwa', 'Hucisko', '55'),
(3, '14-021', 'Warszawa', 'Konwaliowa', '55'),
(4, '38-021', 'Ma?ókwa', 'Hucisko', '55'),
(7, '14-021', 'Warszawa', 'Konwaliowa', '55'),
(8, '38-021', 'Ma?ókwa', 'Hucisko', '55');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `fryzjer`
--

CREATE TABLE `fryzjer` (
  `id_fryzjera` int(11) NOT NULL,
  `imie_fryzjera` varchar(32) COLLATE utf8_polish_ci NOT NULL,
  `nazwisko_fryzjera` varchar(64) COLLATE utf8_polish_ci NOT NULL,
  `telefon_fryzjera` varchar(11) COLLATE utf8_polish_ci NOT NULL,
  `id_oddzialu` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `fryzjer`
--

INSERT INTO `fryzjer` (`id_fryzjera`, `imie_fryzjera`, `nazwisko_fryzjera`, `telefon_fryzjera`, `id_oddzialu`) VALUES
(1, 'Adam', 'Janas', '344233222', 1),
(2, 'Kamil', 'Janas', '344233222', 1),
(3, 'Adam', 'Janas', '344233222', 3),
(4, 'Kamil', 'Janas', '344233222', 3),
(7, 'Adam', 'Janas', '344233222', 7),
(8, 'Kamil', 'Janas', '344233222', 7);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `klient`
--

CREATE TABLE `klient` (
  `id_klienta` int(11) NOT NULL,
  `imie_klienta` varchar(32) COLLATE utf8_polish_ci NOT NULL,
  `nazwisko_klienta` varchar(64) COLLATE utf8_polish_ci NOT NULL,
  `telefon` varchar(11) COLLATE utf8_polish_ci NOT NULL,
  `plec` varchar(1) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `klient`
--

INSERT INTO `klient` (`id_klienta`, `imie_klienta`, `nazwisko_klienta`, `telefon`, `plec`) VALUES
(1, 'Seweryn', 'Dr?g', '536453146', 'M'),
(2, 'Seweryn', 'Dr?g', '536453146', 'M'),
(3, 'Adrian', 'Gula', '473723238', 'M'),
(4, 'Seweryn', 'Dr?g', '536453146', 'M');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `oddzialy`
--

CREATE TABLE `oddzialy` (
  `oddzial_id` int(11) NOT NULL,
  `nazwa` varchar(128) COLLATE utf8_polish_ci NOT NULL,
  `id_adresu` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `oddzialy`
--

INSERT INTO `oddzialy` (`oddzial_id`, `nazwa`, `id_adresu`) VALUES
(1, 'ABC', 1),
(2, 'DEF', 2),
(3, 'ABC', 3),
(4, 'DEF', 4),
(7, 'ABC', 7),
(8, 'DEF', 8);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `usluga`
--

CREATE TABLE `usluga` (
  `id_uslugi` int(11) NOT NULL,
  `cena` double NOT NULL,
  `nazwa_uslugi` varchar(64) COLLATE utf8_polish_ci NOT NULL,
  `data_uslugi` date NOT NULL,
  `godzina_uslugi` time NOT NULL,
  `id_klienta` int(11) NOT NULL,
  `id_fryzjera` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `usluga`
--

INSERT INTO `usluga` (`id_uslugi`, `cena`, `nazwa_uslugi`, `data_uslugi`, `godzina_uslugi`, `id_klienta`, `id_fryzjera`) VALUES
(1, 25.5, 'Stryzenie', '2021-12-03', '13:23:04', 4, 7),
(2, 42, 'Farbowanie', '2021-11-16', '09:24:00', 1, 1),
(3, 21, 'Farbowanko', '2021-11-09', '09:18:00', 3, 8);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `adres_oddzialu`
--
ALTER TABLE `adres_oddzialu`
  ADD PRIMARY KEY (`id_adresu`);

--
-- Indexes for table `fryzjer`
--
ALTER TABLE `fryzjer`
  ADD PRIMARY KEY (`id_fryzjera`),
  ADD KEY `id_oddzialu` (`id_oddzialu`);

--
-- Indexes for table `klient`
--
ALTER TABLE `klient`
  ADD PRIMARY KEY (`id_klienta`);

--
-- Indexes for table `oddzialy`
--
ALTER TABLE `oddzialy`
  ADD PRIMARY KEY (`oddzial_id`),
  ADD UNIQUE KEY `id_adresu` (`id_adresu`),
  ADD UNIQUE KEY `UK_9axxhtuvh0f9jqno4q6yewnqc` (`id_adresu`);

--
-- Indexes for table `usluga`
--
ALTER TABLE `usluga`
  ADD PRIMARY KEY (`id_uslugi`),
  ADD KEY `id_klienta` (`id_klienta`),
  ADD KEY `id_fryzjera` (`id_fryzjera`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `adres_oddzialu`
--
ALTER TABLE `adres_oddzialu`
  MODIFY `id_adresu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT dla tabeli `fryzjer`
--
ALTER TABLE `fryzjer`
  MODIFY `id_fryzjera` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT dla tabeli `klient`
--
ALTER TABLE `klient`
  MODIFY `id_klienta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT dla tabeli `oddzialy`
--
ALTER TABLE `oddzialy`
  MODIFY `oddzial_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT dla tabeli `usluga`
--
ALTER TABLE `usluga`
  MODIFY `id_uslugi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `fryzjer`
--
ALTER TABLE `fryzjer`
  ADD CONSTRAINT `fryzjer_ibfk_1` FOREIGN KEY (`id_oddzialu`) REFERENCES `oddzialy` (`oddzial_id`) ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `oddzialy`
--
ALTER TABLE `oddzialy`
  ADD CONSTRAINT `oddzialy_ibfk_1` FOREIGN KEY (`id_adresu`) REFERENCES `adres_oddzialu` (`id_adresu`) ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `usluga`
--
ALTER TABLE `usluga`
  ADD CONSTRAINT `usluga_ibfk_1` FOREIGN KEY (`id_klienta`) REFERENCES `klient` (`id_klienta`) ON UPDATE CASCADE,
  ADD CONSTRAINT `usluga_ibfk_2` FOREIGN KEY (`id_fryzjera`) REFERENCES `fryzjer` (`id_fryzjera`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
