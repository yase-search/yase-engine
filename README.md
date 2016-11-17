# Y.A.S.E

Yet another search engine.

## Lancer le projet

Pour pouvoir lancer le projet, il faut définir les variables de sessions suivantes :

* `PG_URL` Adresse du serveur postgres
* `PG_USER` Utilisateur de la base de données
* `PG_PASSWORD` Mot de passe de la base de données
* `PG_DB` Nom de la base de données
* `CRAWL_ACTIVE` (optionnel) TRUE / FALSE : Activer ou non le crawler. Défaut : FALSE
* `CRAWL_DOMAINS` (optionnel) Liste de domaines à parcourir, séparés par des virgules. Exemple : `https://google.fr,https://fr.wikipedia.org`
