-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 05, 2026
-- Server version: 8.0
-- PHP Version: 8.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gestionpharmacie`
--

CREATE DATABASE IF NOT EXISTS `gestionpharmacie` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gestionpharmacie`;

-- --------------------------------------------------------

--
-- Table structure for table `utilisateurs`
--

CREATE TABLE `utilisateurs` (
  `id` int(11) NOT NULL,
  `nom_utilisateur` varchar(100) NOT NULL,
  `mot_de_passe` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `utilisateurs`
--

INSERT INTO `utilisateurs` (`id`, `nom_utilisateur`, `mot_de_passe`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `pharmaciens`
--

CREATE TABLE `pharmaciens` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `role` varchar(100) DEFAULT NULL,
  `cin` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `date_recrutement` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pharmaciens`
--

INSERT INTO `pharmaciens` (`id`, `nom`, `role`, `cin`, `telephone`, `date_recrutement`, `created_at`, `updated_at`) VALUES
(1, 'Safir', 'Pharmacien', 'AA123456', '0600000000', '2026-06-01', '2026-06-05 00:00:00', '2026-06-05 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `medicaments`
--

CREATE TABLE `medicaments` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `categorie` varchar(100) DEFAULT NULL,
  `date_entree` date DEFAULT NULL,
  `date_expiration` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medicaments`
--

INSERT INTO `medicaments` (`id`, `nom`, `categorie`, `date_entree`, `date_expiration`, `created_at`, `updated_at`) VALUES
(1, 'DOLIPRANE', 'PARACETAMOL', '2026-06-04', '2030-06-04', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(2, 'ADO', 'DIABETE', '2026-06-04', '2030-06-04', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(3, 'VITAMINE C', 'COMPLEMENT', '2026-06-05', '2026-07-15', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(4, 'EFFERALGAN', 'PARACETAMOL', '2026-06-06', '2029-12-01', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(5, 'SPASFON', 'ANTISPASMODIQUE', '2026-06-06', '2028-11-20', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(6, 'SMECTA', 'DIGESTIF', '2026-06-06', '2026-08-10', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(7, 'AMOXICILLINE', 'ANTIBIOTIQUE', '2026-06-06', '2028-05-14', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(8, 'AUGMENTIN', 'ANTIBIOTIQUE', '2026-06-06', '2028-03-30', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(9, 'VENTOLINE', 'RESPIRATOIRE', '2026-06-06', '2026-08-25', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(10, 'CLARITINE', 'ANTIHISTAMINIQUE', '2026-06-06', '2029-01-12', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(11, 'AERIUS', 'ANTIHISTAMINIQUE', '2026-06-06', '2028-09-19', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(12, 'IBUPROFENE', 'ANTI-INFLAMMATOIRE', '2026-06-06', '2026-07-30', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(13, 'VOLTARENE', 'ANTI-INFLAMMATOIRE', '2026-06-06', '2028-12-16', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(14, 'GAVISCON', 'DIGESTIF', '2026-06-06', '2029-04-22', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(15, 'MAGNE B6', 'COMPLEMENT', '2026-06-06', '2026-08-01', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(16, 'INSULINE RAPIDE', 'DIABETE', '2026-06-06', '2027-02-18', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(17, 'METFORMINE', 'DIABETE', '2026-06-06', '2029-07-08', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(18, 'BETADINE', 'ANTISEPTIQUE', '2026-06-06', '2026-07-20', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(19, 'BIAFINE', 'DERMATOLOGIE', '2026-06-06', '2028-02-11', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(20, 'FUCIDINE', 'DERMATOLOGIE', '2026-06-06', '2028-06-28', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(21, 'SERUM PHYSIOLOGIQUE', 'HYGIENE', '2026-06-06', '2026-08-15', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(22, 'DAFALGAN CODEINE', 'PARACETAMOL', '2026-06-06', '2029-10-10', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(23, 'AZITHROMYCINE', 'ANTIBIOTIQUE', '2026-06-06', '2028-01-05', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(24, 'RINOCLENIL', 'RESPIRATOIRE', '2026-06-06', '2026-07-25', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(25, 'FERROSTRANE', 'COMPLEMENT', '2026-06-06', '2026-08-30', '2026-06-05 00:00:00', '2026-06-05 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `approvisionnements`
--

CREATE TABLE `approvisionnements` (
  `id` int(11) NOT NULL,
  `medicament_id` int(11) NOT NULL,
  `pharmacien_id` int(11) NOT NULL,
  `quantite` decimal(10,2) NOT NULL,
  `date_approvisionnement` date NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `approvisionnements`
--

INSERT INTO `approvisionnements` (`id`, `medicament_id`, `pharmacien_id`, `quantite`, `date_approvisionnement`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 50.00, '2026-06-03', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(2, 2, 1, 100.00, '2026-06-04', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(3, 3, 1, 8.00, '2026-06-05', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(4, 4, 1, 65.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(5, 5, 1, 35.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(6, 6, 1, 6.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(7, 7, 1, 42.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(8, 8, 1, 30.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(9, 9, 1, 9.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(10, 10, 1, 27.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(11, 11, 1, 22.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(12, 12, 1, 7.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(13, 13, 1, 18.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(14, 14, 1, 55.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(15, 15, 1, 5.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(16, 16, 1, 20.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(17, 17, 1, 48.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(18, 18, 1, 4.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(19, 19, 1, 16.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(20, 20, 1, 24.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(21, 21, 1, 3.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(22, 22, 1, 32.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(23, 23, 1, 26.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(24, 24, 1, 2.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(25, 25, 1, 9.00, '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `ventes`
--

CREATE TABLE `ventes` (
  `id` int(11) NOT NULL,
  `medicament_id` int(11) NOT NULL,
  `quantite` decimal(10,2) NOT NULL,
  `prix_unitaire` decimal(10,2) NOT NULL,
  `unite` varchar(50) DEFAULT NULL,
  `date_vente` date NOT NULL,
  `montant` decimal(10,2) GENERATED ALWAYS AS (`quantite` * `prix_unitaire`) STORED,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ventes`
--

INSERT INTO `ventes` (`id`, `medicament_id`, `quantite`, `prix_unitaire`, `unite`, `date_vente`, `created_at`, `updated_at`) VALUES
(1, 2, 2.00, 100.00, 'boite', '2026-06-04', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(2, 1, 5.00, 25.00, 'boite', '2026-06-05', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(3, 4, 12.00, 28.00, 'boite', '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(4, 5, 8.00, 34.00, 'boite', '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(5, 7, 15.00, 65.00, 'boite', '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(6, 8, 7.00, 82.00, 'boite', '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(7, 10, 10.00, 45.00, 'boite', '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(8, 14, 6.00, 38.00, 'flacon', '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(9, 17, 18.00, 30.00, 'boite', '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(10, 22, 9.00, 52.00, 'boite', '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00'),
(11, 23, 11.00, 70.00, 'boite', '2026-06-06', '2026-06-05 00:00:00', '2026-06-05 00:00:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nom_utilisateur` (`nom_utilisateur`);

--
-- Indexes for table `pharmaciens`
--
ALTER TABLE `pharmaciens`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `medicaments`
--
ALTER TABLE `medicaments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `approvisionnements`
--
ALTER TABLE `approvisionnements`
  ADD PRIMARY KEY (`id`),
  ADD KEY `medicament_id` (`medicament_id`),
  ADD KEY `pharmacien_id` (`pharmacien_id`);

--
-- Indexes for table `ventes`
--
ALTER TABLE `ventes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `medicament_id` (`medicament_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `pharmaciens`
--
ALTER TABLE `pharmaciens`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `medicaments`
--
ALTER TABLE `medicaments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `approvisionnements`
--
ALTER TABLE `approvisionnements`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `ventes`
--
ALTER TABLE `ventes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `approvisionnements`
--
ALTER TABLE `approvisionnements`
  ADD CONSTRAINT `approvisionnements_ibfk_1` FOREIGN KEY (`medicament_id`) REFERENCES `medicaments` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `approvisionnements_ibfk_2` FOREIGN KEY (`pharmacien_id`) REFERENCES `pharmaciens` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `ventes`
--
ALTER TABLE `ventes`
  ADD CONSTRAINT `ventes_ibfk_1` FOREIGN KEY (`medicament_id`) REFERENCES `medicaments` (`id`) ON DELETE CASCADE;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
