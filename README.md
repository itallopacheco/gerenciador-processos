# gerenciador-processos

## Como subir a API

### 1. Clone o repo
    
    git clone https://github.com/itallopacheco/gerenciador-processos.git

### 2. Acesse o diretorio
    cd gerenciador-processos

### 3. Buildar o projeto 
    ./mvnw clean package

### 4. Criar a imagem da API 
    docker build -t gerenciador-processos .

### 5. Subir o docker com docker compose
    docker compose up -d
