package com.compath.core.api.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.compath.storage.db.core.entity.member.Member;
import com.compath.storage.db.core.entity.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Deprecated
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info(">>>>>> UserDetailServiceImpl loadUserByUsername");
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with email	: " + email));

		List<GrantedAuthority> authorities = getAuthorities(member);

		return UserDetailsImpl.builder()
			.id(member.getId())
			// .password(member.getPassword())
			.authorities(authorities)
			.build();
	}

	private List<GrantedAuthority> getAuthorities(Member member) {
		return member.getRole() != null ?
			List.of(new SimpleGrantedAuthority(member.getRole().getValue()))
			: List.of();
	}
}
