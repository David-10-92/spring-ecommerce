package com.example.springecommerce.model.dao;

import com.example.springecommerce.model.LocalUser;
import com.example.springecommerce.model.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderDAO extends ListCrudRepository<WebOrder,Long> {

    List<WebOrder> findByUser(LocalUser user);
}
