package org.springframework.samples.tea.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTests {

	@InjectMocks
	protected UserDetailsServiceImpl userDetailsServiceImpl;

	@Mock
	private UsuarioService usuarioService;

	@Test
	void shouldFindEventsbyStudentNotExist() {
		Pair<String, String> user = Pair.with("alumno", "PASSword7");
        when(usuarioService.getUser(any())).thenReturn(user);

        UserDetails u = userDetailsServiceImpl.loadUserByUsername("JaviMartinez");

        assertThat(u.getAuthorities().iterator().next().getAuthority()).isEqualTo("alumno");
	}

	@Test
	void shouldThrowUsernameNotFoundException() {
        when(usuarioService.getUser(any())).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {userDetailsServiceImpl.loadUserByUsername("JaviMartinez");});

        assertThat(exception.getMessage()).isEqualTo("User details not found with this username: JaviMartinez");
	}

}
