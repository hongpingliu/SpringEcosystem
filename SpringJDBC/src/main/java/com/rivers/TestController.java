package com.rivers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rivers.dao.CustomerRepository;
import com.rivers.entity.Customer;

@RestController
public class TestController {
	@Autowired
	CustomerRepository cusdao;
	@RequestMapping("/test")
	public Customer test(@RequestParam(value="info",defaultValue="") String info)
	{
		String uuid = UUID.randomUUID().toString();
		Customer cus = new Customer("zhangsan", "ZS");
		cus.setId(uuid);
		cusdao.save(cus);
		List<Customer> list = cusdao.findByLastName("ZS");
		for (Customer customer : list) {
			System.out.println(customer.getFirstName());
		}
		//return new Customer("","");
		return cus;
	}
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	final Customer retCus = new Customer("", "");
	@RequestMapping("/testJdbc")
	public Customer testJbc()
	{
		jdbcTemplate.query("select * from customer t",(rs,rowNum) -> new Customer(rs.getString("FIRST_NAME"),rs.getString("LAST_NAME"))).forEach(customer -> retCus.setFirstName(customer.getFirstName()));
		return retCus;
	}
}
