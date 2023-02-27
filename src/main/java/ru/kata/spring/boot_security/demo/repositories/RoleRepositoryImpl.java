package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Role getRole(String name) {
        return getRoleByName(name);
    }

    @Override
    public Role getRoleByName(String name) {
        return entityManager.createQuery("select r from Role r where r.name = :roleName", Role.class)
                .setParameter("roleName", name)
                .setMaxResults(1)
                .getSingleResult();
    }


    @Override
    public List<Role> listAllRoles() {
        return entityManager.createQuery("from Role", Role.class)
                .getResultList();
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }
}
