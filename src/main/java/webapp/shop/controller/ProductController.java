package webapp.shop.controller;

import org.springframework.web.bind.annotation.*;
import webapp.shop.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product")
public class ProductController {

    private int counter = 4;

    //Список товаров (пока задан по умалчанию, но обычно берем содержимое из БД)
    private List<Map<String,String>> products = new ArrayList<>(){{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First product"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second product"); }});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third product"); }});
    }};

    //Возвращает список товаров
    @GetMapping
    public List<Map<String,String>> list(){
        return products;
    }

    //Возвращает товар по id
    @GetMapping("{id}")
    public Map<String,String> getProduct(@PathVariable String id){
        return products.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    //Добавляет товар с список товаров
    @PostMapping
    public Map<String,String> createProduct(@RequestBody Map<String,String> product){
        product.put("id", String.valueOf(counter++));
        products.add(product);
        return product;
    }

    //Обновляет текущую запись
    @PutMapping("{id}")
    public Map<String,String> updateProduct(@PathVariable String id, @RequestBody Map<String,String> product){
        Map<String,String> productFromDb = getProduct(id);
        productFromDb.putAll(product);
        productFromDb.put("id", id);

        return productFromDb;
    }

    //Удаляет запись
    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable String id){
        Map<String,String> product = getProduct(id);
        products.remove(product);
    }
}
