package com.cybersoft.cozastore03.service;

import com.cybersoft.cozastore03.dto.CategoryDTO;
import com.cybersoft.cozastore03.entity.CategoryEntity;
import com.cybersoft.cozastore03.repository.CategoryRepository;
import com.cybersoft.cozastore03.service.imp.CategoryServiceImp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements CategoryServiceImp {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    private Gson gson = new Gson();

//    @Cacheable("category")
    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryDTO> listCatgoryDTO = new ArrayList<>();
        if(redisTemplate.hasKey("category")){
            Type listType = new TypeToken<List<CategoryDTO>>() {}.getType();
            String data = redisTemplate.opsForValue().get("category").toString();
            listCatgoryDTO = gson.fromJson(data,listType);
            System.out.println("Em co cache");

        }else{
            System.out.println("Em chua co cache");
            List<CategoryEntity> list = categoryRepository.findAll();

            for(CategoryEntity item : list){
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(item.getId());
                categoryDTO.setCategoryName(item.getName());

                listCatgoryDTO.add(categoryDTO);
            }

            String data = gson.toJson(listCatgoryDTO);
            redisTemplate.opsForValue().set("category",data);
        }

        return listCatgoryDTO;
    }
}
