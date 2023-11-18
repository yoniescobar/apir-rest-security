package com.company.intecap.apibooks.controller;

import com.company.intecap.apibooks.model.Categoria;
import com.company.intecap.apibooks.respose.CategoriaResponseRest;
import com.company.intecap.apibooks.service.ICategoriaService;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1") // prefijo de la ruta  http://localhost:8080/api/v1
public class CategoriaRestController {

    @Autowired // Inyectamos el servicio de categorias para poder utilizarlo en este controlador REST
    private ICategoriaService categoriaService;

    CategoriaResponseRest response = new CategoriaResponseRest();

    @GetMapping("/categorias") // localhost:8080/api/v1/categorias
    public ResponseEntity<CategoriaResponseRest> consultarCategorias(){
        return categoriaService.buscarCategorias(); // Invocamos el metodo buscarCategorias del servicio de categorias para obtener las info de la base de datos
    }

    @GetMapping("/categorias/{id}") // Indica que este metodo se encarga de recibir las peticiones GET a la ruta /v1/categorias/{id}
    public ResponseEntity<CategoriaResponseRest> consultarCategoriaId(@PathVariable  Long id){
        return categoriaService.buscarCategoriaId(id); // Invocamos el metodo buscarCategoriaId del servicio de categorias para obtener la info de la base de datos
    }

    @PostMapping("/categorias")
    public ResponseEntity<CategoriaResponseRest> guardarCategoria(@Valid @RequestBody Categoria request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            response.setMetadata("Respuesta no ok", "400", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            // Verificar que no haya un nombre duplicado antes de crear la categoría
            if (categoriaService.existeCategoriaConNombre(request.getNombre())) {
                response.setMetadata("Respuesta no ok", "500", "Ya existe una categoría con ese nombre...");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // codigo de Bad Request 400
            } else {
                return categoriaService.crear(request);
            }
        }
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseRest> actualizarCategoria(@Valid @RequestBody Categoria request, @PathVariable Long id, BindingResult bindingResult) {


        if(bindingResult.hasErrors()){
            response.setMetadata("Respuesta no ok", "400", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            // Verificar que no haya un nombre duplicado antes de crear la categoría
            if (categoriaService.existeCategoriaConNombre(request.getNombre())) {
                response.setMetadata("Respuesta no ok", "500", "Ya existe una categoría con ese nombre...");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // codigo de Bad Request 400
            } else {
                return categoriaService.actualizar(request, id);
            }
        }

    }
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseRest> eliminarCategoria(@PathVariable Long id) {
        return categoriaService.eliminar(id);
    }

}
