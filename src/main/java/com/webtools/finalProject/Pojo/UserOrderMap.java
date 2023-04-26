package com.webtools.finalProject.Pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="userOrders")
public class UserOrderMap {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer user_order_id;
	
	@ManyToOne
	@JoinColumn(name="id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="packageId")
	private NuEvents nuEvents;
	
	
	public NuEvents getNuEvents() {
		return nuEvents;
	}

	public void setNuEvents(NuEvents nuEvents) {
		this.nuEvents = nuEvents;
	}

	public UserOrderMap() {
		
	}

	public UserOrderMap( User user, NuEvents nuEvents, Integer amount) {
		
		this.user = user;
		this.nuEvents = nuEvents;
		this.amount = amount;
	}

	public Integer getUser_order_id() {
		return user_order_id;
	}

	public void setUser_order_id(Integer user_order_id) {
		this.user_order_id = user_order_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	private Integer amount;
}
