package models;

import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Purchase {
    public String productname;
    public String merchantname;
    public int quantity;
    public int unitprice;
    public int id;

    public Purchase(String productname, String merchantname, int quantity, int unitprice) {
        this.productname = productname;
        this.merchantname = merchantname;
        this.quantity = quantity;
        this.unitprice = unitprice;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(int unitprice) {
        this.unitprice = unitprice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase)) return false;
        Purchase purchase = (Purchase) o;
        return getQuantity() == purchase.getQuantity() &&
                getUnitprice() == purchase.getUnitprice() &&
                getId() == purchase.getId() &&
                getProductname().equals(purchase.getProductname()) &&
                getMerchantname().equals(purchase.getMerchantname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductname(), getMerchantname(), getQuantity(), getUnitprice(), getId());
    }

    public void save() {
        try (Connection con = DB.sql2o.open()) {
            con.setRollbackOnException(false);
            String sql = "INSERT INTO purchase (productname,merchantname,quantity,unitprice) VALUES (:productname, :merchantname, :quantity, :unitprice,)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("productname", this.productname)
                    .addParameter("merchantname", this.merchantname)
                    .addParameter("quantity",this.quantity)
                    .addParameter("unitprice",this.unitprice)
                    .executeUpdate()
                    .getKey();
        }
    }
    public static List<Purchase> all() {
        String sql = "SELECT * FROM purchase";
        try (Connection con = DB.sql2o.open()) {
            con.setRollbackOnException(false);
            return con.createQuery(sql).executeAndFetch(Purchase.class);
        }
    }

    public static Purchase find(int id) {
        try (Connection con = DB.sql2o.open()) {
            con.setRollbackOnException(false);
            String sql = "SELECT * FROM purchase where id=:id";
            Purchase purchase = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Purchase.class);
            return purchase;
        }
    }
    public List<Object> getPurchase() {
        List<Object> allPurchase = new ArrayList<Object>();

        try(Connection con = DB.sql2o.open()) {
            String sqlPurchase = "SELECT * FROM purchase WHERE id=:id;";
            List<Purchase> purchase = con.createQuery(sqlPurchase)
                    .addParameter("id", this.id)
                    .executeAndFetch(Purchase.class);
            allPurchase.addAll(purchase);
        }
        return allPurchase;
    }
}