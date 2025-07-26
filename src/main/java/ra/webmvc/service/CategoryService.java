package ra.webmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.webmvc.entity.Category;
import ra.webmvc.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    //lấy tất cả
    public List<Category> getAllCategories (){
        return categoryRepository.findAll();
    }
    //lấy theo id
    public Category findById(Long id){
        return categoryRepository.findById(id);
    }
    //kiểm tra trùng tên
    public boolean isExistName(String name){
        return categoryRepository.isNameExist(name);
    }
    //Thêm
    public void insert(Category category){
       categoryRepository.insert(category);
    }
    //cập nhật
    public void update(Category category){
        categoryRepository.update(category);
    }
    //xóa theo id
    public void delete(Long id){
        categoryRepository.delete(id);
    }
}
