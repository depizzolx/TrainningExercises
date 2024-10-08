SELECT A017_CD_ESTADO_CIVIL   AS CODIGO,
       A017_DESC_ESTADO_CIVIL AS DESCRICAO 
  FROM T017_ESTADO_CIVIL
 WHERE A017_IND_DESAT=0
 ORDER BY A017_DESC_ESTADO_CIVIL; --Query que lista os estados civis que não foram desativados

SELECT NVL(MAX(A.A017_CD_ESTADO_CIVIL),0)+1 AS NEXT 
  FROM T017_ESTADO_CIVIL A; --Query que busca o maior resultado da coluna em questão, e soma 1 a ele. Se o resultado for nulo, ele substitui o valor por 1

SELECT T.A017_DESC_ESTADO_CIVIL DESCRICAO 
  FROM T017_ESTADO_CIVIL T;

SELECT *
  FROM T017_ESTADO_CIVIL;
  
CREATE TABLE TDEPIZZOL_PRODUTO 
(
  CD_PRODUTO   NUMBER,
  DS_PRODUTO   VARCHAR2(244),
  NM_USUARIO   VARCHAR2(244),
  DT_ALTERACAO DATE,
  DT_INCLUSAO  DATE,
  IND_DESAT    NUMBER DEFAULT 0
); 

SELECT * 
  FROM TDEPIZZOL_PRODUTO;
  
--
  
SELECT CD_PRODUTO AS CODIGO,
       DS_PRODUTO AS DESCRICAO 
  FROM TDEPIZZOL_PRODUTO
 WHERE IND_DESAT = 0
 ORDER BY DS_PRODUTO; --Query que lista os estados civis que não foram desativados

SELECT NVL(MAX(A.CD_PRODUTO),0)+1 AS NEXT 
  FROM TDEPIZZOL_PRODUTO A; --Query que busca o maior resultado da coluna em questão, e soma 1 a ele. Se o resultado for nulo, ele substitui o valor por 1

SELECT T.DS_PRODUTO AS DESCRICAO 
  FROM TDEPIZZOL_PRODUTO T;
