package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity // anotação de configuração Security
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                // Desativa a proteção CSRF (Cross-Site Request Forgery)
                // Não é necessária pois a API é stateless (sem sessão)
                .csrf(csrf -> csrf.disable())

                // Define que a aplicação não vai criar ou usar sessões HTTP
                // Cada requisição deve se autenticar de forma independente via token JWT
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Define as regras de autorização para as rotas da aplicação
                .authorizeHttpRequests(req -> req

                        // Permite acesso público à rota de login (POST /login)
                        // Qualquer usuário pode acessar sem estar autenticado
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()

                        // Todas as outras rotas exigem autenticação
                        .anyRequest().authenticated()
                )

                // Adiciona o filtro JWT antes do filtro padrão do Spring
                // Garante que o token seja validado primeiro
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)

                // Constrói e retorna o objeto SecurityFilterChain
                .build();
    }

    @Bean // serve para injetar uma classe do proprio Spring boot
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
