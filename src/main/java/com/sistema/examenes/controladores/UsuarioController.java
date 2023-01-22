package com.sistema.examenes.controladores;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.examenes.entidades.Rol;
import com.sistema.examenes.entidades.Usuario;
import com.sistema.examenes.entidades.UsuarioRol;
import com.sistema.examenes.servicios.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//Crear un nuevo usuario
	@PostMapping("/")
	public Usuario guardarUsuario(@RequestBody Usuario usuario) throws Exception {
		usuario.setPerfil("default.png");
		
		usuario.setPassword(this.bCryptPasswordEncoder.encode(usuario.getPassword()));
		
		Set<UsuarioRol> roles = new HashSet<>();
		
		Rol rol = new Rol();
		rol.setId(2L);
		rol.setNombre("NORMAL");
		
		UsuarioRol usuarioRol = new UsuarioRol();
		usuarioRol.setUsuario(usuario);
		usuarioRol.setRol(rol);
		
		 roles.add(usuarioRol);
		
		return usuarioService.guardarUsuario(usuario, roles);
	}
	
	//Los {} son el valor que se pasara(@PathVariable)
	@GetMapping("/{username}")
	public Usuario obtenerUsuario(@PathVariable("username") String username) {
		return usuarioService.obtenerUsuario(username);
	}
	
	//Eliminar un usuario
	@DeleteMapping("/{usuarioId}")
	public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId) {
		usuarioService.eliminarUsuario(usuarioId);
	}
}
