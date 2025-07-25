package ra.webmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.webmvc.entity.Category;
import ra.webmvc.repository.CategoryRepository;

import javax.transaction.Transactional;
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
    public Optional<Category> findById(Long id){
        return categoryRepository.findById(id);
    }
    //kiểm tra trùng tên
    public boolean isExistName(String name){
        return categoryRepository.findByCateName(name).isPresent();
    }

    //Thêm hoặc cập nhật
    public void save(Category category){
       categoryRepository.save(category);
    }
    //xóa theo id
    public void delete(Long id){
        categoryRepository.deleteById(id);
    }
}
