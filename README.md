# OpenAI Movie Store Assistant #
Este projeto é uma API construída com Spring Boot, que utiliza a API do OpenAI para fornecer respostas baseadas em modelos de IA (GPT-3.5-turbo). A aplicação inclui um serviço que envia perguntas para o modelo da OpenAI e retorna respostas, simulando uma experiência de assistente para uma loja de filmes.

## Funcionalidades ##
-Enviar perguntas à API da OpenAI e receber respostas baseadas em IA.
-Integração com o modelo GPT-3.5-turbo para processamento de linguagem natural.
-Rota REST simples para interagir com o assistente virtual.
-Estrutura do Projeto
-Controlador: MoviestoreAssistantController
-O controlador principal define um endpoint REST que aceita perguntas do usuário e retorna respostas da IA.

java
Copy code
@RestController
@RequestMapping("/moviestore")
public class MoviestoreAssistantController {

    @Autowired
    private OpenAiService openAiService;

    @GetMapping("/ask")
    public String askQuestion(@RequestParam String question) {
        return openAiService.getResponseFromAi(question);
    }
}
Endpoint: /moviestore/ask
Parâmetro: question - A pergunta do usuário para o assistente da OpenAI.
Resposta: A resposta da IA baseada na entrada fornecida.
Serviço: OpenAiService
O serviço que encapsula a lógica de comunicação com a API da OpenAI.

java
Copy code
@Service
public class OpenAiService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.model}")
    private String model;

    private static final String OPEN_AI_URL = "https://api.openai.com/v1/chat/completions";

    public String getResponseFromAi(String message) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = createHeaders();
        String requestBody = createRequestBody(message);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(OPEN_AI_URL, HttpMethod.POST, request, String.class);

        return response.getBody();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        return headers;
    }

    private String createRequestBody(String message) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("messages", new JSONObject[] {
            new JSONObject().put("role", "user").put("content", message)
        });
        return requestBody.toString();
    }
}
O OpenAiService faz uma chamada HTTP POST para a API da OpenAI.
Constrói o corpo da requisição com o modelo e a mensagem fornecida pelo usuário.
Retorna a resposta da IA em formato JSON.
Arquivo de Configuração: application.properties
properties
Copy code
spring.application.name=ai
###spring.ai.openai.api-key=sk-xxxxxxxxxxxxxxxxxxxxx
spring.ai.openai.model=gpt-3.5-turbo
spring.ai.openai.api-key: Chave da API fornecida pelo OpenAI (comente ou remova antes de fazer o commit).
spring.ai.openai.model: O modelo GPT que será utilizado para processar as perguntas.
Instalação e Configuração
Clone o repositório:

bash
Copy code
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
Adicione a chave da API do OpenAI no arquivo application.properties:

properties
Copy code
spring.ai.openai.api-key=sk-seu-key-aqui
spring.ai.openai.model=gpt-3.5-turbo
Execute a aplicação Spring Boot:

bash
Copy code
./mvnw spring-boot:run
Acesse a API em http://localhost:8080/moviestore/ask?question=Qual+é+o+melhor+filme+de+2024

Dependências
Spring Boot
Spring Web
RestTemplate
OpenAI API
Contribuição
Se quiser contribuir, por favor, faça um fork do projeto, crie um branch, faça suas mudanças e abra um pull request.
