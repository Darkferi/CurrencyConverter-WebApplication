
package view;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import controller.ServerController;

/**
 * Handles all interaction with the account JSF page.
 */
@Named("converterManager")
@ConversationScoped
public class ConverterManager implements Serializable {
    @EJB
    private ServerController serverController;
    private String fromCurrency;
    private String toCurrency;
    private Float moneyAmount;
    private Float resultOfConversion;
    private Exception transactionFailure;
    private Boolean conversationStarted = false;
    
    @Inject
    private Conversation conversation;
    private Float convertRate;
    
    
    public void setMoneyAmount(Float moneyAmount){
        this.moneyAmount = moneyAmount;
    }
    
    public Float getMoneyAmount(){
        return this.moneyAmount; 
    }
    
    public void setFromCurrency(String fromCurrency){
        this.fromCurrency = fromCurrency;
    }
    
    public String getFromCurrency(){
        return fromCurrency;
    }
   
    public void setToCurrency(String toCurrency){
        this.toCurrency = toCurrency;
    }
   
    public String getToCurrency(){
        return toCurrency;
    }
    
    public Float getResultOfConversion(){
        if(resultOfConversion == null){
            new Float(0.0);
        }
        return resultOfConversion;
    }
    
    
    public void doConvert(){
        try {
            if (!conversationStarted){
                if (conversation.isTransient()) {
                    conversation.begin();
                }
                conversationStarted = true;
            }
            serverController.initiate();
            transactionFailure = null;
            convertRate = serverController.getConvertRate(fromCurrency, toCurrency);
            resultOfConversion = moneyAmount*convertRate;
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    
    
    private void handleException(Exception e) {
        if (!conversation.isTransient()) {
            conversation.end();
            conversationStarted = false;
        }
        e.printStackTrace(System.err);
        transactionFailure = e;
    }
    
        
    public boolean getSuccess() {
        return (transactionFailure == null);
        //return true;
    }
    
   
    public Exception getException() {
        return transactionFailure;
    }
}