# Gerador de relatório de transações

A ideia central do projeto foi criar um software capaz de automatizar a leitura, processamento e escrita de arquivos de remessa no formato [CNAB](https://www.iugu.com/blog/o-que-e-cnab), retornando seu relatório correspondente. O arquivo de teste chama-se CNAB.txt e está contido na pasta files, na raiz do projeto.

Paralelo a isso, utilizei o projeto para aprender sobre o uso do spring batch e o processamento de dados em lotes, recomendado em sistemas que realizam tarefas com alto volume de dados. Processamento em lote é uma técnica de execução de tarefas de processamento de dados em grandes quantidades, agrupadas em lotes, que são processadas de forma contínua ou sequencial. Em vez de processar cada tarefa individualmente assim que ela é recebida, o processamento em lote agrupa várias tarefas ou transações e as executa de uma só vez em um momento específico. Esse tipo de técnica era bastante utilizada na segunda geração de computadores (1955 - 1965), adequando-se ao cenário limitado da época. Um exemplo de leitura, processamento e escrita pode ser vista na imagem a seguir, a partir de três modelos IBM 1401, 7094 e 1401:

![image](https://github.com/user-attachments/assets/12eb29a1-718e-4007-94e5-4f0403294425)


### Tecnologias utilizadas
Para a realização do projeto, utilizei Spring Boot no backend da aplicação e Postgresql como banco de dados. O arquivo pode ser enviado diretamente pelo backend, lidando com um ou mais arquivos por vez, porém também foi disponibilizada um frontend em angular para melhor disposição visual dos relatórios.

## Arquitetura de solução

#### Controle de Unicidade
Por padrão, arquivos CNAB são processados apenas uma vez. A fim de assegurar essa característica,  o sistema garante a integridade dos dados ao controlar a unicidade das transações com base no nome do arquivo CNAB. Cada arquivo CNAB é nomeado com um ID ou timestamp único para facilitar a identificação e evitar duplicatas.

#### Gestão de Erros
Em caso de erro durante o processamento, é permitido submeter novamente o mesmo arquivo para reiniciar o processamento a partir do ponto onde parou, prevenindo redundância

#### Execução Assíncrona do JOB
O processamento é realizado de forma assíncrona em segundo plano para manter a responsividade do sistema. Com isso, o usuário pode continuar interagindo com o sistema enquanto o processamento está em andamento

## Fluxo geral

1. Escolha do arquivo CNAB;
2. Upload do arquivo;
3. Salvamento local do arquivo (não NFS);
4. Passagem do arquivo como parâmetro para o JOB;
5. Verificação de importação do arquivo;
6. Se já importado, o JOB é encerrado com mensagem de erro, caso contrário, o processamento continua;
7. Leitura das transações do arquivo;
8. Normalização dos dados;
9. Escrita das transações normalizadas no banco de dados.
