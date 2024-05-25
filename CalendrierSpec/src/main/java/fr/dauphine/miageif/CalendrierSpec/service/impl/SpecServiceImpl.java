package fr.dauphine.miageif.CalendrierSpec.service.impl;

import fr.dauphine.miageif.CalendrierSpec.entity.CalendrierSpec;
import fr.dauphine.miageif.CalendrierSpec.entity.Spec;
import fr.dauphine.miageif.CalendrierSpec.repository.SpecRepository;
import fr.dauphine.miageif.CalendrierSpec.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SpecServiceImpl implements SpecService {
    @Autowired
    private SpecRepository specRepository;

    public List<Spec> getAllSpecs(){
        return specRepository.findAll();
    }
    public Spec getSpecById(String id) {
        return specRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Spectator not found with id " + id));
    }

    public Spec addSpec(Spec spec) {

        return specRepository.save(spec);
    }
    public Spec updateSpec(String id, Spec updatedSpectator) {
        Spec spectator = getSpecById(id);
        spectator.setName(updatedSpectator.getName());
        spectator.setCalendrierSpecList(updatedSpectator.getCalendrierSpecList());
        return specRepository.save(spectator);
    }
    public void deleteSpec(String id) {
        getSpecById(id);
        specRepository.deleteById(id);
    }
    public Spec addEventToSpec(String spectatorId, CalendrierSpec event) {
        Spec spectator = getSpecById(spectatorId);
        spectator.getCalendrierSpecList().add(event);
        return specRepository.save(spectator);
    }
    public Spec updateEventInSpec(String spectatorId, String eventId, CalendrierSpec updatedEvent) {
        Spec spectator = getSpecById(spectatorId);
        List<CalendrierSpec> events = spectator.getCalendrierSpecList();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventId().equals(eventId)) {
                events.set(i, updatedEvent);
                break;
            }
        }
        return specRepository.save(spectator);
    }

    public Spec deleteEventFromSpec(String spectatorId, String eventId) {
        Spec spectator = getSpecById(spectatorId);
        spectator.getCalendrierSpecList().removeIf(event -> event.getEventId().equals(eventId));
        return specRepository.save(spectator);
    }
}
