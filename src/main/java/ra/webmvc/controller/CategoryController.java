package ra.webmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.webmvc.entity.Category;
import ra.webmvc.service.CategoryService;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String showCategory(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/add")
    public String showAddCategory(Model model) {
        model.addAttribute("categories", new Category());
        return "addCategory";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("categories") Category category,Model model, RedirectAttributes redirectAttributes) {
        if (category.getCateName() == null || category.getCateName().isEmpty()) {
            model.addAttribute("categories", category);
            model.addAttribute("error", "Please enter a category name");
            return "addCategory";

        } else if (categoryService.isExistName(category.getCateName())){
            model.addAttribute("categories", category);
            model.addAttribute("error", "Category already exist");
            return "addCategory";
        }
        categoryService.insert(category);
        redirectAttributes.addFlashAttribute("message", "Category added successfully");
        return "redirect:/categories";
    }
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }
    @GetMapping("/edit/{id}")
    public String showEditCategory(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "editCategory";
    }
    @PostMapping("/edit/{id}")
    public String processEditCategory(@PathVariable("id") Long id,
                                   @ModelAttribute("category") Category category,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        // Lấy danh mục hiện tại trong DB
       Category existingCategory = categoryService.findById(id);

       if(categoryService.isExistName(existingCategory.getCateName()) && !category.getCateId().equals(id)) {
           model.addAttribute("error", "Category already exist");
           return "editCategory";
       } else if (categoryService.isExistName(category.getCateName()) && !existingCategory.getCateName().equals(category.getCateName())) {
           model.addAttribute("category", category);
           model.addAttribute("error", "Category already exist");
           return "editCategory";
       }
        // Gán lại ID, cập nhật thành công
       category.setCateId(id);
       categoryService.update(category);
       redirectAttributes.addFlashAttribute("message", "Category updated successfully");
       return "redirect:/categories";
    }
}
