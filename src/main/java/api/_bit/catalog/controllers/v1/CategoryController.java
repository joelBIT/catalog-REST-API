package api._bit.catalog.controllers.v1;

import api._bit.catalog.domain.GameCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "The 8-bit Catalog category API")
public class CategoryController {

    @GetMapping
    @Operation(summary = "Get a list of all NES game categories in the 8-bit Catalog.", description = "Returns a list of all NES game categories used in the 8-bit Catalog.")
    public List<String> getCategories() {
        return Arrays.stream(GameCategory.values()).map(gameCategory -> gameCategory.label).toList();
    }
}
