package api._bit.catalog.controllers.v1;

import api._bit.catalog.domain.GameCategory;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @GetMapping
    public List<String> getCategories() {
        return Arrays.stream(GameCategory.values()).map(gameCategory -> gameCategory.label).toList();
    }
}
