package edu.egg.Biblioteca;

import edu.egg.Biblioteca.Service.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {

    @Autowired
    public UsuarioServiceImp usuarioServiceImp;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //AUNTENTICO POR METODO USERDETAIL, LUEGO PASO CODIFICADOR DE CONTRASEÑA Y CODIFICA
        auth.userDetailsService(usuarioServiceImp).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //ALLOW TO SEE THIS SOURCES WITHOUT LOGIN
        http.authorizeHttpRequests().antMatchers("/css/*", "/js/*", "/img/*", "/*").permitAll()
                .and().formLogin()
                    .loginPage("/logIn")
                    .loginProcessingUrl("/logincheck")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/home")
                    .permitAll()
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/logIn")
                    .permitAll()
                .and().csrf()
                    .disable();
    }
}
