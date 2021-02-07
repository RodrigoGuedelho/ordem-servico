drop table if exists public.cliente;

CREATE TABLE public.cliente
(
  id bigint NOT NULL,
  email character varying(100),
  nome character varying(70),
  telefone character varying(20),
  CONSTRAINT cliente_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.cliente
  OWNER TO postgres;
