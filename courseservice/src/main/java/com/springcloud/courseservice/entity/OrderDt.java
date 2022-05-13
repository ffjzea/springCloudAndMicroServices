package com.springcloud.courseservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity @Table(name="orderdt",
uniqueConstraints=@UniqueConstraint(columnNames={"order_id", "course_id"}))
@Component
public class OrderDt {
	
	@Id @Column(name = "orderdt_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int OrderDtID;
	
	@Column(name = "unit_price")
	private int unitPrice;
	
	@Column(name = "system_time")
	private String systemTime;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private OrderMt orderMt;
	
	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private CourseBasic courseID;

	public OrderDt() {
	}
	
	public OrderDt(int unitPrice, CourseBasic courseID, String systemTime, OrderMt orderMt) {
		this.unitPrice = unitPrice;
		this.courseID = courseID;
		this.systemTime = systemTime;
		this.orderMt = orderMt;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(String systemTime) {
		this.systemTime = systemTime;
	}

	public int getOrderDtID() {
		return OrderDtID;
	}

	public void setOrderDtID(int orderDtID) {
		OrderDtID = orderDtID;
	}

	public OrderMt getOrderMt() {
		return orderMt;
	}

	public void setOrderMt(OrderMt orderMt) {
		this.orderMt = orderMt;
	}

	public CourseBasic getCourseID() {
		return courseID;
	}

	public void setCourseID(CourseBasic courseID) {
		this.courseID = courseID;
	}
	
	
}
