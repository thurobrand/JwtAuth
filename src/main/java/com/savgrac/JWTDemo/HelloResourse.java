package com.savgrac.JWTDemo;

import com.savgrac.JWTDemo.service.MyUserDetailsService;
import com.savgrac.JWTDemo.util.JwtUtil;
import models.AuthenticationRequest;
import models.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloResourse {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping({"/hello"})
    public String hello(){
        return "Hello World";
    }

    @RequestMapping(value = {"/authenticate"}, method = RequestMethod.POST)
    public ResponseEntity<?> createSecurityToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
    {
        try {
             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
         }catch (BadCredentialsException bde){
                throw new Exception("Incorrect username and password");
        }
        final UserDetails userDetails  = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
