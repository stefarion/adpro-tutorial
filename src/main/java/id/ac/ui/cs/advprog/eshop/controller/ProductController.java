package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "CreateProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@Valid @ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "ProductList";
    }

    @GetMapping("/edit/{productId}")
    public String editProductForm(@PathVariable("productId") String productId, Model model) {
        Product product = service.getProductById(productId);
        model.addAttribute("product", product);
        return "EditProduct";
    }

    @PutMapping("/edit/{productId}")
    public String updateProduct(@PathVariable String productId, @ModelAttribute Product productUpdate){
        productUpdate.setProductId(productId);
        service.updateProduct(productUpdate);
        return "redirect:/product/list";
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable("productId") String productId) {
        service.deleteProduct(productId);
        return "redirect:/product/list";
    }

    @Controller
    @RequestMapping("/car")
    class CarController extends ProductController {
        @Autowired
        private CarServiceImpl carService;

        @GetMapping("/CreateCar")
        public String createCarPage(Model model) {
            Car car = new Car();
            model.addAttribute("car", car);
            return "CreateCar";
        }

        @PostMapping("/createCar")
        public String createCarPost(@ModelAttribute Car car, Model model) {
            carService.create(car);
            return "redirect:listCar";
        }

        @GetMapping("/listCar")
        public String carListPage(Model model) {
            List<Car> allCars = carService.findAll();
            model.addAttribute("cars", allCars);
            return "CarList";
        }

        @GetMapping("/editCar/{carId}")
        public String editCar(@PathVariable String carId, Model model) {
            Car car = carService.findById(carId);
            model.addAttribute("car", car);
            return "EditCar";
        }

        @PostMapping("/editCar")
        public String editCarPost(@ModelAttribute Car car, Model model) {
            carService.update(car.getCarId(), car);
            return "redirect:listCar";
        }

        @PostMapping("/deleteCar")
        public String deleteCar(@RequestParam("carId") String carId) {
            carService.deleteCarById(carId);
            return "redirect:listCar";
        }
    }
}