/**
 *
 *  @author Wojas Kamil S23878
 *
 */

package zad2;


import java.beans.*;
import java.io.Serializable;

public class Purchase implements Serializable {
    private String prod;
    private String data;
    private Double price;
    private PropertyChangeSupport pcs;
    private VetoableChangeSupport vcs;

    public Purchase() {
        pcs = new PropertyChangeSupport(this);
        vcs = new VetoableChangeSupport(this);
    }

    public Purchase(String prod, String data, Double price) {
        this.prod = prod;
        this.data = data;
        this.price = price;
        pcs = new PropertyChangeSupport(this);
        vcs = new VetoableChangeSupport(this);
    }

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        String oldProd = this.prod;
        this.prod = prod;
        pcs.firePropertyChange("prod", oldProd, prod);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) throws PropertyVetoException {
        String oldData = this.data;
        vcs.fireVetoableChange("data", oldData, data);
        this.data = data;
        pcs.firePropertyChange("data", oldData, data);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) throws PropertyVetoException {
        Double oldPrice = this.price;
        vcs.fireVetoableChange("price", oldPrice, price);
        this.price = price;
        pcs.firePropertyChange("price", oldPrice, price);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vcs.addVetoableChangeListener(listener);
    }

    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        vcs.removeVetoableChangeListener(listener);
    }

    @Override
    public String toString() {
        return "Purchase [prod=" + prod + ", data=" + data + ", price=" + price + "]";
    }
}
