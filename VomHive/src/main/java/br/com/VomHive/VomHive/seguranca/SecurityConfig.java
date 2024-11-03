package br.com.VomHive.VomHive.seguranca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/profile/adicionar","/profile/atualizar/{id}","/profile/deletar/{id}")
                        .hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated())
                .formLogin((form) -> form.loginPage("/login").failureUrl("/login?erro=true")
                        .defaultSuccessUrl("/index1").permitAll())
                .logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout=true").permitAll())
                .exceptionHandling((exception) ->
                        exception.accessDeniedHandler((request,response,accessDeniedHandler) ->
                        {response.sendRedirect("/acesso_negado");}));

        return http.build();

    }

}
