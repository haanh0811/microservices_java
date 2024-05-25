package fr.dauphine.miageif.Sport.service;

import fr.dauphine.miageif.Sport.entity.Sport;

import java.util.List;

public interface SportService {
    public List<Sport> getAllSports();
    public Sport addSport(Sport sport);

}
