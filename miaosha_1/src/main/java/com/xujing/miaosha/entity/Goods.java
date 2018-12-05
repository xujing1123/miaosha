package com.xujing.miaosha.entity;

public class Goods {
	private Long id;
	private String goodsName;
	private String goodsTitle;
	private String goodsImages;
	private String goodsDetail;
	private Double goodsPrice;
	private Integer goodsStock;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(512);
		sb.append("Goods[");
		sb.append(super.toString());
		sb.append("\n    id=").append(this.id);
		sb.append("\n    goodsName=").append(this.goodsName);
		sb.append("\n    goodsTitle=").append(this.goodsTitle);
		sb.append("\n    goodsImages=").append(this.goodsImages);
		sb.append("\n    goodsDetail=").append(this.goodsDetail);
		sb.append("\n    goodsPrice=").append(this.goodsPrice);
		sb.append("\n    goodsStock=").append(this.goodsStock);
		sb.append("\n]");
		return sb.toString();
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}


	public String getGoodsDetail() {
		return goodsDetail;
	}
	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}
	public Double getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public Integer getGoodsStock() {
		return goodsStock;
	}
	public void setGoodsStock(Integer goodsStock) {
		this.goodsStock = goodsStock;
	}

    public String getGoodsImages() {
        return goodsImages;
    }

    public void setGoodsImages(String goodsImages) {
        this.goodsImages = goodsImages;
    }
}
