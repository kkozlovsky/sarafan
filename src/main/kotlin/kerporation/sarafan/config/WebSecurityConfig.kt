package kerporation.sarafan.config

import kerporation.sarafan.domain.User
import kerporation.sarafan.repo.UserDetailsRepo
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import java.time.LocalDateTime

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .mvcMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
    }


    @Bean
    fun googlePrincipalExtractor(userDetailsRepo: UserDetailsRepo): PrincipalExtractor {
        return GooglePrincipalExtractor(userDetailsRepo = userDetailsRepo)
    }

    class GooglePrincipalExtractor(val userDetailsRepo: UserDetailsRepo) : PrincipalExtractor {

        override fun extractPrincipal(map: MutableMap<String, Any>): User {
            val id: String = map["sub"] as String
            var user: User? = userDetailsRepo.findById(id).orElse(null)
            if (user == null) {
                user = User()
                user.id = id
                user.name = map["name"] as String
                user.email = map["email"] as String
                user.locale = map["locale"] as String
                user.userpic = map["picture"] as String
            }
            user.lastVisit = LocalDateTime.now()
            return userDetailsRepo.save(user)
        }
    }
}
