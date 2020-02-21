package models;

import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sales {
    public String productname;
    public String customername;
    public String paymentstatus;
    public int quantity;
    public int unitprice;
    public int totalprice;
    public int id;

    public Sales(String productname, String customername, String paymentstatus, int quantity, int unitprice) {
        this.productname = productname;
        this.customername = customername;
        this.paymentstatus = paymentstatus;
        this.quantity = quantity;
        this.unitprice = unitprice;
        id=this.id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
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
        if (!(o instanceof Sales)) return false;
        Sales sales = (Sales) o;
        return getQuantity() == sales.getQuantity() &&
                getUnitprice() == sales.getUnitprice() &&
                getId() == sales.getId() &&
                getProductname().equals(sales.getProductname()) &&
                getCustomername().equals(sales.getCustomername()) &&
                getPaymentstatus().equals(sales.getPaymentstatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductname(), getCustomername(), getPaymentstatus(), getQuantity(), getUnitprice(), getId());
    }
    public void save() {
        try (Connection con = DB.sql2o.open()) {
            con.setRollbackOnException(false);
            String sql = "INSERT INTO sales (productname,customername,paymentstatus,quantity,unitprice) VALUES (:productname, :customername, :paymentstatus, :quantity, :unitprice,)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("productname", this.productname)
                    .addParameter("customername", this.customername)
                    .addParameter("paymentstatus",this.paymentstatus)
                    .addParameter("quantity",this.quantity)
                    .addParameter("unitprice",this.unitprice)
                    .executeUpdate()
                    .getKey();
        }
    }
    public static List<Sales> all() {
        String sql = "SELECT * FROM sales";
        try (Connection con = DB.sql2o.open()) {
            con.setRollbackOnException(false);
            return con.createQuery(sql).executeAndFetch(Sales.class);
        }
    }

    public static Sales find(int id) {
        try (Connection con = DB.sql2o.open()) {
            con.setRollbackOnException(false);
            String sql = "SELECT * FROM sales where id=:id";
            Sales sales = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Sales.class);
            return sales;
        }
    }
    public List<Object> getSales() {
        List<Object> allSales = new ArrayList<Object>();

        try(Connection con = DB.sql2o.open()) {
            String sqlSales = "SELECT * FROM sales WHERE id=:id;";
            List<Sales> sales = con.createQuery(sqlSales)
                    .addParameter("id", this.id)
                    .executeAndFetch(Sales.class);
            allSales.addAll(sales);
        }
        return allSales;
    }
}