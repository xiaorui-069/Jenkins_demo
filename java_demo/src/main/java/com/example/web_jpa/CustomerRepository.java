package com.example.web_jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(collectionResourceRel = "cust", path = "cust")
//public interface CustomerRepository extends PagingAndSortingRepository<Customer,Long>,CrudRepository<Customer, Long> {
//    List<Customer> findByLastName(@Param("name") String name);
//}
@Repository
interface CustomerRepository extends JpaRepository<Customer, Long> {

}