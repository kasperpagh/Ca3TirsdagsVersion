/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author kaspe
 */
@Entity
public class ExchangeRates implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dato;
    private String rerfcur;
    @OneToMany(cascade = CascadeType.ALL)
    private List<SingleExchangeRate> rates;
    

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public ExchangeRates(String dato, String rerfcur, List<SingleExchangeRate> rates)
    {

        this.dato = dato;
        this.rerfcur = rerfcur;
        this.rates = rates;
    }

    public ExchangeRates()
    {
    }


    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExchangeRates))
        {
            return false;
        }
        ExchangeRates other = (ExchangeRates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entity.ExchangeRates[ id=" + id + " ]";
    }

    public String getDato()
    {
        return dato;
    }

    public void setDato(String dato)
    {
        this.dato = dato;
    }

    public String getRerfcur()
    {
        return rerfcur;
    }

    public void setRerfcur(String rerfcur)
    {
        this.rerfcur = rerfcur;
    }

    public List<SingleExchangeRate> getRates()
    {
        return rates;
    }

    public void setRates(List<SingleExchangeRate> rates)
    {
        this.rates = rates;
    }
    
}
