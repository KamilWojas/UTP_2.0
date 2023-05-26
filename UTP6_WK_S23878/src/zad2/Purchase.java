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

    public Purchase(String prod, String data, Double price) {
        this.prod = prod;
        this.data = data;
        this.price = price;
    }

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) throws PropertyVetoException {
        String oldData = this.data;
        this.data = data;
        firePropertyChange("data", oldData, data);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) throws PropertyVetoException {
        if (price < 1000) {
            throw new PropertyVetoException("Price change to: " + price + " not allowed", null);
        }
        Double oldPrice = this.price;
        this.price = price;
        firePropertyChange("price", oldPrice, price);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
        System.out.println("Change value of: " + propertyName + " from: " + oldValue + " to: " + newValue);
    }

    @Override
    public String toString() {
        return "Purchase [prod=" + prod + ", data=" + data + ", price=" + price + "]";
    }
}

