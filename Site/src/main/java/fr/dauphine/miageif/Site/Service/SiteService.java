package fr.dauphine.miageif.Site.Service;


import fr.dauphine.miageif.Site.Entity.Site;

import java.util.List;

public interface SiteService {

    Site createSite(Site site);

    Site deleteSite(String name);

    Site updateSite(String oldName, Site udatedSite);

    List<Site> getSiteByVille(String cityName);

    List<Site> getAllSites();

    Site getSiteByName(String siteName);
}
