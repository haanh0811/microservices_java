package fr.dauphine.miageif.CalendrierSpec.service;

import fr.dauphine.miageif.CalendrierSpec.entity.CalendrierSpec;
import fr.dauphine.miageif.CalendrierSpec.entity.Spec;

import java.util.List;

public interface SpecService {
    public List<Spec> getAllSpecs();
    public Spec getSpecById(String id);
    public Spec addSpec(Spec spec);
    public Spec updateSpec(String id, Spec updatedSpectator);
    public void deleteSpec(String id);
    public Spec addEventToSpec(String spectatorId, CalendrierSpec event);
    public Spec deleteEventFromSpec(String spectatorId, String eventId);
}
