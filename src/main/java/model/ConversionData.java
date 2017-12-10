
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author darkferi
 */


@NamedQueries({
        @NamedQuery(
                name = "findConvertRateFirstTry",
                query = "SELECT obj FROM ConversionData obj WHERE obj.fromCurrency LIKE :currency1 AND obj.toCurrency LIKE :currency2"
        )
        ,
        @NamedQuery(
                name = "findConvertRateSecondTry",
                query = "SELECT obj FROM ConversionData obj WHERE obj.toCurrency LIKE :currency1 AND obj.fromCurrency LIKE :currency2"
        )
        ,
        @NamedQuery(
                name = "checkIfDatabaseIsEmpty",
                query = "SELECT obj FROM ConversionData obj"
        )
})


@Entity
public class ConversionData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)   
    private int id;
    @Column(name = "currency1", nullable = false)
    private String fromCurrency;
    @Column(name = "currency2", nullable = false)
    private String toCurrency;
    @Column(name = "rate", nullable = false)
    private float convertRate;
    
    public ConversionData(){
    }
    
    public ConversionData(String fromCurrency, String toCurrency, float convertRate){
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.convertRate = convertRate;
    }
    
    public String getFromCurrency(){
        return fromCurrency;
    }
    
    public String getToCurrency(){
        return toCurrency;
    }
    
    public float getConvertRate(){
        return convertRate;
    }
    
    public void setFromCurrency(String fromCurrency){
        this.fromCurrency = fromCurrency;
    }
    
    public void setToCurrency(String toCurrency){
        this.toCurrency = toCurrency;
    }
    
    public void setConvertRate(float convertRate){
        this.convertRate = convertRate;
    } 
     
}
