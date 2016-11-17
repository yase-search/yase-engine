# Y.A.S.E

Yet another search engine.

## Documentation :

### Installation :

Pour installer le projet :
* `git clone git@github.com:yase-search/yase-engine.git`
* `npm install`

### Lancer le crawler :

Pour pouvoir lancer le crawler, il faut définir les variables de sessions suivantes :

* `PG_URL` Adresse du serveur postgres
* `PG_USER` Utilisateur de la base de données
* `PG_PASSWORD` Mot de passe de la base de données
* `PG_DB` Nom de la base de données
* `CRAWL_ACTIVE` (optionnel) TRUE / FALSE : Activer ou non le crawler. Défaut : FALSE
* `CRAWL_DOMAINS` (optionnel) Liste de domaines à parcourir, séparés par des virgules. Exemple : `https://google.fr,https://fr.wikipedia.org`

### Utilisation :

On écris des mots dans la barre de recherche de la page d'accueil puis on appuis sur la touche "Entrée". Les résultats de notre recherche s'affiche suite à notre action.
