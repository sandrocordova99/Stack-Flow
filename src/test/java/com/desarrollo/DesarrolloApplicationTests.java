package com.desarrollo;

import com.desarrollo.dto.tarea.TareaResponseDTO;
import com.desarrollo.repository.TareaRepository;
import com.desarrollo.repository.userRepository.UserRepository;
import com.desarrollo.service.tareas.imp.TareasServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DesarrolloApplicationTests {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TareaRepository tareaRepository;

	@Autowired
	private TareasServiceImpl tareasService;

	@Test
	void listarTareas_usuarioConTareas_retornaListaDeTareas() {
		Long userId = 1L;
		List<TareaResponseDTO> resultado = tareasService.listarTareas(userId);

		resultado.forEach(tarea -> {
			System.out.println("Tarea: " + tarea.getId() + " - " + tarea.getNombre());
		});

	}

}
