package com.example.springecommerce.service;

import com.example.springecommerce.api.model.RegistrationBody;
import com.example.springecommerce.exception.UserAlreadyExistsException;
import com.example.springecommerce.model.LocalUser;
import com.example.springecommerce.model.dao.LocalUserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private LocalUserDAO localUserDAO;

    public UserService(LocalUserDAO localUserDAO) {
        this.localUserDAO = localUserDAO;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if(localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()
                || localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setUsername((registrationBody.getUsername()));
        //Encrypt passwords!!
        user.setPassword(registrationBody.getPassword());
        return localUserDAO.save(user);
    }
}
