# Y.A.S.E

Yet another search engine.

## Documentation

### Installation

Project installation:
* `git clone git@github.com:yase-search/yase-engine.git`
* `nvm install`

### Launch

For launch the project, you'll need to set several environment variables:

* `PG_URL` Postgres server address
* `PG_USER` Database user
* `PG_PASSWORD` Database password
* `PG_DB` Database name

### Crawl

For launch the crawler, you'll need to set 2 environment variables:

* `CRAWL_ACTIVE` (optional) TRUE / FALSE: Crawler activation. Default: FALSE
* `CRAWL_DOMAINS` (optional) Domain list to browse, split by comma. Example: `http://google.com,http://fr.wikipedia.org`

### Use

Write some words in the search bar at the first page, then push "Enter" key. The results of your search will be displayed.
