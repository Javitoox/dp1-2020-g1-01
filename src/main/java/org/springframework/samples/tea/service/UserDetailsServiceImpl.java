package org.springframework.samples.tea.service;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private UsuarioService usuarioService;

	@Autowired
	public UserDetailsServiceImpl(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Pair<String, String> user = usuarioService.getUser(username);
		if(user == null) {
			throw new UsernameNotFoundException("User details not found with this username: " + username);
		}
		List<SimpleGrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(user.getValue0()));
		return new User(username, user.getValue1(), authList);
	}

}
