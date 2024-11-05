package pe.edu.upeu.sysalmacenfx.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysalmacenfx.modelo.Actor;
import pe.edu.upeu.sysalmacenfx.repositorio.ActorRepository;

import java.util.List;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;

    public Actor save(Actor actor) {
        return actorRepository.save(actor);
    }

    public List<Actor> list() {
        return actorRepository.findAll();
    }

    public Actor findById(int id) {
        return actorRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        actorRepository.deleteById(id);
    }

    public Actor update(Actor actor) {
        return actorRepository.save(actor);
    }
}
