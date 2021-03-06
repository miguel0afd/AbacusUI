/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.cediant.abacus;

import es.cediant.cimon.OpenLMIproviders;
import es.cediant.cimon.Service;
import es.cediant.cimon.ServiceDataModel;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author miguel
 */
@ManagedBean
@RequestScoped
public class ServicesBean {

    final Logger logger = LoggerFactory.getLogger(ServicesBean.class);
    
    private OpenLMIproviders providers;
    private List<Service> services;
    private Service selectedService;   
    private ServiceDataModel servicesName;
        
    /**
     * Creates a new instance of ServicesBean
     */
    public ServicesBean() {
        this.providers = new OpenLMIproviders();
        this.services = new ArrayList<>();
    }
    
    public List<Service> getServices() {
        try {
            fetchServices();
            return services;
        } catch (Throwable ex) {
            logger.error("Error while getting services.");
            logger.error(ex.getMessage());
            return null;
        }
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }
    
    public void fetchServices() {
        try {
            providers.fetchServices();
            providers.sortServices();
            this.services = providers.getServices();
            this.servicesName = new ServiceDataModel(this.services);
        } catch (Exception ex) {
            logger.error("Error while fetching services.");
            logger.error(ex.getMessage());
        }
    }
    
    public Service getSelectedService(){
        return this.selectedService;
    }
    
    public void setSelectedService(Service selectedService){
        this.selectedService = selectedService;
    }
    
    public ServiceDataModel getServicesName() {
        this.setServicesName(new ServiceDataModel(this.getServices()));
        return this.servicesName;
    }

    public void setServicesName(ServiceDataModel servicesName) {
        this.servicesName = servicesName;
    }
    
    public void onRowSelect(SelectEvent event) {  
        String name = ((Service) event.getObject()).getName();
        this.logger.info("\""+name+"\" selected.");
    }
    
    public void onRowUnselect(UnselectEvent event) { 
        String name = ((Service) event.getObject()).getName();
        this.logger.info("\""+name+"\" unselected.");
    }
}
