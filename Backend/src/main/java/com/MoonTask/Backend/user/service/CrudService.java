package com.MoonTask.Backend.user.service;

/**
 * These are the common method that service class will have.
 * <li>Create: creating new user</li>
 * <li>update: update the existing user by userId</li>
 * <li>delete: delete the user by email</li>*/
public interface CrudService<T, S> {
    String create(T t);
    String update(String email, S s);
    String delete(String email);
}
