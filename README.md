**Projeto de Extração e Compactação de Dados de PDF**
=====================================================

**Descrição**
-------------

Este projeto foi desenvolvido para extrair dados de uma tabela presente no arquivo PDF, chamado _Anexo I_, do site da ANS. O código realiza as seguintes tarefas:

1.  **Extração de Dados**: O código extrai os dados da tabela "Rol de Procedimentos e Eventos em Saúde" presente no PDF.
    
2.  **Formatação em CSV**: Os dados extraídos são salvos em um arquivo no formato CSV.
    
3.  **Compactação**: O arquivo CSV gerado é compactado em um arquivo ZIP.
    
4.  **Substituição de Abreviações**: As abreviações OD e AMB são substituídas por suas descrições completas, conforme a legenda encontrada no rodapé do PDF.
    

**Tecnologias Utilizadas**
--------------------------

*   **Java**: A principal linguagem utilizada para o desenvolvimento do código.
    
*   **Apache PDFBox**: Para manipulação e leitura de PDFs.
    
*   **Tabula**: Biblioteca usada para extrair as tabelas do PDF.
    
*   **JSoup**: Para fazer o scraping e download do PDF.
    
*   **Java IO**: Para leitura, escrita e manipulação de arquivos CSV e ZIP.
    

**Funcionalidades**
-------------------

*   **Download do PDF**: O código faz o download automaticamente do arquivo PDF _Anexo I_.
    
*   **Extração de Tabelas**: Utiliza o Tabula para extrair as tabelas do PDF.
    
*   **Substituição de Abreviações**: Substitui as abreviações OD e AMB pelas descrições completas conforme a legenda do rodapé.
    
*   **Geração de Arquivo CSV**: Salva as informações extraídas em um arquivo CSV.
    
*   **Compactação ZIP**: O arquivo CSV gerado é compactado em um arquivo ZIP.
    

**Como Usar**
-------------

### **Passo 1: Pré-requisitos**

Antes de executar o código, verifique se você tem o Java instalado no seu computador. Você pode baixar a versão mais recente do [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

### **Passo 2: Download do Código**

Clone ou baixe o código do projeto para o seu ambiente local.

### **Passo 3: Execução do Código**

1.  Compile e execute o código no seu IDE preferida ou pelo terminal.
    
2.  Ao executar o código, ele irá:
    
    *   Realizar o download do arquivo PDF do site da ANS.
        
    *   Extrair as tabelas do PDF.
        
    *   Substituir as abreviações de OD e AMB pelas descrições completas.
        
    *   Gerar um arquivo CSV com os dados extraídos.
        
    *   Compactar o arquivo CSV em um arquivo ZIP chamado Teste_Adriano_Xavier.zip.
        

### **Passo 4: Verifique os Arquivos Gerados**

Após a execução do código, o arquivo ZIP será gerado na sua pasta de Downloads com o nome Teste_Adriano_Xavier.zip.

**Detalhes Técnicos**
---------------------

### **1\. Extração de Tabelas do PDF**

Usei o ObjectExtractor e o SpreadsheetExtractionAlgorithm da biblioteca _Tabula_ para extrair as tabelas do PDF. O código percorre cada página do PDF e coleta as informações presentes nas células da tabela.

### **2\. Substituição de Abreviações**

As abreviações OD e AMB são mapeadas para suas descrições completas extraídas da legenda do rodapé do PDF. O código utiliza expressões regulares para identificar e substituir essas abreviações nas células da tabela.

### **3\. Compactação em ZIP**

Após a criação do arquivo CSV, a classe FileCompressor é usada para compactar o arquivo CSV em um arquivo ZIP com o nome Teste_Adriano_Xavier.zip.
