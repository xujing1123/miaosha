package com.xujing.miaosha.vo;

import com.xujing.miaosha.entity.Goods;

import java.util.Date;

public class GoodsVo extends Goods {
	private Double miaoshaPrice;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(512);
		sb.append("GoodsVo[");
		sb.append(super.toString());
		sb.append("\n    miaoshaPrice=").append(this.miaoshaPrice);
		sb.append("\n    stockCount=").append(this.stockCount);
		sb.append("\n    startDate=").append(this.startDate);
		sb.append("\n    endDate=").append(this.endDate);
		sb.append("\n]");
		return sb.toString();
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
	public Double getMiaoshaPrice() {
		return miaoshaPrice;
	}
	public void setMiaoshaPrice(Double miaoshaPrice) {
		this.miaoshaPrice = miaoshaPrice;
	}

}
