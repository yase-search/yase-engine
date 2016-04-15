--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.1
-- Dumped by pg_dump version 9.5.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: errors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE errors (
    id bigint NOT NULL,
    idpage bigint NOT NULL,
    errorcode integer NOT NULL,
    date timestamp with time zone NOT NULL
);


ALTER TABLE errors OWNER TO postgres;

--
-- Name: errors_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE errors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE errors_id_seq OWNER TO postgres;

--
-- Name: errors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE errors_id_seq OWNED BY errors.id;


--
-- Name: errors_idpage_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE errors_idpage_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE errors_idpage_seq OWNER TO postgres;

--
-- Name: errors_idpage_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE errors_idpage_seq OWNED BY errors.idpage;


--
-- Name: page_content_idpage_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE page_content_idpage_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE page_content_idpage_seq OWNER TO postgres;

--
-- Name: pages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pages (
    id bigint NOT NULL,
    id_website bigint NOT NULL,
    link character varying(2000) NOT NULL,
    clicks bigint DEFAULT 0 NOT NULL,
    page_rank integer DEFAULT 0 NOT NULL,
    content text NOT NULL,
    title character varying(100) NOT NULL,
    description character varying(255) NOT NULL,
    crawl_date timestamp with time zone NOT NULL,
    size integer NOT NULL,
    load_time integer NOT NULL,
    locale character varying(5) NOT NULL
);


ALTER TABLE pages OWNER TO postgres;

--
-- Name: pages_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pages_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pages_id_seq OWNER TO postgres;

--
-- Name: pages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pages_id_seq OWNED BY pages.id;


--
-- Name: pages_idwebsite_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pages_idwebsite_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pages_idwebsite_seq OWNER TO postgres;

--
-- Name: pages_idwebsite_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pages_idwebsite_seq OWNED BY pages.id_website;


--
-- Name: pages_links; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pages_links (
    "idRefferer" bigint NOT NULL,
    "idDestination" bigint NOT NULL
);


ALTER TABLE pages_links OWNER TO postgres;

--
-- Name: pages_links_iddestination_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pages_links_iddestination_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pages_links_iddestination_seq OWNER TO postgres;

--
-- Name: pages_links_iddestination_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pages_links_iddestination_seq OWNED BY pages_links."idDestination";


--
-- Name: pages_links_idrefferer_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pages_links_idrefferer_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pages_links_idrefferer_seq OWNER TO postgres;

--
-- Name: pages_links_idrefferer_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pages_links_idrefferer_seq OWNED BY pages_links."idRefferer";


--
-- Name: pages_words; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pages_words (
    idpage bigint NOT NULL,
    idword bigint NOT NULL,
    strength integer NOT NULL
);


ALTER TABLE pages_words OWNER TO postgres;

--
-- Name: pages_words_idpage_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pages_words_idpage_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pages_words_idpage_seq OWNER TO postgres;

--
-- Name: pages_words_idpage_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pages_words_idpage_seq OWNED BY pages_words.idpage;


--
-- Name: pages_words_idword_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pages_words_idword_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pages_words_idword_seq OWNER TO postgres;

--
-- Name: pages_words_idword_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pages_words_idword_seq OWNED BY pages_words.idword;


--
-- Name: websites; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE websites (
    id bigint NOT NULL,
    url character varying(255) NOT NULL,
    "siteRank" integer NOT NULL
);


ALTER TABLE websites OWNER TO postgres;

--
-- Name: websites_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE websites_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE websites_id_seq OWNER TO postgres;

--
-- Name: websites_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE websites_id_seq OWNED BY websites.id;


--
-- Name: words; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE words (
    id bigint NOT NULL,
    text character varying(100) NOT NULL
);


ALTER TABLE words OWNER TO postgres;

--
-- Name: words_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE words_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE words_id_seq OWNER TO postgres;

--
-- Name: words_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE words_id_seq OWNED BY words.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY errors ALTER COLUMN id SET DEFAULT nextval('errors_id_seq'::regclass);


--
-- Name: idpage; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY errors ALTER COLUMN idpage SET DEFAULT nextval('errors_idpage_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pages ALTER COLUMN id SET DEFAULT nextval('pages_id_seq'::regclass);


--
-- Name: id_website; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pages ALTER COLUMN id_website SET DEFAULT nextval('pages_idwebsite_seq'::regclass);


--
-- Name: idRefferer; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pages_links ALTER COLUMN "idRefferer" SET DEFAULT nextval('pages_links_idrefferer_seq'::regclass);


--
-- Name: idDestination; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pages_links ALTER COLUMN "idDestination" SET DEFAULT nextval('pages_links_iddestination_seq'::regclass);


--
-- Name: idpage; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pages_words ALTER COLUMN idpage SET DEFAULT nextval('pages_words_idpage_seq'::regclass);


--
-- Name: idword; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pages_words ALTER COLUMN idword SET DEFAULT nextval('pages_words_idword_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY websites ALTER COLUMN id SET DEFAULT nextval('websites_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY words ALTER COLUMN id SET DEFAULT nextval('words_id_seq'::regclass);


--
-- Data for Name: errors; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY errors (id, idpage, errorcode, date) FROM stdin;
\.


--
-- Name: errors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('errors_id_seq', 1, false);


--
-- Name: errors_idpage_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('errors_idpage_seq', 1, false);


--
-- Name: page_content_idpage_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('page_content_idpage_seq', 1, false);


--
-- Data for Name: pages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pages (id, id_website, link, clicks, page_rank, content, title, description, crawl_date, size, load_time, locale) FROM stdin;
\.


--
-- Name: pages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pages_id_seq', 1, false);


--
-- Name: pages_idwebsite_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pages_idwebsite_seq', 1, false);


--
-- Data for Name: pages_links; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pages_links ("idRefferer", "idDestination") FROM stdin;
\.


--
-- Name: pages_links_iddestination_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pages_links_iddestination_seq', 1, false);


--
-- Name: pages_links_idrefferer_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pages_links_idrefferer_seq', 1, false);


--
-- Data for Name: pages_words; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pages_words (idpage, idword, strength) FROM stdin;
\.


--
-- Name: pages_words_idpage_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pages_words_idpage_seq', 1, false);


--
-- Name: pages_words_idword_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pages_words_idword_seq', 1, false);


--
-- Data for Name: websites; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY websites (id, url, "siteRank") FROM stdin;
\.


--
-- Name: websites_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('websites_id_seq', 1, false);


--
-- Data for Name: words; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY words (id, text) FROM stdin;
\.


--
-- Name: words_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('words_id_seq', 1, false);


--
-- Name: errors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY errors
    ADD CONSTRAINT errors_pkey PRIMARY KEY (id);


--
-- Name: pages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pages
    ADD CONSTRAINT pages_pkey PRIMARY KEY (id);


--
-- Name: unique_url; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY websites
    ADD CONSTRAINT unique_url UNIQUE (url);


--
-- Name: websites_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY websites
    ADD CONSTRAINT websites_pkey PRIMARY KEY (id);


--
-- Name: words_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY words
    ADD CONSTRAINT words_pkey PRIMARY KEY (id);


--
-- Name: fk_iddestination; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pages_links
    ADD CONSTRAINT fk_iddestination FOREIGN KEY ("idDestination") REFERENCES pages(id);


--
-- Name: fk_idpage; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pages_words
    ADD CONSTRAINT fk_idpage FOREIGN KEY (idpage) REFERENCES pages(id);


--
-- Name: fk_idpage; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY errors
    ADD CONSTRAINT fk_idpage FOREIGN KEY (idpage) REFERENCES pages(id);


--
-- Name: fk_idrefferer; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pages_links
    ADD CONSTRAINT fk_idrefferer FOREIGN KEY ("idRefferer") REFERENCES pages(id);


--
-- Name: fk_idwebsite; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pages
    ADD CONSTRAINT fk_idwebsite FOREIGN KEY (id_website) REFERENCES websites(id);


--
-- Name: fk_idwork; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pages_words
    ADD CONSTRAINT fk_idwork FOREIGN KEY (idword) REFERENCES words(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

