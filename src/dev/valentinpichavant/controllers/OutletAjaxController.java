package dev.valentinpichavant.controllers;

import dev.valentinpichavant.beans.Outlet;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by valentinpichavant on 1/6/17.
 */
@RestController
public class OutletAjaxController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    LocalSessionFactoryBean localSessionFactoryBean;


    @RequestMapping(value = "/outlet/activate/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public boolean getOutletById(@PathVariable Long id) throws Exception {
        Session session = localSessionFactoryBean.getObject().openSession();
        String query = "select outlet from Outlet outlet where outlet.id = :id";
        List results = session.createQuery(query).setParameter("id", id).list();
        if (results != null) {
            Logger.getLogger(OutletAjaxController.class.getName()).log(Level.INFO, "Outlet found");
            Outlet outlet = (Outlet) results.get(0);
            boolean success = false;
            if (outlet.getActivated()) {
                try {
                    String[] cmd = {"/bin/bash", "-c", "sudo " + OutletAjaxController.class.getResource("codesend").getPath() + " " + outlet.getNumberOff()};
                    Logger.getLogger(OutletAjaxController.class.getName()).log(Level.INFO, cmd[2].toString());
                    Process pb = Runtime.getRuntime().exec(cmd);
                    success = true;
                } catch (Error e) {
                    Logger.getLogger(OutletAjaxController.class.getName()).log(Level.INFO, e.getMessage());
                }
                Logger.getLogger(OutletAjaxController.class.getName()).log(Level.INFO, "Off executed");
            } else {
                try {
                    String[] cmd = {"/bin/bash", "-c", "sudo " + OutletAjaxController.class.getResource("codesend").getPath() + " " + outlet.getNumberOn()};
                    Process pb = Runtime.getRuntime().exec(cmd);
                    Logger.getLogger(OutletAjaxController.class.getName()).log(Level.INFO, cmd[2].toString());
                    success = true;
                } catch (Error e) {
                    Logger.getLogger(OutletAjaxController.class.getName()).log(Level.INFO, e.getMessage());
                }
                Logger.getLogger(OutletAjaxController.class.getName()).log(Level.INFO, "On executed");
            }
            if (success == true) {
                outlet.setActivated(!outlet.getActivated());
                Transaction tx = session.beginTransaction();
                outlet.setModificationTime(new Date());
                session.update(outlet);
                tx.commit();
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}
