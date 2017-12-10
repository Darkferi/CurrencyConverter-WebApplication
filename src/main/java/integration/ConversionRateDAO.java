
package integration;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import model.ConversionData;


@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class ConversionRateDAO {
    private boolean firstTime = true;
    @PersistenceContext(unitName = "currencyExchange")
    private EntityManager em;
    
    public ConversionData getConvertRate(String fromCurrency, String toCurrency){
        List<ConversionData> convDataList = null;
        ConversionData convData = new ConversionData();
        ConversionData temp = new ConversionData();
        convDataList = em.createNamedQuery("findConvertRateFirstTry", ConversionData.class).       
                setParameter("currency1", fromCurrency).setParameter("currency2", toCurrency).getResultList();
        if(convDataList.size()==0){
            convDataList = em.createNamedQuery("findConvertRateSecondTry", ConversionData.class).       
                setParameter("currency1", fromCurrency).setParameter("currency2", toCurrency).getResultList();
            if(convDataList.size()==0){
                throw new EntityNotFoundException("Hey error there is no converter with your currency");
            }
            temp = convDataList.get(0);
            convData.setFromCurrency(temp.getToCurrency());
            convData.setToCurrency(temp.getFromCurrency());
            convData.setConvertRate((float)(1/temp.getConvertRate()));
            return convData;
        }
        convData = convDataList.get(0);
        return convData;
    }
    
    public void initiateDatabase(){
        List<ConversionData> convDataList = null;
        convDataList = em.createNamedQuery("checkIfDatabaseIsEmpty", ConversionData.class).getResultList();
        if(convDataList.size()==0){
            ConversionData record1 = new ConversionData("Euro","Dollar", (float) 1.18);
            em.persist(record1);
            ConversionData record2 = new ConversionData("Euro","Ruble", (float) 69.71);
            em.persist(record2);
            ConversionData record3 = new ConversionData("Euro","Sek",(float) 9.96);
            em.persist(record3);
            ConversionData record4 = new ConversionData("Dollar","Sek", (float) 8.47);
            em.persist(record4);
            ConversionData record5 = new ConversionData("Dollar","Ruble", (float) 59.24);
            em.persist(record5);
            ConversionData record6 = new ConversionData("Sek","Ruble", (float) 7.0);
            em.persist(record6);
            firstTime = false;
        }
    }
}
