--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: DimBusiness; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."DimBusiness" (
    business_id integer NOT NULL,
    name character varying(30),
    city character varying(10)
);


ALTER TABLE public."DimBusiness" OWNER TO postgres;

--
-- Name: DimCity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."DimCity" (
    city_id integer NOT NULL,
    name character varying(10)
);


ALTER TABLE public."DimCity" OWNER TO postgres;

--
-- Name: DimUser; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."DimUser" (
    user_id integer NOT NULL,
    name text,
    review_count integer,
    average_stars double precision
);


ALTER TABLE public."DimUser" OWNER TO postgres;

--
-- Name: DimWeather; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."DimWeather" (
    weather_id integer NOT NULL,
    temperature double precision,
    humidity double precision,
    pressure double precision,
    winddirection double precision,
    windspeed double precision,
    description text,
    city character varying(10)
);


ALTER TABLE public."DimWeather" OWNER TO postgres;

--
-- Name: FactCheckin; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."FactCheckin" (
    checkin_id integer NOT NULL,
    business_id integer,
    weather_id integer,
    city_id integer,
    date timestamp with time zone
);


ALTER TABLE public."FactCheckin" OWNER TO postgres;

--
-- Name: FactReview; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."FactReview" (
    review_id integer NOT NULL,
    user_id integer,
    business_id integer,
    weather_id integer,
    city_id integer,
    stars integer NOT NULL,
    date timestamp with time zone
);


ALTER TABLE public."FactReview" OWNER TO postgres;

--
-- Name: DimBusiness DimBusiness_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."DimBusiness"
    ADD CONSTRAINT "DimBusiness_pkey" PRIMARY KEY (business_id);


--
-- Name: DimCity DimCity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."DimCity"
    ADD CONSTRAINT "DimCity_pkey" PRIMARY KEY (city_id);


--
-- Name: DimUser DimUser_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."DimUser"
    ADD CONSTRAINT "DimUser_pkey" PRIMARY KEY (user_id);


--
-- Name: DimWeather DimWeather_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."DimWeather"
    ADD CONSTRAINT "DimWeather_pkey" PRIMARY KEY (weather_id);


--
-- Name: FactCheckin FactCheckin_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."FactCheckin"
    ADD CONSTRAINT "FactCheckin_pkey" PRIMARY KEY (checkin_id);


--
-- Name: FactReview FactReview_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."FactReview"
    ADD CONSTRAINT "FactReview_pkey" PRIMARY KEY (review_id);


--
-- PostgreSQL database dump complete
--

