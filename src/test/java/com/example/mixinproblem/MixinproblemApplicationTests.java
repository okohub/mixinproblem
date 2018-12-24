package com.example.mixinproblem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.jackson2.SecurityJackson2Modules;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping.NON_FINAL;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author onurozcan
 */
public class MixinproblemApplicationTests {

  @Test
  public void shouldContextsEqual() throws IOException {
    ObjectMapper mapper =
        new ObjectMapper().registerModules(SecurityJackson2Modules.getModules(getClass().getClassLoader()))
                          .enableDefaultTypingAsProperty(NON_FINAL, "typeClass");

    SecurityContext context = buildSecurityContext();
    String jsonContext = mapper.writeValueAsString(context);
    Object retrievedContext = mapper.readValue(jsonContext, Object.class);

    assertThat(retrievedContext).isEqualTo(context);
  }

  @Test
  public void shouldContextsNotEqual() throws IOException {
    ObjectMapper mapper =
        new ObjectMapper().registerModules(SecurityJackson2Modules.getModules(getClass().getClassLoader()))
                          .enableDefaultTypingAsProperty(NON_FINAL, "typeClass");
    //custom inclusions
    mapper.setDefaultPropertyInclusion(JsonInclude.Value.construct(ALWAYS, NON_NULL))
          .setSerializationInclusion(NON_ABSENT);
    //custom inclusions

    SecurityContext context = buildSecurityContext();
    String jsonContext = mapper.writeValueAsString(context);
    Object retrievedContext = mapper.readValue(jsonContext, Object.class);

    assertThat(retrievedContext).isNotEqualTo(context);
  }

  private SecurityContext buildSecurityContext() {
    SecurityContext context = new SecurityContextImpl();
    Foo principal = new Foo();
    principal.setName("foo");
    principal.setLabel("bar");
    List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("Test"));
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,
                                                                                                 null,
                                                                                                 authorities);
    context.setAuthentication(authentication);
    return context;
  }
}

