
package controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import integration.ConversionRateDAO;
import model.ConversionData;

/**
 * A controller. All calls to the model that are executed because of an action taken by the cashier
 * pass through here.
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class ServerController {
    @EJB ConversionRateDAO rateDAO;
    
    public float getConvertRate(String fromCurrency, String toCurrency){
        if (fromCurrency.equalsIgnoreCase(toCurrency)){
            return 1;
        }
        ConversionData convertObj;
        convertObj = rateDAO.getConvertRate(fromCurrency, toCurrency); 
        float convRate = convertObj.getConvertRate();
        return convRate;
        //return 0;
    }
    
    public void initiate(){
        rateDAO.initiateDatabase();
    }
}
