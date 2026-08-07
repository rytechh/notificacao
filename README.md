# 📬Notificacao — Task Scheduler Ecosystem

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)

Microsserviço especialista no processamento e disparo de notificações por e-mail no **Task Scheduler Ecosystem**, utilizando **Thymeleaf** para renderização de templates HTML dinâmicos e conexão SMTP com suporte a SSL/TLS.

> 💡 **Nota:** Este serviço é um componente interno do ecossistema. Para a documentação completa da API e orquestração das requisições, acesse o repositório do [BFF Agendador de Tarefas](https://github.com/rytechh/bff-agendador-tarefas).

---

## 📌 Responsabilidades do Serviço

* **Disparo de E-mails:** Processamento e envio de e-mails consumindo o endpoint `/email`.
* **Templates HTML Dinâmicos:** Renderização de mensagens estilizadas via **Thymeleaf** utilizando o template `notificacao.html`.
* **Configuração de Remetente Personalizado:** Suporte a propriedades customizadas de envio (`envio.email.remetente` e `nomeRemetente`).
* **Gestão do Estado da Notificação:** Rastreamento do ciclo de disparo via `StatusNotificacaoEnum`.
* **Conectividade SMTP Resiliente:** Conexão segura via SSL (Porta 465) com parâmetros otimizados de *timeout* (`connectiontimeout`, `writetimeout`).

---

## 🛠️ Tech Stack & Infraestrutura

* **Linguagem/Framework:** Java 21 / Spring Boot 3.x
* **Build Tool:** Gradle
* **Template Engine:** Thymeleaf
* **Integrações:** JavaMailSender (SMTP via SSL/TLS)
* **Containerização:** Docker (`Dockerfile` e `.env`)
* **Porta Padrão de Execução:** `8082`

---

## ⚙️ Variáveis de Ambiente Recomendadas

| Variável | Descrição | Valor Padrão (Local) |
| :--- | :--- | :--- |
| `SERVER_PORT` | Porta de execução do microsserviço | `8082` |
| `SPRING_MAIL_PORT` | Porta do servidor SMTP (SSL) | `465` |
| `ENVIO_EMAIL_REMETENTE` | E-mail do remetente das notificações | `seu-email@dominio.com` |
| `ENVIO_EMAIL_NOMEREMETENTE` | Nome de exibição do remetente | `Rytech` |

---

## 👤 Autor

Desenvolvido por **Raian Santos** — [@rytechh](https://github.com/rytechh)
