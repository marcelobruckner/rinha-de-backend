CREATE TABLE IF NOT EXISTS pessoas
(
  id UUID PRIMARY KEY,
  apelido VARCHAR(32) NOT NULL UNIQUE,
  nome VARCHAR(100) NOT NULL, 
  nascimento VARCHAR(10) NOT NULL,
  stack VARCHAR(255)
);

CREATE INDEX idx_pessoas_apelido ON pessoas (apelido);
CREATE INDEX idx_pessoas_nome ON pessoas (nome);
CREATE INDEX idx_pessoas_stack ON pessoas (stack);