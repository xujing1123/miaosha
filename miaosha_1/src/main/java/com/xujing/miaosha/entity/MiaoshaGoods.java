package com.xujing.miaosha.entity;

import java.util.Date;

public class MiaoshaGoods {
	private Long id;
	private Long goodsId;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(512);
		sb.append("MiaoshaGoods[");
		sb.append(super.toString());
		sb.append("\n    id=").append(this.id);
		sb.append("\n    goodsId=").append(this.goodsId);
		sb.append("\n    stockCount=").append(this.stockCount);
		sb.append("\n    startDate=").append(this.startDate);
		sb.append("\n    endDate=").append(this.endDate);
		sb.append("\n]");
		return sb.toString();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getStockCount() {
		return stockCount;
	}
	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
