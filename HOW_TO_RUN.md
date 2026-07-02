# Run the Pharmacy Management App in VS Code

## 1. Install VS Code Java tools

Install these VS Code extensions:

```text
Extension Pack for Java
Maven for Java
```

The project uses Maven, so JavaFX and MySQL Connector/J are downloaded automatically.

## 2. Check Java and Maven

Open the VS Code terminal:

```text
Terminal > New Terminal
```

Run:

```powershell
java -version
mvn -version
```

If both commands work, continue.

## 3. Connect to MySQL

Try:

```powershell
mysql -u root -p
```

If PowerShell does not recognize `mysql`, use:

```powershell
& "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p
```

Use this password:

```text
root
```

## 4. Create the database

Inside MySQL, run the script:

```powershell
Get-Content database/schema.sql | & "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p
```

If the database already exists and you want to reload the sample data from zero, run this first:

```powershell
& "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p -e "DROP DATABASE IF EXISTS gestionpharmacie;"
```

Then run the `Get-Content database/schema.sql | ...` command again.

Or copy this SQL manually:

```sql
CREATE DATABASE IF NOT EXISTS gestionpharmacie;
USE gestionpharmacie;

CREATE TABLE IF NOT EXISTS utilisateurs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom_utilisateur VARCHAR(100) NOT NULL,
    mot_de_passe VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS pharmaciens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    role VARCHAR(100),
    cin VARCHAR(50),
    telephone VARCHAR(50),
    date_recrutement DATE
);

CREATE TABLE IF NOT EXISTS medicaments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    categorie VARCHAR(100),
    date_entree DATE,
    date_expiration DATE
);

CREATE TABLE IF NOT EXISTS approvisionnements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    medicament_id INT NOT NULL,
    pharmacien_id INT NOT NULL,
    quantite DOUBLE NOT NULL,
    date_approvisionnement DATE
);

CREATE TABLE IF NOT EXISTS ventes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    medicament_id INT NOT NULL,
    quantite DOUBLE NOT NULL,
    prix_unitaire DOUBLE NOT NULL,
    unite VARCHAR(50),
    date_vente DATE
);

INSERT INTO utilisateurs (nom_utilisateur, mot_de_passe)
SELECT 'admin', 'admin'
WHERE NOT EXISTS (
    SELECT 1 FROM utilisateurs WHERE nom_utilisateur = 'admin'
);
```

Exit MySQL:

```sql
exit;
```

## 5. Run the app

From the project folder, run:

```powershell
mvn javafx:run
```

You can also use:

```text
Terminal > Run Task > Run Gestion Pharmacie
```

## 6. Login

Use:

```text
Username: admin
Password: admin
```
