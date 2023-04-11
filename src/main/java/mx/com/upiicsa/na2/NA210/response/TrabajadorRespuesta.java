package mx.com.upiicsa.na2.NA210.response;

import lombok.Data;
import mx.com.upiicsa.na2.NA210.model.entity.TrabajadorModel;

import java.util.List;

@Data
public class TrabajadorRespuesta {
    private List<TrabajadorModel> contenido;
    private int numeroPagina;
    private int sizePagina;
    private long totalElementos;
    private int totalPaginas;
    private boolean ultima;

}