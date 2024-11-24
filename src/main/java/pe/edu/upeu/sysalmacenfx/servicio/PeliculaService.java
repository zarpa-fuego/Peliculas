package pe.edu.upeu.sysalmacenfx.servicio;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysalmacenfx.modelo.Actor;
import pe.edu.upeu.sysalmacenfx.modelo.Genero;
import pe.edu.upeu.sysalmacenfx.modelo.Pelicula;
import pe.edu.upeu.sysalmacenfx.repositorio.ActorRepository;
import pe.edu.upeu.sysalmacenfx.repositorio.GeneroRepository;
import pe.edu.upeu.sysalmacenfx.repositorio.PeliculaRepository;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Service
public class PeliculaService {

    @Autowired
    DataSource dataSource;

    @Autowired
    private PeliculaRepository actorRepository;

    public Pelicula save(Pelicula pelicula) {
        return actorRepository.save(pelicula);
    }

    public List<Pelicula> list() {
        return actorRepository.findAll();
    }

    public Pelicula findById(int id) {
        return actorRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        actorRepository.deleteById(id);
    }

    public Pelicula update(Pelicula pelicula) {
        return actorRepository.save(pelicula);
    }

    public File getFile(String filex) {
        System.out.println("LLego a get file");
        File newFolder = new File("jasper");
        String ruta = newFolder.getAbsolutePath();
        //CAMINO = Paths.get(ruta+"/"+"reporte1.jrxml");
        Path CAMINO = Paths.get(ruta + "/" + filex);
        System.out.println("Llegasss Ruta 2:" + CAMINO.toAbsolutePath().toFile());
        return CAMINO.toFile();
    }

    public JasperPrint runReport() throws JRException, SQLException {
        // Verificar si la venta existe
        System.out.println("Llego a run report");
        HashMap<String, Object> param = new HashMap<>();
        // Obtener ruta de la imagen
        // Agregar parámetros
        String imgen = getFile("logonetflix.png").getAbsolutePath();
        param.put("imagenurl", imgen);

        // Cargar el diseño del informe
        JasperDesign jdesign = JRXmlLoader.load(getFile("peliculas.jrxml"));
        JasperReport jreport = JasperCompileManager.compileReport(jdesign);
        // Llenar el informe
        return JasperFillManager.fillReport(jreport, param, dataSource.getConnection());
    }
}