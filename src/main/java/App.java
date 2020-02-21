import java.util.HashMap;
import java.util.Map;

import models.Purchase;
import models.Sales;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");

        Map<String,Object> model = new HashMap<String,Object>();

        get("/", (req,res) -> {
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/sales/form",(req,res)->{
            return new ModelAndView(model, "sales-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/sales/new",(req,res)->{
            String productname = req.queryParams("productname");
            String customername = req.queryParams("customername");
            String paymentstatus = req.queryParams("paymentstatus");
            int quantity = Integer.parseInt(req.queryParams("quantity"));
            int unitprice = Integer.parseInt(req.queryParams("unitprice"));
            Sales sales = new Sales(productname,customername,paymentstatus,quantity,unitprice);
            sales.save();
            return new ModelAndView(model, "salesSuccess.hbs");
        }, new HandlebarsTemplateEngine());

        get("/sales",(req,res)->{
            model.put("sales",Sales.all());
            return new ModelAndView(model, "sales.hbs");
        }, new HandlebarsTemplateEngine());

        get("/purchase",(req,res) ->{
            model.put("purchase", Purchase.all());
            return new ModelAndView(model, "purchase.hbs");
        }, new HandlebarsTemplateEngine());


        get("/purchase/form",(req,res)->{
            return new ModelAndView(model, "purchase-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/purchase/new",(req,res) ->{
            String productname = req.queryParams("productname");
            String merchantname = req.queryParams("merchantname");
            int quantity = Integer.parseInt(req.queryParams("quantity"));
            int unitprice = Integer.parseInt(req.queryParams("unitprice"));
            Purchase purchase = new Purchase(productname,merchantname,quantity,unitprice);
            purchase.save();
            return new ModelAndView(model, "purchaseSuccess.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
