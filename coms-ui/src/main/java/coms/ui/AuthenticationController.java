package coms.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import coms.ui.model.AuthenticationRequest;
import coms.ui.model.AuthenticationResponse;
import coms.ui.model.ComsUser;
import coms.ui.security.config.CustomUserDetailsService;
import coms.ui.security.config.JwtUtil;



@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		System.out.println("Inside createAuthenticationToken(): "+authenticationRequest.getUsername()+" , "+authenticationRequest.getPassword());
		try {			
			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			System.out.println(auth);
			if(auth != null) {
				System.out.println(auth.getCredentials()+" "+auth.getName()+"  "+auth.getDetails()+"  "+auth.getPrincipal());
			}
						
			ComsUser usr = (ComsUser)auth.getPrincipal();
			
			String token = jwtUtil.generateToken(usr);
			System.out.println("token="+token);
			return ResponseEntity.ok(new AuthenticationResponse(token, usr.getUser()));
			
		} catch (DisabledException e) {
			e.printStackTrace();
			throw new Exception("USER_DISABLED", e);
		} catch (LockedException e) {
			e.printStackTrace();
			throw new Exception("USER_LOCKED", e);
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		
		//UserDetails userdetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
	}
}
